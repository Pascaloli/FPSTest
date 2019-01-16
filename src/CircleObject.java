import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;

public class CircleObject extends JComponent {

  private int x;
  private int radius;
  private EasingThread ease;

  public CircleObject(int x, int radius, EasingThread ease) {
    this.x = x;
    this.radius = radius;
    this.ease = ease;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g.setColor(Color.black);
    g2d.draw(new Ellipse2D.Double(0, this.ease.ease, radius, radius));
  }
}


