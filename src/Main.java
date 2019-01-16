import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

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
      System.out.println("beginning: " + fps.get());
      textFields[i] = new HintTextField(String.valueOf(fps.get()));
      int xPos = calculatePosition(amount, i + 1, textFieldWidth);
      textFields[i].setBounds(xPos, textFieldY, textFieldWidth, textFieldHeight);
      panel.add(textFields[i]);

      circles[i] = new CircleObject(xPos, textFieldWidth, ease);
      circles[i].setBounds(xPos, 50, 100, this.height);
      panel.add(circles[i]);
      
      fps.set(fps.get() * 2);
      System.out.println("end: " + fps.get());
    });

    return panel;
  }

  public void startTimer() {
    this.ease.start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        long[] times = new long[amount];
        IntStream.range(0, amount).parallel().forEach(i -> times[i] = System.currentTimeMillis());
        System.out.println(times[0] + " " + times[1] + " " + times[2] + " " + times[3]);
        while (true) {
          IntStream.range(0, amount).parallel().forEach(i -> {
            if (System.currentTimeMillis() - times[i] >= 1000 / Integer
                .valueOf(textFields[i].getText().isEmpty() ? "1" : textFields[i].getText())) {
              circles[i].repaint();
              times[i] = System.currentTimeMillis();
            }
          });
        }
      }
    }).start();
  }

  public int calculatePosition(int sections, int section, int width) {
    return ((this.width / sections) * (section - 1)) + (this.width / sections / 2) - (width / 2);
  }

}
