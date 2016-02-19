import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

class ShutDownToggleFrame
implements ActionListener {
    ShutDownSelectFrame selectFrame = new ShutDownSelectFrame();
    ShutDownStatusFrame statusFrame = new ShutDownStatusFrame();

    ShutDownToggleFrame() {
        this.selectFrame.forecastButton.addActionListener(this);
        for (int i = 0; i < 8; ++i) {
            this.selectFrame.quickSelect[i].addActionListener(this);
            if (i >= 4) continue;
            this.selectFrame.instantButton[i].addActionListener(this);
        }
        this.statusFrame.abortButton.addActionListener(this);
        this.createSelectFrame(true);
    }

    void createSelectFrame(boolean bl) {
        this.selectFrame.setVisible(bl);
        this.selectFrame.setSize(500, 300);
        this.selectFrame.setDefaultCloseOperation(3);
    }

    void createStatusFrame(boolean bl) {
        this.statusFrame.setVisible(bl);
        this.statusFrame.setSize(500, 300);
        this.statusFrame.setDefaultCloseOperation(3);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        boolean bl = actionEvent.getSource() == this.statusFrame.abortButton;
        this.createStatusFrame(!bl);
        this.createSelectFrame(bl);
    }
}
public class ShutDownToggle {
    public static void main(String[] arrstring) {
        ShutDownToggleFrame shutDownToggleFrame = new ShutDownToggleFrame();
    }
}