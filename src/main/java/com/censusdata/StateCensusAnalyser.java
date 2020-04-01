package com.censusdata;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {

    public StateCensusAnalyser() {
    }

    public static <T>  int openCsvBuilder(String csvFilePath, Object myClass) throws CensusAnalyserException {
        int counter = 1;
        try {
            Iterator<Object> myIterator = getIterator(csvFilePath, myClass);
            while ( myIterator.hasNext() ) {
                counter++;
                Object myObj = myIterator.next();
                //System.out.println(myObj.toString());
            }
        } catch (CensusAnalyserException e){
            throw e;
        } catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.DELIMITER_ISSUE,
                    "might be some error related to delimiter at record no. : " +(counter+1));
        }
        return counter;
    }

    public static Iterator<Object> getIterator(String csvFilePath, Object myClass) throws CensusAnalyserException {
        Reader reader = null;
        CsvToBean<Object> csvToBean;
        try {
            reader = Files.newBufferedReader(Paths.get(csvFilePath));
            csvToBean = new CsvToBeanBuilder(reader)
                    .withType((Class) myClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<Object> myIterator = csvToBean.iterator();
            return myIterator;
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE,
                    "NO_SUCH_FILE_EXCEPTION ENTER CORRECT FILE");
        } catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE,
                    "delimiter error at line 1 OR might be some error " +
                            "related to headers or incorrect extension / input file ");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.SOME_OTHER_IO_EXCEPTION,
                    "Some other IO related exception");
        }
    }

    public static int loadStateCode(String STATECODES_CSVFILE) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(STATECODES_CSVFILE))) {
            CsvToBeanBuilder<StateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(StateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<StateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<StateCensus> censusCSVIterator = csvToBean.iterator();
            Iterable<StateCensus> iterable = () -> censusCSVIterator;
            int namOfEateries = (int) StreamSupport.stream(iterable.spliterator(), false).count();
           // System.out.println("Nub of states"+namOfEateries);
            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
        }

    }


    }
