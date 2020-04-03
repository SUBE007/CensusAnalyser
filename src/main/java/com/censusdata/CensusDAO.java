package com.censusdata;

public class CensusDAO {

    public static String state;
    public String  areaInSqKm ;
    public String  densityPerSqKm;
    public String population;
    public String stateCode;
    public String srNo;
    public String tin;
    public String stateName;

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
