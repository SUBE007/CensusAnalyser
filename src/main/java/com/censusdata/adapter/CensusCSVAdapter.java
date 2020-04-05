package com.censusdata.adapter;

import com.censusdata.factory.CSVBuilderFactory;
import com.censusdata.ICSVBuilder;
import com.censusdata.StateCensusCsv;
import com.censusdata.dao.CensusDAO;
import com.censusdata.dao.USCensusCSVDTO;
import com.censusdata.exception.CSVBuilderException;
import com.censusdata.exception.CensusAnalyserException;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusCSVAdapter {
    public abstract Map<String, CensusDAO> loadCensusCSVData(String... csvFilePath) throws CSVBuilderException, CensusAnalyserException;
    <E> Map<String, CensusDAO> loadCensusCSVData(Class<E> censusCSVClass, String... getPath) throws CSVBuilderException, CensusAnalyserException {
        Map<String, CensusDAO> censusDAOMap = new HashedMap();
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath[0]))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> censusCSVIterator;
            if (censusCSVClass.getName().equals("com.censusdata.StateCensusCsv"))
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(StateCensusCsv.class::cast)
                        .forEach(StateCensusCsv -> censusDAOMap.put(StateCensusCsv.stateName, new CensusDAO(StateCensusCsv)));
            if (censusCSVClass.getName().equals("com.censusdata.USCensusCSV"))
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSVDTO.class::cast)
                        .forEach(USCensusCSV -> censusDAOMap.put(USCensusCSV.state, new CensusDAO(USCensusCSV)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
        }
    }
}
