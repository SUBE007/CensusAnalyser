package com.censusdata;

public class CensusCSVAdapterFactory {

    public static CensusCSVAdapter getCensusData(StateCensusAnalyser.COUNTRY country) throws CensusAnalyserException {
        switch (country) {
            case INDIA:
                return new IndiaCensusCSVAdapter();
            case US:
                return new USCensusCSVAdapter();
            default:
                throw new CensusAnalyserException("Incorrect Country entered",
                                  CensusAnalyserException.CensusExceptionType.INCORRECT_COUNTRY_ENTERED);

        }
    }
}
