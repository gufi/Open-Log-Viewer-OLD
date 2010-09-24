/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;
import Graphing.PlayableGraph;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import LogParser.*;
import OpenLogReader.OpenLogAnalyzer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author bryan
 */
public class VarsTabListeners {

    //public static logParse log;
    static logParse dataLog = OpenLogAnalyzer.getLog();
    static JPanel mainContainer = OpenLogAnalyzer.getMain();
    static JPanel varsContainer = OpenLogAnalyzer.getVarsPanel();
    static JFrame main = OpenLogAnalyzer.getBasePane();


    

    public static void createVarCheckBoxes() {

        varsContainer.setName("CheckBoxes");
        MigLayout varsBox = new MigLayout("wrap 8");
        varsContainer.setLayout(varsBox);
        String varName;
        JCheckBox checkBox;
        JComboBox comboBox;
        ArrayList<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
        ArrayList<JComboBox> selectionIndex= new ArrayList<JComboBox>();
        ArrayList<String> indexSelect = new ArrayList<String>();
        indexSelect.add(null);
        for(Integer i = 1; i <= dataLog.size(); i++) {
            indexSelect.add(i.toString());
        }



        for (int i = 0; i < dataLog.size(); i++) {
            checkBox = new JCheckBox();
            comboBox = new JComboBox(indexSelect.toArray());
            varName = dataLog.get(i).getName();

            comboBox.setName(varName);
            comboBox.setSize(20, 20);
            comboBox.setEditable(false);
            selectionIndex.add(comboBox);

            checkBox.setName(varName);
            checkBox.setText(varName);
            checkBox.setVisible(true);
            checkBox.setSelected(false);
            checkBoxList.add(checkBox);

        }

        int horizontal = 5;// Colums before setting wrap
        Dimension d = new Dimension(120, 400);

        //Dynamically add all vars to the pane Horizontal x X rows

        for (int i = 0; i < checkBoxList.size(); i++) {
           varsContainer.add(selectionIndex.get(i));
                varsContainer.add(checkBoxList.get(i));
        }

        // add Selection buttons
        addSelectionButtons(varsContainer);
        varsContainer.setVisible(true);

        //return vars;

    }

    

    private static void addSelectionButtons(JPanel vars) {
        // private static void addSelectionButtons(JScrollPane vars) {
        JButton btn_selectAll = new JButton("Select All");
        JButton btn_removeAll = new JButton("Remove All");
        JButton btn_apply = new JButton("Apply");
        //JButton btn_apply = new JButton("Apply Selection");

        btn_selectAll.setMaximumSize(new Dimension(120, 20));
        btn_removeAll.setMaximumSize(new Dimension(120, 20));
        btn_apply.setMaximumSize(new Dimension(120, 20));

        btn_selectAll.setVisible(true);
        btn_removeAll.setVisible(true);
        btn_apply.setVisible(true);


        vars.add(btn_selectAll, "cell 11 0");
        vars.add(btn_removeAll, "cell 11 1");
        vars.add(btn_apply, "cell 11 2");


        btn_apply.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                logParse dataLog = OpenLogAnalyzer.getLog();
                JPanel mainContainer = OpenLogAnalyzer.getMain();
                JPanel varsContainer = OpenLogAnalyzer.getVarsPanel();

                mainContainer.removeAll();

                setGraphs();
            }
        });



    }

    /**
     * TO-DO: Fuck... make this shit grab the order from the combo boxes
     * then create the graphs
     * then display the graphs in order mainContainer.add(graph,"pos 0 " + graph.getOrder());
     */

    private static void setGraphs() {

        logParse dataLog = OpenLogAnalyzer.getLog();
        JPanel mainContainer = OpenLogAnalyzer.getMain();
        JPanel varsContainer = OpenLogAnalyzer.getVarsPanel();
        ArrayList<PlayableGraph> playableGraphs = new ArrayList<PlayableGraph>();


        JCheckBox check;
        JComboBox combo;
        PlayableGraph graph;
        int min =0;//random color ranges min and max
        int max = 255;
        int maxScale = 200;
        int pos = 0;
        for (int i = 0; i < varsContainer.getComponentCount(); i++) {
            if (varsContainer.getComponent(i) instanceof JCheckBox) {
                // JOptionPane.showMessageDialog(new JFrame(),varsContainer.getComponent(i).getClass())   ;
                check = (JCheckBox) varsContainer.getComponent(i);
                if (check.isSelected()) {
                    //JOptionPane.showMessageDialog(new JFrame(),check.getName());
                    graph = new PlayableGraph();
                    combo = (JComboBox)varsContainer.getComponent(i-1);
                    graph.setOrder(combo.getSelectedIndex()*maxScale);
                    graph.setMaxScale(maxScale);

                    graph.addGraph(dataLog.getGraphData(check.getName()));
                    
                    //pos += maxScale;// not needed once ordering is implemented
                }
            }
        }
    }

    private static int rand(int min,int max) {
        return (int) (Math.random() * (max - min + 1) ) + min;
    }
}
