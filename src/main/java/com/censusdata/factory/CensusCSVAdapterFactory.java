package com.censusdata.factory;
import com.censusdata.StateCensusAnalyser;
import com.censusdata.StateCensusAnalyser.COUNTRY;
import com.censusdata.adapter.CensusCSVAdapter;
import com.censusdata.adapter.IndiaCensusCSVAdapter;
import com.censusdata.adapter.USCensusCSVAdapter;

public class CensusCSVAdapterFactory {

    public static CensusCSVAdapter getCensusData(COUNTRY country) {
        if (country.equals(StateCensusAnalyser.COUNTRY.INDIA))
            return new IndiaCensusCSVAdapter();
        if (country.equals(COUNTRY.US))
            return new USCensusCSVAdapter();
        return null;
    }
}
