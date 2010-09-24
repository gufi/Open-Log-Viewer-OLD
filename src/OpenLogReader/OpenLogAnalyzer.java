/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OpenLogReader;

import javax.swing.*;
import java.awt.*;
import LogParser.*;
import net.miginfocom.swing.MigLayout;
import Statistics.FuelStatistics;

/**
 *
 * @author bryan
 */
public class OpenLogAnalyzer {

    private static JFrame main;
    private static JTabbedPane tabbedPane;

    private static JScrollPane mainScroll;
    private static JPanel mainContainer;

    private static JPanel varsContainer;
    private static JScrollPane varsScroll;

    private static LogMenu mainMenu;

    private static OpenPlayBar playBar;

    private static StatusBar statusBar;
    private static FuelStatistics fuelObj;

    private static logParse dataLog;



    public OpenLogAnalyzer() {
        this("OpenLog Analyzer");
    }

    public OpenLogAnalyzer(String windowName) {

        //Create
        tabbedPane = new JTabbedPane();
        mainContainer = new JPanel();
        varsContainer = new JPanel();
        //varsContainer = new JScrollPane();
        mainMenu = new LogMenu();
        statusBar = new StatusBar();
        varsScroll = null;
        dataLog = new logParse();
        mainScroll = new JScrollPane();
        playBar = new OpenPlayBar();
        this.main = createMainWindow(windowName);
    }

    public static OpenPlayBar playBar() {
        return playBar;
    }

    public static JPanel getMain() {
        return mainContainer;
    }

    public static logParse getLog() {
        return dataLog;
    }

    public static JPanel getVarsPanel() {
        return varsContainer;
    }

    public static JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /*public static JScrollPane getMainScroll() {
        return mainScroll;
    }*/

    public static StatusBar getStatusBar() {
        return statusBar;
    }

    public static JFrame getBasePane() {
        return main;
    }
    /**
     *
     * @param windowTitle - String Title of the window
     * @return a new JFrame as the base of the application
     */
    public JFrame createMainWindow(String windowTitle) {

        //Create new frames and containers
        main = new JFrame(windowTitle);
        main.setLayout(new MigLayout());
        //Set main windows attributes
        main.setSize(1024, 500);
        main.setPreferredSize(main.getSize());
        main.setResizable(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Begin adding each piece to the window using default BorderLayout
        
        main.getContentPane().add(mainMenu, "dock north");
        main.getContentPane().add(tabbedPane, "dock center");
        main.getContentPane().add(statusBar, "dock south");
        main.getContentPane().add(new OpenPlayBar(), "dock east");
        
        //statusBar.setStatus1("Wtf");

        //give the Main Tab a Name and add it as the first tab
        mainContainer.setName("mainContent");
        mainContainer.setLayout(new MigLayout());
        mainScroll = new JScrollPane(mainContainer);
        mainScroll.setName("Main");
        tabbedPane.add(mainScroll);

        //Ensure that all are set visible
        main.setVisible(true);
        tabbedPane.setVisible(true);
        mainContainer.setVisible(true);

        mainMenu.setVisible(true);
        mainMenu.setName("MenuBar");
        main.repaint();
        return main;
    }

    

}
