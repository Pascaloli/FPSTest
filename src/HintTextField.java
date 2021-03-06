import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class HintTextField extends JTextField implements FocusListener {

  private HintTextField instance;
  private String hint;
  private boolean showingHint;

  public HintTextField(String hint) {
    super(hint);
    this.hint = hint;
    this.showingHint = true;
    this.setText(hint);
    instance = this;
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar()) || instance.getText().length() >= 3
            || (instance.getText().length() == 0 && e.getKeyChar() == '0')) {
          e.consume();
          return;
        }
      }
    });
    super.addFocusListener(this);
  }

  @Override
  public void focusGained(FocusEvent e) {
    if (this.getText().isEmpty()) {
      super.setText("");
      showingHint = false;
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (this.getText().isEmpty()) {
      super.setText(hint);
      showingHint = true;
    }
  }

  @Override
  public String getText() {
    return super.getText();
  }
}
