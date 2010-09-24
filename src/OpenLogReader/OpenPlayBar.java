/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OpenLogReader;

import Graphing.PlayableGraph;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author bryan
 */
public class OpenPlayBar extends JPanel {

    private JButton pause;
    private JButton play;
    private JButton plus;
    private JButton minus;
    private JButton zoomPlus;
    private JButton zoomMinus;
    private JButton reset;
    private JTextField zoomFactor;
    private JButton forward;
    private JButton back;

    public OpenPlayBar() {
       super();
       pause = new JButton("Pause");
       play = new JButton("Play");
       plus  = new JButton("+");
       minus = new JButton("-");
       zoomPlus = new JButton("Zoom -->");
       zoomMinus = new JButton("Zoom Out");
       reset = new JButton("Reset");
       forward = new JButton(">>");
       back = new JButton("<<");
       zoomFactor = new JTextField();
       zoomFactor.setColumns(2);
       zoomFactor.setText("1");
       zoomFactor.setToolTipText("Default: 1");

       setLayout(new MigLayout("wrap"));

       add(pause,"split 2");
       add(play);
       add(plus,"split 2");
       add(minus);
       add(zoomPlus,"split 2");
       //add(zoomMinus);
       add(zoomFactor);
       add(back,"split 2");
       add(forward);
       add(reset);

       play.addMouseListener(addPlayMethod());
       pause.addMouseListener(addPauseMethod());
       reset.addMouseListener(addResetMethod());
       zoomPlus.addMouseListener(addZoomMethod());
       plus.addMouseListener(addPlusMethod());
       minus.addMouseListener(addMinusMethod());
       back.addMouseListener(addBackMethod());


       this.setVisible(true);
    }

    private MouseAdapter addPlayMethod() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                System.out.println("Play");
                PlayableGraph.play();
            }
        };
        return mouseAdapter;
    }

    private MouseAdapter addPauseMethod() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                System.out.println("Pause");
                PlayableGraph.pause();
            }
        };
        return mouseAdapter;
    }

    private MouseAdapter addResetMethod() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                System.out.println("Reset");
                PlayableGraph.reset();
            }
        };
        return mouseAdapter;
    }

    private MouseAdapter addZoomMethod() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                System.out.println("Zoom:" + zoomFactor.getText() + "x");
                try{
                double zoom = Double.parseDouble(zoomFactor.getText());
                if(zoom >= 1 )PlayableGraph.setZoom((int)zoom);
                else System.out.println("Zoom: Less than 1");
                } catch (NumberFormatException nfe) {
                    System.out.println("Zoom: not a number");
                }
            }
        };
        return mouseAdapter;
    }

    private MouseAdapter addPlusMethod() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                System.out.println("Speed: up " + PlayableGraph.getPlaybackSpeed());
                PlayableGraph.increaseSpeed();
            }
        };
        return mouseAdapter;
    }
    private MouseAdapter addMinusMethod() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                System.out.println("Speed: down " + PlayableGraph.getPlaybackSpeed());
                PlayableGraph.decreaseSpeed();
            }
        };
        return mouseAdapter;
    }

    ////////////////////////////////////////////
    //////////////////////////////////////////
    ////////////////////////////////////////////
    //This is not a true method yet... not fully implemented... Only works with 1x zoom due to zoom factoring in Playable Graph...
    //
    private MouseAdapter addBackMethod() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                System.out.println("Back: " + PlayableGraph.getStartPoint() + " " + PlayableGraph.getTimeLine());
                if(PlayableGraph.getTimeLine() < PlayableGraph.getStaticWidth()/2) {
                    PlayableGraph.setTimeLine(PlayableGraph.getTimeLine()-1);
                }

                PlayableGraph.setStartPoint(PlayableGraph.getStartPoint()-1);
            }
        };
        return mouseAdapter;
    }

}
