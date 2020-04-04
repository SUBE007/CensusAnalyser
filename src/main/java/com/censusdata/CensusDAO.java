package com.censusdata;

public class CensusDAO {

    public String state, stateCode,srNo,tin,stateName ;
    public int population;
    public double areaInSqKm, densityPerSqKm;
   public CensusDAO(StateCensusCsv indiaCensusCSV) {
        state = indiaCensusCSV.getStateName();
        areaInSqKm = indiaCensusCSV.getAreaInSqKm();
        densityPerSqKm = indiaCensusCSV.getDensityPerSqKm();
        population = indiaCensusCSV.getPopulation();
    }
    public CensusDAO(StateCodeCSV indiaCodeCSV) {
        srNo = indiaCodeCSV.getSrNo();
        stateCode = indiaCodeCSV.getStateCode();
        tin = indiaCodeCSV.getTin();
        stateName = indiaCodeCSV.getStateName();
    }

    public CensusDAO(USCensusCSV censusCSV) {
        stateCode=censusCSV.getStateId();
        areaInSqKm=censusCSV.getArea();
        state=censusCSV.getState();
        densityPerSqKm=censusCSV.getPopulationDensity();
        population=censusCSV.getPopulation();
    }
}
