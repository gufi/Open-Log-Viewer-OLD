/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import LogParser.GraphData;
import OpenLogReader.OpenLogAnalyzer;
import java.text.DecimalFormat;

/**
 *
 * @author bryan
 */
public class PlayableGraph extends JPanel implements Runnable {

    private ArrayList<GraphData> graphIndex;
    // private static singleVar time;
    ///////////////////////////////////////////////////////////////////////////////////////////Playback Attributes
    private static Thread play;
    private static boolean pause; // make the thread controlable
    private static int timeLine; // position of the current value, vertical line on graph will follow this
    private static int startPoint; // where to start drawing the graph from
    private static int playbackSpeed; // Speed that the graph will play at between each value
    private static int size;
    private static int totalGraphs;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////Scale Attributes
    //private double max; // commented out because graph max is held in each graph dataset
    //private double min; // commented out because graph max is held in each graph dataset
    private static int zoom;
    private static int width; // shared between all individual graphs
    private static int maxScale;// shared between all individual graphs
    private static Dimension prefSize;// shared between all playable graphs
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////Decoration Attributes
    private int order;
    private Color color; // graph and text color
    private int textOffset; // For future use of when there are more graphs than can be displayed.( + 500 to move text to the right mebbe?
    private String current;
    DecimalFormat df;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static PlayableGraph getThis;

    public PlayableGraph(GraphData newGraph) {
        this();
        this.addGraph(newGraph);
    }

    public PlayableGraph() {

        super();
        graphIndex = new ArrayList<GraphData>();
        ///////////////////////////////////////////////////////////////////////////////////////////Playback Attributes
        play = null;
        this.pause = true;
        this.timeLine = 0; // always start from 0 Position of the vertical time line bar
        this.startPoint = 0; // always start from 0 as this is where the graph is STARTING from on the left side
        this.playbackSpeed = 100; // Speed is in MS, 1000 = 1 second
        this.size = 0;

        ////////////////////////////////////////////////////////////////////////////////////////////END Playback Attributes

        ////////////////////////////////////////////////////////////////////////////////////////////Scale Attributes
        this.zoom = 1;
        ////////////////////////////////////////////////////////////////////////////////////////////END Scale Attributes

        ////////////////////////////////////////////////////////////////////////////////////////////Size Attributes
        this.maxScale = 300;//change this later to be applied by constructor
        this.width = 798; // Change this from static 800 eventually
        this.prefSize = new Dimension(width, maxScale);
        this.setMaxScale(maxScale);
        ////////////////////////////////////////////////////////////////////////////////////////////END Size Attributes

        ////////////////////////////////////////////////////////////////////////////////////////////Decoration Attributes
        df = new DecimalFormat("#.##");
        this.textOffset = 1;
        this.current = "";
        this.order = 1;
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getThis = this;
    }

    public static PlayableGraph getThis() {
        return getThis;
    }

    public void addGraph(GraphData v) {
        graphIndex.add(v);
        this.size = this.graphIndex.get(0).size();
        this.width = 798;// must be even or it causes out of bounds issues with the time line... need to ad a modulus to the time line to correct that...
    }

    public void addGraph(GraphData v, int max, int min) {
        graphIndex.add(v);
        this.size = this.graphIndex.get(0).size();
        v.setMax(max);
        v.setMin(min);

    }

