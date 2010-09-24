
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TabbedPaneDemo extends JPanel {

    public TabbedPaneDemo() {
        ImageIcon icon = new ImageIcon("images/middle.gif");
        JTabbedPane tabbedPane = new JTabbedPane();
        Component panel1 = makeTextPanel("Blah");
        tabbedPane.addTab("One", icon, panel1, "Does nothing");
        tabbedPane.setSelectedIndex(0);

        Component panel4 = makePanel();
        tabbedPane.addTab("Two", icon, panel4, "Does nothing at all");
//Add the tabbed pane to this panel.
        setLayout(new GridLayout(1, 1));
        add(tabbedPane);
    }

    protected Component makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    protected Component makePanel() {
        JPanel panel = new JPanel(false);
        JEditorPane jEdit = new JEditorPane();
        JScrollPane jScrollPane1 = new JScrollPane();
        
        jScrollPane1.getViewport().add(jEdit, null);
        panel.add(jScrollPane1, null);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(jScrollPane1);
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(new TabbedPaneDemo(),
                BorderLayout.CENTER);
        frame.setSize(400, 125);
        frame.setVisible(true);
    }
}
