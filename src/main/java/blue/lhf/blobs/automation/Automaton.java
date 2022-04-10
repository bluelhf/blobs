package blue.lhf.blobs.automation;

import blue.lhf.blobs.simulation.model.State;
import blue.lhf.blobs.simulation.strategy.*;
import net.sourceforge.tess4j.*;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;

public class Automaton implements AutoCloseable {
    private final ITesseract tesseract;
    private final Robot robot;
    private final Path dataPath;

    public Automaton() throws IOException, AWTException {
        tesseract = new Tesseract();
        dataPath = Files.createTempDirectory("tessdata");

        try (InputStream is = getClass().getResourceAsStream("/eng.traineddata")) {
            if (is == null) throw new IOException("Corrupt jarfile");
            Files.copy(is, dataPath.resolve("eng.traineddata"));
        }

        tesseract.setDatapath(dataPath.toAbsolutePath().toString());
        robot = new Robot();
        robot.setAutoDelay(100);
    }

    public static void main(String[] args) {

        Strategy strategy = new ChanceStrategy();

        try (Automaton automaton = new Automaton()) {
            while (true) {
                Integer heads = automaton.readHeads();
                Integer tails = automaton.readTails();

                // We don't care about score or flips for the chance strategy
                Integer score = 0;//automaton.readScore();
                Integer flips = 0;//automaton.readFlips();

                Random random = new Random();

                System.out.printf("%s %s %s %s%n", heads, tails, score, flips);
                if (heads == null && tails == null) {
                    automaton.flip();
                    LockSupport.parkNanos((long) 0.5E9);
                } else if (heads != null && tails != null && score != null && flips != null) {
                    State state = new State(heads, tails, score, flips);
                    var action = strategy.act(state);
                    switch (action) {
                        case FLIP -> {
                            automaton.flip();
                            LockSupport.parkNanos((long) 0.5E9);
                        }
                        case GUESS_FAIR -> {
                            automaton.fair();
                            LockSupport.parkNanos((long) 3E9);
                        }
                        case GUESS_CHEATER -> {
                            automaton.cheater();
                            LockSupport.parkNanos((long) 3E9);
                        }
                    }
                    LockSupport.parkNanos((long) (1E9 * (random.nextDouble(0.1, 0.5))));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flip() {
        robot.mouseMove(1300, 920);
        robot.mousePress(BUTTON1_DOWN_MASK);
        robot.mouseRelease(BUTTON1_DOWN_MASK);
    }

    public void fair() {
        robot.mouseMove(1300, 1020);
        robot.mousePress(BUTTON1_DOWN_MASK);
        robot.mouseRelease(BUTTON1_DOWN_MASK);
    }

    public void cheater() {
        robot.mouseMove(1550, 1020);
        robot.mousePress(BUTTON1_DOWN_MASK);
        robot.mouseRelease(BUTTON1_DOWN_MASK);
    }

    public String readScreen(Rectangle rectangle) throws TesseractException, IOException {
        var image = robot.createScreenCapture(rectangle);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                Color color = new Color(rgb);
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                if (hsb[2] > 0.5) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                } else {
                    image.setRGB(i, j, Color.WHITE.getRGB());
                }
            }
        }
        return tesseract.doOCR(image);
    }

    public Integer numbers(String input) {
        try {
            return Integer.parseInt(
                input.chars()
                    .filter(c -> c >= '0' && c <= '9')
                    .mapToObj(c -> String.valueOf((char) c))
                    .collect(Collectors.joining()));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer readHeads() throws Exception {
        return numbers(readScreen(new Rectangle(1403, 552, 140, 28)));
    }

    public Integer readTails() throws Exception {
        return numbers(readScreen(new Rectangle(1402, 583, 120, 32)));

    }

    public Integer readScore() throws Exception {
        return numbers(readScreen(new Rectangle(1197, 851, 184, 28)));

    }

    public Integer readFlips() throws Exception {
        return numbers(readScreen(new Rectangle(1416, 851, 240, 28)));

    }

    @Override
    public void close() throws Exception {
        Files.deleteIfExists(dataPath);
    }
}
