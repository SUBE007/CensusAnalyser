package com.censusdata.adapter;

import com.censusdata.factory.CSVBuilderFactory;
import com.censusdata.ICSVBuilder;
import com.censusdata.dto.StateCensusCSVDTO;
import com.censusdata.dao.CensusDAO;
import com.censusdata.dao.StateCodeCSVDAO;
import com.censusdata.dao.USCensusCSVDTO;
import com.censusdata.exception.CSVBuilderException;
import com.censusdata.exception.CensusAnalyserException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusCSVAdapter extends CensusCSVAdapter {
    Map<String, CensusDAO> censusMap = new HashMap<>();
    private Object CSVBuilderException;

    public <E> Map loadCensusCSVData(Class<E> className, String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> IndiaStateCodeIterator = csvbuilder.getCSVFileIterator(reader, className);
            Iterable<E> csvIterable = () -> IndiaStateCodeIterator;
            if (className.getName().equals("com.censusanalyser.main.StateCensusCSVDTO")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(StateCensusCSVDTO.class::cast)
                        .forEach(StateCensusCSVDTO -> censusMap.put(StateCensusCSVDTO.state, new CensusDAO(StateCensusCSVDTO)));
                censusMap = loadStateCodeCSV(csvFilePath[1]);
            } else if (className.getName().equals("com.censusanalyser.main.USCensusCSVDTO")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSVDTO.class::cast)
                        .forEach(USCensusCSVDTO -> censusMap.put(USCensusCSVDTO.state, new CensusDAO(USCensusCSVDTO)));
            }
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (com.censusdata.exception.CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Error capturing CSV header!",CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE_ERROR);
        }
    }
    public Map loadStateCodeCSV(String csvFilePath) throws CensusAnalyserException, IOException {
        try (Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSVDAO> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSVDAO.class);
            Iterable<StateCodeCSVDAO> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
             .map(StateCodeCSVDAO.class::cast)
                    .forEach(csvStateCode -> censusMap.get(csvStateCode.state).stateCode = csvStateCode.stateCode);
            return censusMap;
            }
            catch (IOException e) {
              throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
            } catch (RuntimeException | CSVBuilderException e) {
               throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
            }
    }
    @Override
    public Map<String, CensusDAO> loadCensusCSVData(String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusCSVData(StateCensusCSVDTO.class,csvFilePath[0]);
        if (csvFilePath.length == 1)
            return  censusStateMap;
        return loadCensusCSVData(StateCensusCSVDTO.class, csvFilePath);
    }
}
