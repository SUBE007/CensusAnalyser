package com.censusdata;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    USCensusCSV(){ }
    @CsvBindByName (column = "StateId" , required = true)
    public String stateId;

    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population")
    public Integer population;

    @CsvBindByName (column = "HousingUnits" , required = true)
    public  Integer housingUnits;

    @CsvBindByName (column = "TotalArea" , required = true)
    public  Double area;

    @CsvBindByName (column = "WaterArea" , required = true)
    public Double waterArea;

    @CsvBindByName (column = "LandArea" , required = true)
    public Double landArea;

    @CsvBindByName (column = "PopulationDensity" , required = true)
    public   Double populationDensity;

    @CsvBindByName (column = "HousingDensity" , required = true)
    public   Double housingDensity;

    public USCensusCSV(String state, String stateCode, int population, double areaInSqKm, double densityPerSqKm) {
        this.area=areaInSqKm;
        this.state=state;
        this.population=population;
        this.populationDensity=densityPerSqKm;
        this.stateId=stateCode;

    }

}
