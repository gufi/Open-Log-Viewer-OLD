/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OpenLogReader;

import Listeners.OpenListener;
import java.awt.Dimension;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author bryan
 */
public class LogMenu extends JMenuBar {

    public LogMenu() {
        this.setPreferredSize(new Dimension(200,20));
        this.setMinimumSize(new Dimension(200,20));
        //Creat the File Menu
        JMenu mnu_File = new JMenu("File");
        mnu_File.setVisible(true);
        JMenuItem mnu_File_Open = new JMenuItem("Open Log");
        JMenuItem mnu_File_Exit = new JMenuItem("Exit");

        ///File Menu Mouse listeners
        ////////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        mnu_File_Open.addMouseListener(new OpenListener() );

        mnu_File.add(mnu_File_Open);
        mnu_File.add(mnu_File_Exit);

        //Statistics Menu

        JMenu statistics = new JMenu("Statistics");
        JMenuItem mnu_Statistics_fuelBurned = new JMenuItem("Fuel Burned this Log");
        JMenuItem mnu_Statistics_avgGasMilage = new JMenuItem("Average Gas Milage");
        JMenuItem mnu_Statistics_distTraveled = new JMenuItem("Distance Traveled");

        statistics.add(mnu_Statistics_fuelBurned);
        statistics.add(mnu_Statistics_avgGasMilage);
        statistics.add(mnu_Statistics_distTraveled);


        //Statistics Menu Listeners

        /*mnu_Statistics_fuelBurned.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fuelObj = null;
                fuelObj = new FuelStatistics();
                fuelObj.getTotalFuelConsumption(dataLog);
            }
        });*/

        // Create the Help Menu

        JMenu mnu_Help = new JMenu("Help");
        JMenuItem mnu_Help_about = new JMenuItem("About");
        JMenuItem mnu_Help_helpFiles = new JMenuItem("Help Files");
        JMenuItem mnu_Help_megaManual = new JMenuItem("MegaManual");

        mnu_Help.add(mnu_Help_about);
        mnu_Help.add(mnu_Help_helpFiles);
        mnu_Help.add(mnu_Help_megaManual);


        add(mnu_File);
        add(statistics);
        add(mnu_Help);
    }
}
