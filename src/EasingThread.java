import java.awt.Graphics;
import java.awt.Graphics2D;

public class EasingThread extends Thread {

  public float ease = 0;
  private long time = 0;
  private boolean flip = false;

  public EasingThread(long time) {
    this.time = time;
  }

  @Override
  public void run() {
    while (true) {
      Easing e = Easing.CUBIC_IN_OUT;
      float ease = e.ease((System.currentTimeMillis() - time), 0, 300, 20 * 60);
      if ((System.currentTimeMillis() - time) > 20 * 60 - 200) {
        ease = 300;
        time = System.currentTimeMillis();
        flip = !flip;
      }

      if (flip) {
        ease = 300 - ease;
      }
      this.ease = ease;
    }
  }

}
