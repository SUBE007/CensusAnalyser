package com.censusdata.dto;

import com.opencsv.bean.CsvBindByName;

public class StateCensusCSVDTO {

    public StateCensusCSVDTO(){ }

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public long population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public Double areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public Double densityPerSqKm;

    public StateCensusCSVDTO(String state, long population, Double areaInSqKm, Double densityPerSqKm) {

        this.state = state;
        this.areaInSqKm = areaInSqKm;
        this.population = population;
        this.densityPerSqKm = densityPerSqKm;

    }
}
