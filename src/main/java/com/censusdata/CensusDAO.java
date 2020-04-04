package com.censusdata;

import java.util.Comparator;

public class CensusDAO {

    public String state, stateCode, stateName;
    public int population;
    private Integer srNo, tin;
    public double areaInSqKm, densityPerSqKm;

    public CensusDAO(StateCensusCsv indiaCensusCSV) {
        state = indiaCensusCSV.stateName;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(StateCodeCSV indiaCodeCSV) {
        srNo = indiaCodeCSV.srNo;
        stateCode = indiaCodeCSV.stCode;
        tin = indiaCodeCSV.tin;
        stateName = indiaCodeCSV.stName;
    }

    public CensusDAO(USCensusCSV censusCSV) {
        stateCode = censusCSV.stateId;
        areaInSqKm = censusCSV.area;
        state = censusCSV.state;
        densityPerSqKm = censusCSV.populationDensity;
        population = censusCSV.population;
    }

    public static Comparator<? super CensusDAO> getSortComparator(StateCensusAnalyser.SortingMode mode) {
        if (mode.equals(StateCensusAnalyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.state);
        if (mode.equals(StateCensusAnalyser.SortingMode.STATECODE))
            return Comparator.comparing(census -> census.stateCode);
        if (mode.equals(StateCensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(CensusDAO::getPopulation).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(CensusDAO::getPopulationDensity).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(CensusDAO::getTotalArea).reversed();
        return null;
    }

    private double getTotalArea() {
        return areaInSqKm;
    }

    private double getPopulationDensity() {
        return this.densityPerSqKm;
    }

    private double getPopulation() {
        return this.population;
    }

    public Object getCensusDTO(StateCensusAnalyser.COUNTRY country) {
        if (country.equals(StateCensusAnalyser.COUNTRY.INDIA))
            return new StateCensusCsv(state, population, areaInSqKm, densityPerSqKm);
        if (country.equals(StateCensusAnalyser.COUNTRY.US))
            return new USCensusCSV(state, stateCode, population, areaInSqKm, densityPerSqKm);
        return null;
    }
}
