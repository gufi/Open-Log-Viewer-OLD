/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Graphing.PlayableGraph;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import LogParser.logParse;
import LogParser.GraphData;
import OpenLogReader.OpenLogAnalyzer;
import OpenLogReader.OpenPlayBar;
import OpenLogReader.StatusBar;
import javax.swing.JFrame;

/**
 *
 * @author bryan
 */
public class OpenListener extends MouseAdapter {

    OpenLogAnalyzer mainRef;
    logParse dataLog;
    JTabbedPane tabbedPane;
    StatusBar statusBar;
    JPanel varsContainer;
    JPanel mainContainer;
    JScrollPane varsScroll;
    JFrame main;

    public OpenListener() {
        super();
    }

    public void mouseReleased(java.awt.event.MouseEvent evt) {
        dataLog = OpenLogAnalyzer.getLog();
        tabbedPane = OpenLogAnalyzer.getTabbedPane();
        statusBar = OpenLogAnalyzer.getStatusBar();
        main = OpenLogAnalyzer.getBasePane();
        
        varsContainer = OpenLogAnalyzer.getVarsPanel();
        mainContainer = OpenLogAnalyzer.getMain();

        ///dataLog = null;

        dataLog.newLog();

        if (varsScroll != null) {
            tabbedPane.remove(varsScroll);
        }
        else varsScroll = null;


        if (dataLog.isLogLoaded()) {

            statusBar.setStatus1(dataLog.getLogFile());
            statusBar.setStatus2(dataLog.getEcuType());
            if (varsScroll != null) {
                varsContainer.removeAll();
                mainContainer.removeAll();

            }
            VarsTabListeners.createVarCheckBoxes();

            //give the Vars Tab a Name and add it as the second tab
            //varsContainer.setName("Test");
            varsScroll = new JScrollPane(varsContainer);
            varsScroll.setName("Vars");
            tabbedPane.add(varsScroll,1);

            PlayableGraph graph = new PlayableGraph();
            
            GraphData graphData;

            graphData = dataLog.getGraphData("RPM");
            graphData.setColor(Color.yellow);
            graph.addGraph(graphData);

            graphData = dataLog.getGraphData("TP");
            graphData.setColor(Color.blue);
            graph.addGraph(graphData);

            graphData = dataLog.getGraphData("Time");
            graphData.setColor(Color.green);
            graph.addGraph(graphData);

            graphData = dataLog.getGraphData("MAP");
            graphData.setColor(Color.red);
            graph.addGraph(graphData);
            graph.setVisible(true);


            mainContainer.add(graph, "wrap");// first playable graph added


            graph = new PlayableGraph();
            


            graphData = dataLog.getGraphData("PW");
            graphData.setColor(Color.yellow);
            graph.addGraph(graphData);

            graphData = dataLog.getGraphData("MAT");
            graphData.setColor(Color.blue);
            graph.addGraph(graphData);

            graphData = dataLog.getGraphData("Batt V");
            graphData.setColor(Color.green);
            graph.addGraph(graphData);

            graphData = dataLog.getGraphData("AFR");
            graphData.setColor(Color.red);
            graph.addGraph(graphData);

            graphData = dataLog.getGraphData("AFR");
            graphData.setColor(Color.CYAN);
            graph.addGraph(graphData);
            graph.setVisible(true);


            //Static methods that will be accessed by ALL graphs
            //graph.setPlaybackSpeed(22);
            

            mainContainer.add(graph); // second playable graph
            tabbedPane.setSelectedIndex(0);
            //graph.play();
            
           
        }
    }
}