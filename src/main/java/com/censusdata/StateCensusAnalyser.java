package com.censusdata;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {

    public StateCensusAnalyser() { }

    public int loadIndiaCensusData(String csvFilePath)throws  CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<StateCensusCsv> censusCSVList = csvBuilder.getCSVFileList(reader, StateCensusCsv.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public  int loadStateCode(String STATECODES_CSVFILE) throws CensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(STATECODES_CSVFILE));) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<StateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
                return this.getCount(stateCSVIterator);
            } catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
            } catch (CSVBuilderException e) {
                throw new CensusAnalyserException(e.getMessage(), e.type.name());
            }
        }

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries= (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder= CSVBuilderFactory.createCSVBuilder();
            List<StateCensusCsv> censusCSVList = csvBuilder.getCSVFileList(reader, StateCensusCsv.class);
            Comparator<StateCensusCsv> censusCSVComparator = Comparator.comparing(census -> census.stateName);
            this.sort(censusCSVList, censusCSVComparator);
            String sortedStateCensusJson = new Gson().toJson(censusCSVList);
            return sortedStateCensusJson;
        } catch (IOException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private void sort(List<StateCensusCsv> censusCSVList, Comparator<StateCensusCsv> censusCSVComparator) {
        for (int i=0; i<censusCSVList.size()-1;i++){
            for (int j = 0; j<censusCSVList.size()-i-1; j++){
                StateCensusCsv censusCSV1 = censusCSVList.get(j);
                StateCensusCsv censusCSV2 = censusCSVList.get(j+1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2)> 0){
                    censusCSVList.set(j, censusCSV2);
                    censusCSVList.set(j+1, censusCSV1);
                }
            }
        }
    }

}//class

