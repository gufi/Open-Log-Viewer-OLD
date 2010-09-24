/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import LogParser.logParse;

/**
 *
 * @author Bryan
 */
public class FuelStatistics {

    private Double totalFuelUsed;
    private Double tireSize;
    private Double rearRatio;
    private Double gearRatio;
    private Double injectorSize;
    private Boolean injectorLBH;
    private Double startTime;
    private Double endTime;
    private Integer numInjectors;
    private Integer injFiringSystem;
    final public Integer ALTERNATING = 999;
    final public Integer SEQUENTIAL = 998;
    final public Integer ALL = 997;
    final private Double FUEL_WEIGHT_PER_GALLON = 6.0;

    public FuelStatistics() {
        this.totalFuelUsed = 0.0;
        this.tireSize = 27.0;
        this.rearRatio = 3.73;
        this.gearRatio = 1.0;
        this.injectorSize = 39.0;
        this.injectorLBH = true;
        this.startTime = 0.0;
        this.endTime = 0.0;
        this.numInjectors = 8;
        this.setInjFiringSystem(ALTERNATING);
    }

    public FuelStatistics(Double totalFuelUsed, Double tireSize, Double rearRatio, Double gearRatio, Double injectorSize, Integer numInjectors, Integer injFiringSystem, Double startTime, Double endTime, Boolean injectorLBH) {
        this.totalFuelUsed = totalFuelUsed;
        this.tireSize = tireSize;
        this.rearRatio = rearRatio;
        this.gearRatio = gearRatio;
        this.injectorSize = injectorSize;
        this.injectorLBH = injectorLBH;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numInjectors = numInjectors;
        setInjFiringSystem(injFiringSystem);
    }

    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }

    public Double getGearRatio() {
        return gearRatio;
    }

    public void setGearRatio(Double gearRatio) {
        this.gearRatio = gearRatio;
    }

    public Boolean getInjectorLBH() {
        return injectorLBH;
    }

    public void setInjectorLBH(Boolean injectorLBH) {
        this.injectorLBH = injectorLBH;
    }

    public Double getInjectorSize() {
        return injectorSize;
    }

    public void setInjectorSize(Double injectorSize) {
        this.injectorSize = injectorSize;
    }

    public Double getRearRatio() {
        return rearRatio;
    }

    public void setRearRatio(Double rearRatio) {
        this.rearRatio = rearRatio;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public Double getTireSize() {
        return tireSize;
    }

    public void setTireSize(Double tireSize) {
        this.tireSize = tireSize;
    }

    public Double getTotalFuelUsed() {
        return totalFuelUsed;
    }

    public void setTotalFuelUsed(Double totalFuelUsed) {
        this.totalFuelUsed = totalFuelUsed;
    }

    private void increaseTotalFuelUsed(Double totalFuelUsed) {
        this.totalFuelUsed += totalFuelUsed;
    }

    public Integer getNumInjectors() {
        return numInjectors;
    }

    public void setNumInjectors(Integer numInjectors) {
        this.numInjectors = numInjectors;
    }

    public Integer getInjFiringSystem() {
        return injFiringSystem;
    }

    public void setInjFiringSystem(Integer injFiringSystem) {
        if (injFiringSystem == this.ALL) {
            this.injFiringSystem = getNumInjectors();
        } else if (injFiringSystem == this.ALTERNATING) {
            this.injFiringSystem = getNumInjectors() / 2;
        } else if (injFiringSystem == this.SEQUENTIAL) {
            this.injFiringSystem = 1;
        }

    }

    /**********************************************
     * END SETTERS AND GETTERS
     * END SETTERS AND GETTERS
     **********************************************/
    private int getIndex(ArrayList<String> al, String token) {
        for (int i = 0; i < al.size(); i++) {
            if (al.get(i).equals(token)) {
                return i;
            }
        }
        return -1;
    }


    /**
     *
     * @param lp - logParse
     * This function creates a new Jframe with an approx
     */
    public void getTotalFuelConsumption(logParse lp) {
        /*if (lp != null) {
            
            int pwIndex = this.getIndex(headers, "PW");
            int timeIndex = this.getIndex(headers, "Time");

            JFrame fuelConsumption = new JFrame("Total Fuel Consumption");
            fuelConsumption.setSize(new Dimension(600, 75));


            JPanel TFC = new JPanel();
            TFC.setVisible(true);

            JLabel lbl_totalFuel = new JLabel("Total Lbs of fuel burned:");
            lbl_totalFuel.setVisible(true);

            JLabel lbl_totalFuelAnswer = new JLabel("Please Wait...");
            lbl_totalFuelAnswer.setVisible(true);

            JLabel lbl_totalTime = new JLabel("Total Time");
            lbl_totalTime.setVisible(true);

            JLabel lbl_totalTimeAnswer = new JLabel("Please Wait...");
            lbl_totalTimeAnswer.setVisible(true);


            TFC.add(lbl_totalFuel);
            TFC.add(lbl_totalFuelAnswer);
            TFC.add(lbl_totalTime);
            TFC.add(lbl_totalTimeAnswer);
            fuelConsumption.add(TFC);
            fuelConsumption.setVisible(true);

            setStartTime(Double.parseDouble(lp.getNewList().get(1).get(timeIndex)));
            for (int i = 1; i < lp.getNewList().size(); i++) {
                increaseTotalFuelUsed(Double.parseDouble(lp.getNewList().get(i).get(pwIndex)));
                setEndTime(Double.parseDouble(lp.getNewList().get(i).get(timeIndex)));
            }

            Double timeTotal = ((getEndTime() - getStartTime()) / 60) / 60;
            lbl_totalTimeAnswer.setText(doubleToTime(timeTotal));
            /*
            This section is pissing me off
             *
             * Data required to determine total fuel consumed
             * Number of injectors getNumInjectors()
             * Size of injectors ( rated at 1 hr )
             * Firing system ( Bank, Seq, All )
             * Total Time running
             *
             *
            Double answer = 0.0;
            answer = (getTotalFuelUsed() / 1000) / lp.getNewList().size();
            answer = (this.getInjectorSize() * answer) * (60 * timeTotal);
            lbl_totalFuelAnswer.setText(answer.toString());


        }*/
    }

    private String doubleToTime(Double time) {

        double doubTime = time;
        int hours = (int) doubTime;
        doubTime = doubTime - hours;
        int mins = (int) (doubTime * 60);

        return hours + ":" + mins;
    }
}
