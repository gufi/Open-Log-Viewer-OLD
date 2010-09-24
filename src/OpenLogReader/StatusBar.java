/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OpenLogReader;

import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author bryan
 */
public class StatusBar extends JPanel {
    
    
        
        JLabel status1;
        JLabel status2;
        JPanel panel;

    public StatusBar() {
        super();
        ///Dimension sized = new Dimension(this.getParent().getSize());
        //sized.setSize(10, sized.getWidth());
        //this.setPreferredSize(sized);
        MigLayout layout = new MigLayout("","[100:100:200][50:2000:2000][400:550:1800]","[15:15:40]");
        setLayout(layout);

        status1 = new JLabel();
        status2 = new JLabel();
        panel = new JPanel();

        this.add(status1,"cell 0 0");
        this.add(panel,"Cell 1 0");
        this.add(status2,"cell 2 0");

        status1.setVisible(true);
        status2.setVisible(true);

    }

    public void setStatus1(String status1) {
        this.status1.setText(status1);
    }

    public void setStatus2(String status2) {
        this.status2.setText(status2);
    }









}
