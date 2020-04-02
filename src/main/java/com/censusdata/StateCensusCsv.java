package com.censusdata;

import com.opencsv.bean.CsvBindByName;

public class StateCensusCsv {

    @CsvBindByName(column = "State")
    public String stateName;

    @CsvBindByName(column = "Population",required = true)
    public String population;

    @CsvBindByName(column = "AreaInSqKm")
    public String areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public String densityPerSqKm;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public void setDensityPerSqKm(String densityPerSqKm) {
        this.densityPerSqKm = densityPerSqKm;
    }

    @Override
    public String toString() {
        return  "stateName='" + stateName + '\'' +
                ", population='" + population + '\'' +
                ", areaInSqKm='" + areaInSqKm + '\'' +
                ", densityPerSqKm='" + densityPerSqKm + '\''
                +"\n";
    }
}
