package com.censusdata;

import com.opencsv.bean.CsvBindByName;

public class StateCensusCsv {

    @CsvBindByName(column = "State")
    public String stateName;

    @CsvBindByName(column = "Population",required = true)
    public Integer population;

    @CsvBindByName(column = "AreaInSqKm")
    public Double areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public Double densityPerSqKm;

    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getPopulation() {
        return population;
    }
    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getAreaInSqKm() {
        return areaInSqKm;
    }
    public void setAreaInSqKm(Double areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public Double getDensityPerSqKm() {
        return densityPerSqKm;
    }
    public void setDensityPerSqKm(Double densityPerSqKm) {
        this.densityPerSqKm = densityPerSqKm;
    }

    @Override
    public String toString() {
        return  "StateName='" + stateName + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\''
                +"\n";
    }
}
