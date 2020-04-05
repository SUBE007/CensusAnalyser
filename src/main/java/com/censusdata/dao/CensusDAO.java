package com.censusdata.dao;

import java.util.Comparator;
import com.censusdata.StateCensusAnalyser.SortingMode;
import com.censusdata.StateCensusAnalyser.COUNTRY;
import com.censusdata.dto.StateCensusCSVDTO;
import com.censusdata.StateCensusCsv;
import com.censusdata.StateCodeCSV;
import com.censusdata.USCensusCSV;

public class CensusDAO {

    public String state, stateCode, stateName;
    public long population;
    public Integer srNo, tin;
    public double areaInSqKm, densityPerSqKm;
    public Double populationDensity,totalArea;

    public CensusDAO(StateCensusCSVDTO indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

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
    public CensusDAO(USCensusCSVDTO usCSVDTO) {
        stateCode=usCSVDTO.stateId;
        totalArea=usCSVDTO.totalArea;
        state=usCSVDTO.state;
        populationDensity=usCSVDTO.populationDensity;
        population=usCSVDTO.population;
    }

    public static Comparator<? super CensusDAO> getSortComparator(SortingMode mode) {
        if (mode.equals(SortingMode.STATE))
            return Comparator.comparing(census -> census.state);
        if (mode.equals(SortingMode.STATECODE))
            return Comparator.comparing(census -> census.stateCode);
        if (mode.equals(SortingMode.POPULATION))
            return Comparator.comparing(CensusDAO::getPopulation).reversed();
        if (mode.equals(SortingMode.DENSITY))
            return Comparator.comparing(CensusDAO::getPopulationDensity).reversed();
        if (mode.equals(SortingMode.AREA))
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

    public Object getCensusDTO(COUNTRY country) {
        if (country.equals(COUNTRY.INDIA))
            return new StateCensusCSVDTO(state, population, areaInSqKm, densityPerSqKm);
        if (country.equals(COUNTRY.US))
            return new USCensusCSVDTO(state, stateCode, population, areaInSqKm, densityPerSqKm);
        return null;
    }
}
