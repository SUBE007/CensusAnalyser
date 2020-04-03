package com.censusdata;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    int count=0;
    HashMap<String, CensusDAO> censusStateMap = null;
   public Set set;
    public StateCensusAnalyser() throws CensusAnalyserException {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath)throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCensusCsv> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCensusCsv.class);
            while (csvFileIterator.hasNext()) {
                CensusDAO indianCensusDAO = new CensusDAO(csvFileIterator.next());
                this.censusStateMap.put(CensusDAO.state, indianCensusDAO);
            }
            return this.censusStateMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.CsvExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new  CSVBuilderException("UNABLE_TO_PARSE", CSVBuilderException.CsvExceptionType.UNABLE_TO_PARSE);
        }
    }

    public void getFileExtension(File file) throws CensusAnalyserException {
        boolean result = false;
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            result = fileName.substring(fileName.lastIndexOf(".") + 1).equals("csv");
        }
        if (!result)
            throw new CensusAnalyserException("Wrong file type",
                    CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
    }

    public  int loadStateCode(String STATECODES_CSVFILE) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(STATECODES_CSVFILE));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSV> stateCsvIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
            while (stateCsvIterator.hasNext()) {
                CensusDAO  censusDAO = new CensusDAO(stateCsvIterator.next());
                this.censusStateMap.put(censusDAO.stateName, censusDAO);
            }
            return this.censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries= (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        Comparator<Map.Entry<String, CensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().state);
         LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeComparator);
         Collection<CensusDAO> list1 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list1);
        return sortedStateCensusJson;
    }
    public String getStateWiseSortedStateCode() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No State Data",
                    CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<Map.Entry<String, CensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().stateName);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeComparator);
        Collection<CensusDAO> list2 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list2);
        return sortedStateCensusJson;
    }
    private <E extends CensusDAO> LinkedHashMap<String, CensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, CensusDAO>> entries = censusStateMap.entrySet();
        List<Map.Entry<String, CensusDAO>> listOfEntries = new ArrayList<Map.Entry<String,CensusDAO>>(entries);
         Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String,  CensusDAO> sortedByValue = new LinkedHashMap<String, CensusDAO>(listOfEntries.size());
        for (Map.Entry<String,  CensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
            }
        return sortedByValue;
        }


}//class

