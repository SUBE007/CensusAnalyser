package com.censusdata;

public class CensusCSVAdapterFactory {

    public static CensusCSVAdapter getCensusData(StateCensusAnalyser.COUNTRY country) {
        if (country.equals(StateCensusAnalyser.COUNTRY.INDIA))
            return new IndiaCensusCSVAdapter();
        if (country.equals(StateCensusAnalyser.COUNTRY.US))
            return new USCensusCSVAdapter();
        return null;
    }
}
