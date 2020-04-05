package com.censusdata.adapter;

import com.censusdata.dao.CensusDAO;
import com.censusdata.dao.USCensusCSVDTO;
import com.censusdata.exception.CSVBuilderException;
import com.censusdata.exception.CensusAnalyserException;

import java.util.Map;

public class USCensusCSVAdapter extends CensusCSVAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusCSVData(String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        Map <String, CensusDAO> censusDAOMap =  super.loadCensusCSVData(USCensusCSVDTO.class,csvFilePath[0]);
        return censusDAOMap;
    }
}
