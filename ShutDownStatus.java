import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class ShutDownStatusFrame
extends JFrame
implements ActionListener {
    JButton abortButton;
    String command = "";
    JLabel testingStatus;
    Box boxLayout = Box.createVerticalBox();

    ShutDownStatusFrame() {
        super("Status");
        this.boxLayout.add(Box.createRigidArea(new Dimension(20, 20)));
        this.abortButton = new JButton("ABORT !!");
        this.boxLayout.add(this.abortButton);
        this.abortButton.addActionListener(this);
        this.testingStatus = new JLabel("status representative");
        this.boxLayout.add(Box.createRigidArea(new Dimension(20, 20)));
        this.boxLayout.add((Component)this.testingStatus, "South");
        this.add(this.boxLayout);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.command = "shutdown /a";
        this.testingStatus.setText(this.command);
        try {
            Process process = Runtime.getRuntime().exec(this.command);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error occured" + e);
        }
    }
}
public class ShutDownStatus {
    public static void main(String[] arrstring) {
        ShutDownStatusFrame shutDownStatusFrame = new ShutDownStatusFrame();
        shutDownStatusFrame.setDefaultCloseOperation(3);
        shutDownStatusFrame.setSize(500, 300);
        shutDownStatusFrame.setVisible(true);
    }
}