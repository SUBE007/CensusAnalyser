package com.censusdata;

public class CensusDAO {

    public String state, areaInSqKm, densityPerSqKm,population,stateCode,srNo,tin,stateName ;

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
}
