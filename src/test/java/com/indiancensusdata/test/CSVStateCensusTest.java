package com.indiancensusdata.test;

import com.censusdata.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.File;
import java.io.IOException;

import static com.censusdata.StateCensusAnalyser.COUNTRY.INDIA;
import static com.censusdata.StateCensusAnalyser.COUNTRY.US;

public class CSVStateCensusTest {

    public static final String STATECODES_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\StateCode.csv";
    public static final String STATECENSUS_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\IndianCensusData.csv";
    public static final String USCENSUSDATA_CSVFILE="E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\USCensusData.csv";
    public static final String WRONG_FILE_PATH = "/Wrongfile.txt";


    @Test
    public void givenTheStateCensusCsvFile_IfHasCorrectNumberOfRecords_ShouldReturnTrue() throws  CensusAnalyserException  {

        try {
            StateCensusAnalyser censusAnalyse = new StateCensusAnalyser();
            int count = censusAnalyse.loadCensusCSVData(INDIA, STATECENSUS_CSVFILE);
            Assert.assertEquals(29, count);
        } catch (CSVBuilderException | CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenStateCensusData_WhenSortedOnStateFromFirst_ShouldReturnSortedResult_1() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
             stateCensusAnalyser.loadCensusCSVData(INDIA, STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedStateCode();
            CensusDAO[] censusCSV = new Gson( ).fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException | CSVBuilderException e) {
            e.printStackTrace( );
        }
    }

    @Test
    public void givenStateCensusData_WhenSortedOnStateFromFirst_ShouldReturnSortedResult_2() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadCensusCSVData(INDIA, STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedStateCode();
            CensusDAO[] censusCSV = new Gson( ).fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        } catch (CensusAnalyserException | CSVBuilderException e) {
            e.printStackTrace( );
        }
    }

    @Test
    public void GivenTheStateCodeCSVFile_WhenCorrect_ButDelimiterIncorrect_ReturnsCensusAnalyserException() throws IOException {
        try {
            File delimiterCheck = new File(STATECENSUS_CSVFILE);
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.checkDelimiter(delimiterCheck);
          } catch (CensusAnalyserException  e) {
            e.printStackTrace( );
        }
    }
    @Test
    public void givenStateCodeCsvFile_WhenIncorrectType_ReturnCustomException() {
         try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            File fileExtension = new File(STATECODES_CSVFILE);
            stateCensusAnalyser.getFileExtension(fileExtension);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE, e.type);
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }
    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadCensusCSVData(INDIA,STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulationDensity();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Arunachal Pradesh", stateCensuses[28].state);
        } catch (CSVBuilderException |CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList_2() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadCensusCSVData(INDIA,STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulation();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldReturnSortedList() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadCensusCSVData(INDIA,STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedStateArea();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", stateCensuses[0].state);
        } catch (CSVBuilderException |CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldReturnSortedList() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadCensusCSVData(INDIA,STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulationDensity();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Gujarat", stateCensuses[0].state);
        } catch (CSVBuilderException |CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_WhenLoadedCorrect_ShouldReturnExactCountOfList() {
        try{
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            int count = stateCensusAnalyser.loadCensusCSVData(US,USCENSUSDATA_CSVFILE);
            Assert.assertEquals(51,count);
        } catch (CSVBuilderException | CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_WhenLoadedIncorrect_ShouldReturnWrongCountOfList() {
        try{
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            int count = stateCensusAnalyser.loadCensusCSVData(US,USCENSUSDATA_CSVFILE);
            Assert.assertNotEquals(52,count);
        } catch (CSVBuilderException | CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

}//class test


