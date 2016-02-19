import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

class ShutDownSelectFrame
extends JFrame {
    JRadioButton shutdownRButton;
    JRadioButton rebootRButton;
    JCheckBox forecastCheck;
    JCheckBox quickCheck;
    ButtonGroup bootTypeGroup;
    ButtonGroup forecastGroup;
    JLabel bootTypeLabel;
    JLabel forecastHour;
    JLabel forecastMin;
    JLabel forecastSec;
    JLabel testingPlan;
    JLabel testingInstant;
    JButton forecastButton;
    JButton[] quickSelect;
    JButton[] instantButton;
    JComboBox<String> hoursCBox;
    JComboBox<String> minCBox;
    JComboBox<String> secCBox;
    JPanel selectBootTypePanel = new JPanel();
    JPanel forecastPanel;
    JPanel quickSelectPanel;
    JPanel systemTimePanel;
    JPanel instantButtonPanel;
    Box mainFrame;
    Box instantFrame;
    JTabbedPane shutDownSelectTab;
    JTabbedPane shutDownInstantTab;
    String[] hoursList;
    String[] minList;
    String[] secList;
    String[] quickSelectText = new String[]{"10 sec", "20 sec", "30 sec", "1 min", "2 min", "5 min", "10 min", "20 min"};
    String[] quickSelectActionCommand = new String[]{"10", "20", "30", "60", "120", "300", "600", "1200"};
    String[] instantButtonText = new String[]{"LOGG OFF", "SHUTDOWN", "HIBERNATE", "RESTART"};
    String[] instantButtonActionCommand = new String[]{" /l ", " /s ", " /h ", " /r "};
    String bootType = "/s ";
    String command;
    int hour = 0;
    int min = 0;
    int sec = 30;

    public ShutDownSelectFrame() {
        super("Plan Your Boot");
        this.selectBootTypePanel.setLayout(new FlowLayout());
        this.bootTypeLabel = new JLabel("Select Boot Type : ", 4);
        this.bootTypeLabel.setToolTipText("Select Type of Boot Operation You Want");
        this.shutdownRButton = new JRadioButton("Shutdown", true);
        this.rebootRButton = new JRadioButton("Reboot");
        this.selectBootTypePanel.add(this.shutdownRButton);
        this.selectBootTypePanel.add(this.rebootRButton);
        this.bootTypeGroup = new ButtonGroup();
        this.bootTypeGroup.add(this.shutdownRButton);
        this.bootTypeGroup.add(this.rebootRButton);
        this.forecastPanel = new JPanel();
        this.forecastCheck = new JCheckBox("Custom Time : ");
        this.forecastCheck.setToolTipText("Set custom time after which system automatically performs selected boot operation");
        this.hoursList = new String[25];
        int n = 0;
        while (n < 25) {
            this.hoursList[n] = "" + n++;
        }
        this.minList = new String[60];
        n = 0;
        while (n < 60) {
            this.minList[n] = "" + n++;
        }
        this.secList = new String[60];
        n = 0;
        while (n < 60) {
            this.secList[n] = "" + n++;
        }
        this.hoursCBox = new JComboBox<String>(this.hoursList);
        this.minCBox = new JComboBox<String>(this.minList);
        this.secCBox = new JComboBox<String>(this.secList);
        this.secCBox.setSelectedIndex(30);
        this.hoursCBox.setMaximumRowCount(5);
        this.minCBox.setMaximumRowCount(5);
        this.secCBox.setMaximumRowCount(5);
        this.forecastHour = new JLabel("Hour : ");
        this.forecastMin = new JLabel("Min : ");
        this.forecastSec = new JLabel("Sec : ");
        this.forecastButton = new JButton("Set as Plan !!");
        this.forecastSetEnabled(false);
        this.forecastPanel.add(this.forecastHour);
        this.forecastPanel.add(this.hoursCBox);
        this.forecastPanel.add(this.forecastMin);
        this.forecastPanel.add(this.minCBox);
        this.forecastPanel.add(this.forecastSec);
        this.forecastPanel.add(this.secCBox);
        this.forecastPanel.add(this.forecastButton);
        this.testingPlan = new JLabel("Himanshu ");
        this.quickSelectPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        this.quickCheck = new JCheckBox("Quick Select : ", true);
        this.quickCheck.setToolTipText("Use predefined dead line's");
        this.forecastGroup = new ButtonGroup();
        this.forecastGroup.add(this.quickCheck);
        this.forecastGroup.add(this.forecastCheck);
        this.quickSelect = new JButton[8];
        for (n = 0; n < 8; ++n) {
            this.quickSelect[n] = new JButton(this.quickSelectText[n]);
            this.quickSelect[n].setActionCommand(this.quickSelectActionCommand[n]);
            this.quickSelect[n].addActionListener((ActionListener)new PlanActionHandler());
            this.quickSelectPanel.add(this.quickSelect[n]);
        }
        this.systemTimePanel = new JPanel(new BorderLayout());
        this.systemTimePanel.add((Component)this.testingPlan, "East");
        this.mainFrame = Box.createVerticalBox();
        this.mainFrame.add(this.bootTypeLabel);
        this.mainFrame.add(this.selectBootTypePanel);
        this.mainFrame.add(this.forecastCheck);
        this.mainFrame.add(this.forecastPanel);
        this.mainFrame.add(this.quickCheck);
        this.mainFrame.add(this.quickSelectPanel);
        this.mainFrame.add(this.systemTimePanel);
        ItemHandler itemHandler = new ItemHandler();
        this.shutdownRButton.addItemListener((ItemListener)itemHandler);
        this.rebootRButton.addItemListener((ItemListener)itemHandler);
        this.forecastCheck.addItemListener((ItemListener)itemHandler);
        this.forecastButton.addActionListener((ActionListener)new PlanActionHandler());
        this.hoursCBox.addItemListener((ItemListener)itemHandler);
        this.minCBox.addItemListener((ItemListener)itemHandler);
        this.secCBox.addItemListener((ItemListener)itemHandler);
        this.shutDownSelectTab = new JTabbedPane();
        this.shutDownSelectTab.addTab("PLAN", null, this.mainFrame, "Plan by setting time limit");
        this.instantButtonPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        this.instantButton = new JButton[4];
        for (int i = 0; i < 4; ++i) {
            this.instantButton[i] = new JButton(this.instantButtonText[i]);
            this.instantButton[i].setActionCommand(this.instantButtonActionCommand[i]);
            this.instantButtonPanel.add(this.instantButton[i]);
            this.instantButton[i].addActionListener(new InstantActionHandler());
        }
        this.testingInstant = new JLabel("instant side testing");
        this.instantButtonPanel.add(this.testingInstant);
        this.shutDownSelectTab.addTab("INSTANT", null, this.instantButtonPanel, "Perform instant operation instead");
        this.add(this.shutDownSelectTab);
    }

    private void forecastSetEnabled(boolean bl) {
        this.hoursCBox.setEnabled(bl);
        this.minCBox.setEnabled(bl);
        this.secCBox.setEnabled(bl);
        this.forecastButton.setEnabled(bl);
    }

    

    class InstantActionHandler implements ActionListener {
        InstantActionHandler() {
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ShutDownSelectFrame.this.command = "shutdown ";
            for (int i = 0; i < 4; ++i) {
                if (actionEvent.getSource() != ShutDownSelectFrame.this.instantButton[i]) continue;
                ShutDownSelectFrame.this.command = ShutDownSelectFrame.this.command + ShutDownSelectFrame.this.instantButton[i].getActionCommand();
                break;
            }
            ShutDownSelectFrame.this.testingInstant.setText(ShutDownSelectFrame.this.command);
            try {
                Process process = Runtime.getRuntime().exec(ShutDownSelectFrame.this.command);
            }
            catch (Exception var2_4) {
                JOptionPane.showMessageDialog(null, "Error occured" + var2_4);
            }
        }
    }
	
	class ItemHandler implements ItemListener {
    ItemHandler() {
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        int n;
        if (itemEvent.getStateChange() == 1) {
            if (ShutDownSelectFrame.this.shutdownRButton.isSelected()) {
                ShutDownSelectFrame.this.bootType = " /s ";
            }
            if (ShutDownSelectFrame.this.rebootRButton.isSelected()) {
                ShutDownSelectFrame.this.bootType = " /r ";
            }
            if (ShutDownSelectFrame.this.forecastCheck.isSelected()) {
                ShutDownSelectFrame.this.forecastSetEnabled(true);
                for (n = 0; n < 8; ++n) {
                    ShutDownSelectFrame.this.quickSelect[n].setEnabled(false);
                }
                ShutDownSelectFrame.this.hour = ShutDownSelectFrame.this.hoursCBox.getSelectedIndex();
                ShutDownSelectFrame.this.min = ShutDownSelectFrame.this.minCBox.getSelectedIndex();
                ShutDownSelectFrame.this.sec = ShutDownSelectFrame.this.secCBox.getSelectedIndex();
            }
        }
        if (itemEvent.getStateChange() == 2 && itemEvent.getSource() == ShutDownSelectFrame.this.forecastCheck) {
            ShutDownSelectFrame.this.forecastSetEnabled(false);
            for (n = 0; n < 8; ++n) {
                ShutDownSelectFrame.this.quickSelect[n].setEnabled(true);
            }
        }
    }
}


class PlanActionHandler implements ActionListener {
    PlanActionHandler() {
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ShutDownSelectFrame.this.command = "shutdown " + ShutDownSelectFrame.this.bootType + " /t ";
        ShutDownSelectFrame.this.command = actionEvent.getSource() == ShutDownSelectFrame.this.forecastButton ? ShutDownSelectFrame.this.command + (ShutDownSelectFrame.this.hour * 60 * 60 + ShutDownSelectFrame.this.min * 60 + ShutDownSelectFrame.this.sec) + " /f " : ShutDownSelectFrame.this.command + actionEvent.getActionCommand() + " /f ";
        ShutDownSelectFrame.this.testingPlan.setText(ShutDownSelectFrame.this.command);
        try {
            Process process = Runtime.getRuntime().exec(ShutDownSelectFrame.this.command);
        }
        catch (Exception var2_3) {
            JOptionPane.showMessageDialog(null, "Error occured" + var2_3);
        }
    }
}
}
public class ShutDown {
    public static void main(String[] arrstring) {
        ShutDownSelectFrame shutDownSelectFrame = new ShutDownSelectFrame();
        shutDownSelectFrame.setDefaultCloseOperation(3);
        shutDownSelectFrame.setVisible(true);
        shutDownSelectFrame.setAlwaysOnTop(true);
        shutDownSelectFrame.setSize(500, 300);
    }
}