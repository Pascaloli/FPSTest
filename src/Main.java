import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

  public static Main instance;

  public static void main(String[] args) {
    new Main().start();
  }

  private final int amount = 4;
  private final int width = 150 * amount;
  private final int height = 500;

  public void start() {
    instance = this;
    this.setTitle("FPS Test - Made by Pascaloli");
    JPanel panel = getContent();
    this.add(panel);
    this.setSize(this.width + 13, this.height);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    startTimer();
  }

  private HintTextField[] textFields = new HintTextField[amount];
  private CircleObject[] circles = new CircleObject[amount];
  private EasingThread ease = new EasingThread(System.currentTimeMillis());

  public JPanel getContent() {
    int textFieldWidth = 60;
    int textFieldHeight = 20;
    int textFieldY = this.height - 60 - textFieldHeight;
    JPanel panel = new JPanel();
    panel.setLayout(null);

    AtomicInteger fps = new AtomicInteger(15);
    IntStream.range(0, amount).distinct().forEach(i -> {
      textFields[i] = new HintTextField(String.valueOf(fps.get()));
      int xPos = calculatePosition(amount, i + 1, textFieldWidth);
      textFields[i].setBounds(xPos, textFieldY, textFieldWidth, textFieldHeight);
      panel.add(textFields[i]);

      circles[i] = new CircleObject(xPos, textFieldWidth, ease);
      circles[i].setBounds(xPos, 50, 100, this.height);
      panel.add(circles[i]);

      fps.set(fps.get() * 2);
    });

    return panel;
  }

  public void startTimer() {
    this.ease.start();
    new Thread(() -> {
      long[] times = new long[amount];
      IntStream.range(0, amount).forEach(i1 -> times[i1] = System.currentTimeMillis());
      while (true) {
        IntStream.range(0, amount).parallel().forEach(i2 -> {
          String fpsRaw = textFields[i2].getText();
          int fps = fpsRaw.isEmpty() ? 1 : Integer.valueOf(fpsRaw);
          if (System.currentTimeMillis() - times[i2] >= 1000 / fps) {
            circles[i2].repaint();
            times[i2] = System.currentTimeMillis();
          }
        });
      }
    }).start();
  }

  public int calculatePosition(int sections, int section, int width) {
    return ((this.width / sections) * (section - 1)) + (this.width / sections / 2) - (width / 2);
  }

}
