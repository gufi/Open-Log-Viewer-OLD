/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LogParser;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author bryan
 */
public class GraphData extends ArrayList<String> implements Comparable {

    //private ArrayList<String> vars;

    private double max;
    private double min;
    private boolean bottom;
    private String name;
    private Color color;

    public GraphData(String name) {
        super();
        this.max = Double.MIN_VALUE;
        this.min = Double.MAX_VALUE;
        this.bottom = false;
        this.name = name;
        this.color = Color.RED;
    }

    /*public GraphData(ArrayList<String> vars) {
        super(vars);
        setMinMax();
        //name = this.get(0);
        this.bottom = true;
        this.color = Color.RED;
        this.name = "";

    }*/

    /*public GraphData(ArrayList<String> vars, Color color) {
        this(vars);
        this.color = color;
    }*/

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMax() {
        return this.max;
    }
    public void setMax(int max) {
        this.max = max;
    }

    public double getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void resetMinMax() {
        setMinMax();
    }

    public boolean isBottom() {
        return this.bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    @Override
    public boolean add(String s) {


       double temp = Double.parseDouble(s);
            if(temp > this.max) {
                this.max = temp;
                //System.out.println("Max: " + this.name + " " + this.max);
            }
            if(temp < this.min) {
                this.min = temp;
                //System.out.println("Min: " + this.name + " " + this.min);
            }
       return super.add(s);
    }

    public void setMinMax() {
        double temp = 0;
        this.max = Double.MIN_VALUE;
        this.min = Double.MAX_VALUE;
        
        for(int i = 1; i < this.size(); i++) {
            temp = Double.parseDouble(this.get(i));
            if(temp > this.max) this.max = temp;
            if(temp < this.min) this.min = temp;
        }
    }

    public int compareTo(Object t) {
        GraphData g2 = (GraphData)t;
        if(this.name.compareTo(g2.getName()) > 0) return 1;
        else if(this.name.compareTo(g2.getName()) < 0) return -1;
        else return 0;
    }




}