    public void removeAllGraphs() {
        //remove all graphs from the array
        int remove = graphIndex.size() - 1;// size is always 1 higher than the indexmax
        for (int i = remove; i >= 0; i--) {
            graphIndex.remove(i);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        // if there are no graphs... draw nothing oh... and not paused
        if (this.startPoint >= this.graphIndex.get(0).size() || !(this.graphIndex.size() > 0)) {
            pause();
            if (this.graphIndex.size() > 0) {
                this.startPoint--;//This is a workaround... find out why your getting an extra 1 in there causeing an NPE
            }
            System.out.println("paused: end of play");
        }
        if (!this.getPrefSize().equals(this.getSize())) {
            this.setMaxScale(maxScale);// not sure if this even works but meh
        }
        g = drawBackground(g);
        g = playback(g);
        g = drawGraph(g);
        g = drawBorder(g);

    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static int getStaticWidth() {
        return width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<GraphData> getGraphIndex() {
        return graphIndex;
    }

    public void setGraphIndex(ArrayList<GraphData> graphIndex) {
        this.graphIndex = graphIndex;
    }

    public int getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(int maxScale) {
        this.maxScale = maxScale;
        this.prefSize = new Dimension(width, maxScale);
        this.setPreferredSize(prefSize);
        this.setSize(prefSize);
        this.setMaximumSize(prefSize);
        this.setMinimumSize(prefSize);

    }

    public boolean isPaused() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public Thread getPlay() {
        return play;
    }

    public void setPlay(Thread play) {
        this.play = play;
    }

    public static int getPlaybackSpeed() {
        return playbackSpeed;
    }

    public static void setPlaybackSpeed(int p) {
        playbackSpeed = p;
    }

    public static void increaseSpeed() {
        if (playbackSpeed - 10 > 0) {
            playbackSpeed -= 10;
        } else if (playbackSpeed - 1 > 0) {
            playbackSpeed -= 1;
        }
    }

    public static void decreaseSpeed() {
        if (playbackSpeed < 10) {
            playbackSpeed += 1;
        } else {
            playbackSpeed += 10;
        }

    }

    public Dimension getPrefSize() {
        return prefSize;
    }

    public void setPrefSize(Dimension prefSize) {
        this.prefSize = prefSize;
    }

    public static int getStartPoint() {
        return startPoint;
    }

    public static void setStartPoint(int sp) {
        startPoint = sp * getZoom();
    }

    public int getTextOffset() {
        return textOffset;
    }

    public void setTextOffset(int textOffset) {
        this.textOffset = textOffset;
    }

    public static int getTimeLine() {
        return timeLine;
    }

    public static void setTimeLine(int tl) {
        timeLine = tl * getZoom();
    }

    public static int getZoom() {
        return zoom;
    }

    public static void setZoom(int z) {
        /*int tempStartPoint = (int)getStartPoint()/getZoom();
        int tempTimeLine = (int)getTimeLine()/getZoom();


        
        if(tempStartPoint < width/2) timeLine = */


        zoom = z;
        getThis().getParent().repaint();
    }

    private int getInfoPosition(int i) {
        return (i + 1) * 12;
    }

    private Graphics drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, this.maxScale - 1);
        return g;
    }

    private Graphics drawGraph(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawLine(0, (int) (this.getMaxScale() / 2), width, (int) (this.getMaxScale() / 2));
        for (int i = 0; i < graphIndex.size(); i++) { //loop through the array of graphs and

            GraphData tmpGraph = graphIndex.get(i);
            double min = tmpGraph.getMin();
            double max = tmpGraph.getMax();


            //Initialize the begining of the left side of the graph
            double doubleTo = Double.parseDouble(tmpGraph.get(this.startPoint));
            double toForward = this.maxScale - (int) (this.maxScale * ((doubleTo - min) / (max - min)));
            double toBackward = toForward;
            double fromBackward = toForward;
            double fromForward = toForward;

            g.setColor(tmpGraph.getColor());
            int j = 0;

            while (j/getZoom() <= width/2+getZoom()) {
                if (this.getStartPoint() + j/getZoom() < (tmpGraph.size() - 1)) {
                    
                    doubleTo = Double.parseDouble(tmpGraph.get(this.startPoint + (j/getZoom())));// get data from the graphforward
                    toForward = this.maxScale - (int) (this.maxScale * (doubleTo - min) / (max - min)); // scale said data
                    if(j != 0) g.drawLine((int) (width / 2) + j - getZoom(), (int) fromForward, (int) (width / 2) + j , (int) toForward); // draw the line
                    else g.drawLine((int) (width / 2) + j , (int) fromForward, (int) (width / 2) + j, (int) toForward); // draw the line
                    fromForward = toForward;// give the point a point back to know where to draw from
                }
                if (this.startPoint - j/getZoom() >= 0) {
                    
                    doubleTo = Double.parseDouble(tmpGraph.get(this.startPoint - (j/getZoom())));// get data from the graphbackward
                    toBackward = this.maxScale - (int) (this.maxScale * (doubleTo - min) / (max - min));// scale the backwards value
                    if(j != 0) g.drawLine((int) (width / 2) - j + getZoom(), (int) fromBackward, (int) (width / 2) - j , (int) toBackward); // draw the line
                    else g.drawLine((int) (width / 2) - j, (int) fromBackward, (int) (width / 2) - j, (int) toBackward); // draw the Middle point
                    fromBackward = toBackward;
                }
                j= j + getZoom();
            }


            // Draw the graph data

            g.drawString(tmpGraph.getName(), this.getTextOffset() + 5, this.getInfoPosition(i));
            g.drawString("Max " + tmpGraph.getMax(), this.getTextOffset() + 130, this.getInfoPosition(i));
            g.drawString("Min " + tmpGraph.getMin(), this.getTextOffset() + 230, this.getInfoPosition(i));
            g.drawString("Mid " + (df.format((tmpGraph.getMax() + tmpGraph.getMin()) / 2)), this.getTextOffset() + 320, this.getInfoPosition(i));

            if (this.startPoint < tmpGraph.size()) {
                current = "Current " + tmpGraph.get((int) (this.startPoint));
            } else {//This else is a workaround... for some reason the graphing system causes an out of bounds issue at the end of the playback
                current = "Current " + tmpGraph.get(tmpGraph.size() - 1);
            }

            g.drawString(current, this.getTextOffset() + 400, this.getInfoPosition(i));


        }
        return g;
    }

    private Graphics drawBorder(Graphics g) {

        g.drawRect(0, 0, width - 1, this.getMaxScale() - 1);
        return g;

    }

    private Graphics playback(Graphics g) {



        //g.setColor(Color.darkGray);

        //g.fillRect((int) (width / 2) - 2, 0, 5, this.getMaxScale());
       /// g.setColor(Color.GRAY);
       // g.fillRect((int) (width / 2) - 1, 0, 3, this.getMaxScale());
        g.setColor(Color.WHITE);
        g.drawLine((int) (width / 2), 0, (int) (width / 2), this.getMaxScale());
        return g;

    }

    public void run() {
        // while (true) {
        try {

            while (!pause && (startPoint) < size - 1) {
                OpenLogAnalyzer.getMain().repaint();
                startPoint++;
                play.sleep(playbackSpeed);
            }

        } catch (InterruptedException ex) {
            System.out.println("fuck");
        }
        //}
    }

//These functions deal with attributes of the thread
    public static void play() {
        if (play == null) {
            pause = false;
            play = new Thread(getThis());
            play.start();
        }
    }

    public static void pause() {
        if (play != null) {
            pause = true;
            play = null;
        }
    }

    public static boolean isPlaying() {
        return !pause;
    }

    public static void reset() {
        startPoint = 0;
        timeLine = 0;
        if (pause) {
            getThis().getParent().repaint();
        }
    }

    public String toString() {
        return this.getName();
    }
}
