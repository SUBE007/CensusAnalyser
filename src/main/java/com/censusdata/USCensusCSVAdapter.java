package com.censusdata;

import java.util.Map;

public class USCensusCSVAdapter extends CensusCSVAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusCSVData(String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        return super.loadCensusCSVData(USCensusCSV.class, csvFilePath[0]);
    }
}
