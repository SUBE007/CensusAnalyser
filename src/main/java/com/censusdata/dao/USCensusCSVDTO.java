package com.censusdata.dao;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSVDTO {

    public USCensusCSVDTO(){}

    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "StateId")
    public String stateId;

    @CsvBindByName(column = "Population")
    public long population;

    @CsvBindByName(column = "TotalArea")
    public double totalArea;

    @CsvBindByName(column = "PopulationDensity")
    public double populationDensity;

    public USCensusCSVDTO(String state, String stateCode, long population, double populationDensity, double totalArea) {
        this.state = state;
        this.population = population;
        this.populationDensity = populationDensity;
        this.totalArea = totalArea ;
        this.stateId = stateCode;
    }
}
