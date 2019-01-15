import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Main extends JFrame {

  public static Main instance;

  public static void main(String[] args) {
    new Main().start();
  }

  private final int width = 700;
  private final int height = 500;

  public void start() {
    instance = this;
    this.setTitle("FPS Test");
    JPanel panel = getContent();
    this.add(panel);
    this.setSize(this.width + 13, this.height);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    // start circle movement
    startTimer();
  }

  private String[] defaultFps = new String[] {"15", "30", "60", "120"};
  private HintTextField[] textFields = new HintTextField[] {null, null, null, null};
  private CircleObject[] circles = new CircleObject[] {null, null, null, null};
  private EasingThread ease = new EasingThread(System.currentTimeMillis());

  public JPanel getContent() {
    int textFieldWidth = 60;
    int textFieldHeight = 20;
    int textFieldY = this.height - 60 - textFieldHeight;
    JPanel panel = new JPanel();
    panel.setLayout(null);

    textFields[0] = new HintTextField(defaultFps[0]);
    int fps1X = calculatePosition(4, 1, textFieldWidth);
    textFields[0].setBounds(fps1X, textFieldY, textFieldWidth, textFieldHeight);
    panel.add(textFields[0]);

    circles[0] = new CircleObject(fps1X, textFieldWidth, ease);
    circles[0].setBounds(fps1X, 50, 100, this.height);
    panel.add(circles[0]);

    textFields[1] = new HintTextField(defaultFps[1]);
    int fps2X = calculatePosition(4, 2, textFieldWidth);
    textFields[1].setBounds(fps2X, textFieldY, textFieldWidth, textFieldHeight);
    panel.add(textFields[1]);

    circles[1] = new CircleObject(fps2X, textFieldWidth, ease);
    circles[1].setBounds(fps2X, 50, 100, this.height);
    panel.add(circles[1]);

    textFields[2] = new HintTextField(defaultFps[2]);
    int fps3X = calculatePosition(4, 3, textFieldWidth);
    textFields[2].setBounds(fps3X, textFieldY, textFieldWidth, textFieldHeight);
    panel.add(textFields[2]);

    circles[2] = new CircleObject(fps3X, textFieldWidth, ease);
    circles[2].setBounds(fps3X, 50, 100, this.height);
    panel.add(circles[2]);

    textFields[3] = new HintTextField(defaultFps[3]);
    int fps4X = calculatePosition(4, 4, textFieldWidth);
    textFields[3].setBounds(fps4X, textFieldY, textFieldWidth, textFieldHeight);
    panel.add(textFields[3]);

    circles[3] = new CircleObject(fps4X, textFieldWidth, ease);
    circles[3].setBounds(fps4X, 50, 100, this.height);
    panel.add(circles[3]);

    return panel;
  }

  public Timer[] timers = new Timer[] {null, null, null, null};

  public void startTimer() {

    this.ease.start();

    new Thread(new Runnable() {

      @Override
      public void run() {
        long time1 = System.currentTimeMillis();
        long time2 = System.currentTimeMillis();
        long time3 = System.currentTimeMillis();
        long time4 = System.currentTimeMillis();
        while (true) {
          if (System.currentTimeMillis() - time1 >= 1000 / Integer
              .valueOf(textFields[0].getText().isEmpty() ? "1" : textFields[0].getText())) {
            circles[0].repaint();
            time1 = System.currentTimeMillis();
          }
          System.out.println("#" + textFields[1].getText() + "#");
          if (System.currentTimeMillis() - time2 >= 1000 / Integer
              .valueOf(textFields[1].getText().isEmpty() ? "1" : textFields[1].getText())) {
            circles[1].repaint();
            time2 = System.currentTimeMillis();
          }

          if (System.currentTimeMillis() - time3 >= 1000 / Integer
              .valueOf(textFields[2].getText().isEmpty() ? "1" : textFields[2].getText())) {
            circles[2].repaint();
            time3 = System.currentTimeMillis();
          }

          if (System.currentTimeMillis() - time4 >= 1000 / Integer
              .valueOf(textFields[3].getText().isEmpty() ? "1" : textFields[3].getText())) {
            circles[3].repaint();
            time4 = System.currentTimeMillis();
          }
        }
      }
    }).start();
  }

  public int calculatePosition(int sections, int section, int width) {
    return ((this.width / sections) * (section - 1)) + (this.width / sections / 2) - (width / 2);
  }

}
