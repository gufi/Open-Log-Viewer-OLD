/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogParser;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import javax.swing.JFrame;

/**
 *
 * @author bryan
 */
public class logParse extends ArrayList<GraphData> {

    String DELIM = "\t";
    final String DELIM_COMMA = ",";
    private String logFile;
    //private ArrayList<ArrayList<String>> newList;
    private String ecuType;
    private Boolean logLoaded;

    /**
     *
     * @param f
     */
    public logParse() {
        ecuType = "None Loaded";
        logFile = "";
        logLoaded = false;
    }

    ////////GETTERS AND SETTERS
    public String getLogFile() {
        return logFile;
    }

    public String getEcuType() {
        return ecuType;
    }

    private void setEcuType(String ecuType) {
        this.ecuType = ecuType;
    }

    public Boolean isLogLoaded() {
        return logLoaded;
    }

    private void setLogLoaded(Boolean logLoaded) {
        this.logLoaded = logLoaded;
    }

    public void newLog() {
        if (isLogLoaded()) {
            this.removeRange(0, this.size());//dunno how to use removeall ... so i used this
        }
        FileDialog fd = new FileDialog(new JFrame(), "Choose a Log File", FileDialog.LOAD);
        fd.setFile("*.*");
        fd.setDirectory("/home/bryan/Desktop/DataLogs/");
        fd.setLocation(50, 50);
        fd.setVisible(true);
        logFile = fd.getDirectory();
        logFile += fd.getFile();


        DELIM = "\t";//set/reset default delimiter

        try {
            File file = new File(logFile);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));


                this.setEcuType(br.readLine());// This line is specific to MTX/TS datalogs
                StringTokenizer st = null;


                String readLine = br.readLine();// get the first line of variable names, aka the headers
                st = new StringTokenizer(readLine, DELIM);

                if (st.countTokens() <= 1) {
                    st = new StringTokenizer(readLine, DELIM_COMMA);
                    DELIM = DELIM_COMMA;
                }

                int tokenCount = st.countTokens();
                if (tokenCount > 0) {
                    for (int i = 0; i < tokenCount; i++) { //set all the Graph Data Names

                        this.add(new GraphData(st.nextToken()));

                    }
                    while ((readLine = br.readLine()) != null) { // get all the other data and start adding it to each Graph Data Object
                        st = new StringTokenizer(readLine, DELIM);
                        for (int i = 0; i < tokenCount && st.hasMoreTokens(); i++) {

                            this.get(i).add(st.nextToken());

                        }
                    }
                    GraphData afrdiff = this.compareGraphs("AFR", "AFR Target 1", 1);
                    //afrdiff.setMax(500);
                    //afrdiff.setMin(500);
                    this.add(afrdiff);
                    Collections.sort(this);

                    setLogLoaded(true);
                } else {
                    setLogLoaded(false);
                }

            }
        } catch (IOException IOE) {
            System.out.println("IOE: " + IOE.toString());
            setLogLoaded(false);
        }
        System.out.println("Log Loaded:" + isLogLoaded());
    }

    @Override
    public boolean add(GraphData g) {
        if (g != null) {
            return super.add(g);
        } else {
            return false;
        }
    }

    public GraphData getGraphData(String header) {
        int index = getIndex(header);
        if (index > -1) {
            GraphData gd = (GraphData) this.get(index);
            return gd;
        } else {
            return null;
        }
    }

    public int getIndex(String token) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().equals(token)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        //ArrayList lines = this.get(0); // ONLY RETURN THE HEAD ROW OF A FILE
        String line = "";
        for (int i = 0; i < this.size(); i++) {
            line += (String) this.get(0).getName() + " ";
        }
        return line;
    }

    public GraphData compareGraphs(String graph1, String graph2) {
        return compareGraphs(graph1, graph2, 1);
    }

    public GraphData compareGraphs(String graph1, String graph2, int scale) {
        GraphData g1 = getGraphData(graph1);
        GraphData g2 = getGraphData(graph2);
        if (g1 != null && g2 != null) {
            GraphData differenceGraph = new GraphData(graph1 + " vs " + graph2);

            Double g1val;
            Double g2val;

            Double difference = 0.0;
            for (int i = 0; i < g1.size(); i++) {
                g1val = Double.parseDouble(g1.get(i));
                g2val = Double.parseDouble(g2.get(i));
                if (g1.getMax() > g2.getMax()) {
                    difference = g1val - (g2val * scale);
                } else if (g1.getMax() < g2.getMax()) {
                    difference = (g1val * scale) - g2val;
                }

                differenceGraph.add(difference.toString());
            }
            return differenceGraph;
        } else {
            return null;
        }


    }


}
