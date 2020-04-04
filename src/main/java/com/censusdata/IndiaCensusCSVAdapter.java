package com.censusdata;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusCSVAdapter extends CensusCSVAdapter {
    public static Map<String, CensusDAO> loadIndiaStateCodeData(Map<String, CensusDAO> censusDAOMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
            Iterable<StateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .map(StateCodeCSV.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.getStateName(), new CensusDAO(csvStateCode)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.INVALID_HEADER_COUNT);
        }
    }

    @Override
    public Map<String, CensusDAO> loadCensusCSVData(String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusCSVData(StateCensusCsv.class,csvFilePath[0]);
        if (csvFilePath.length == 1)
            return  censusStateMap;
        return this.loadIndiaStateCodeData(censusStateMap, csvFilePath[1]);
    }
}
