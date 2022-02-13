package jh.hw;

import java.util.List;

public class Program {
    public String programName;
    public List<Concentration> concentrations;

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<Concentration> getConcentrations() {
        return concentrations;
    }

    public void setConcentrations(List<Concentration> concentrations) {
        this.concentrations = concentrations;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programName='" + programName + '\'' +
                ", concentrations=" + concentrations +
                '}';
    }
}