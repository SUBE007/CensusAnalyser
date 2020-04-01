package com.censusdata;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {

    public StateCensusAnalyser() { }

    public int loadIndiaCensusData(String csvFilePath)throws  CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCensusCsv> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, StateCensusCsv.class);
            return this.getCount(censusCSVIterator);
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
    }//class

