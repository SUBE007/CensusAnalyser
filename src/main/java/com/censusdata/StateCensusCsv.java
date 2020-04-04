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

    public StateCensusCsv(String state, int population, double areaInSqKm, double densityPerSqKm) {
        this.stateName = state;
        this.areaInSqKm = areaInSqKm;
        this.population = population;
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
