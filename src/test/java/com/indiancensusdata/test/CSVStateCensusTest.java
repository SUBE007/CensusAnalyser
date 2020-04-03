package com.indiancensusdata.test;

import com.censusdata.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CSVStateCensusTest {

    public static final String STATECODES_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\StateCode.csv";
    public static final String STATECENSUS_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\IndianCensusData.csv";
    public static final String WRONG_FILE_PATH = "/Wrongfile.txt";

    @Test
    public void GivenTheStateCensusCsvFile_IfHasCorrectNumberOfRecords_ShouldReturnTrue() throws  CensusAnalyserException  {

        try {
            StateCensusAnalyser analyse = new StateCensusAnalyser();
            int count = analyse.loadIndiaCensusData(STATECENSUS_CSVFILE);
            Assert.assertEquals(29, count);
        } catch (CSVBuilderException | CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GivenTheStateCodeCsvFile_IfIncorrect_ShouldThrowCensusAnalyserException() throws IOException {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadStateCode(STATECODES_CSVFILE);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE, e.type);
        }
    }

    @Test
    public void GivenTheStateCodeCsvFile_WhenCorrect_ButFileExtensionIncorrect_ShouldThrowCensusAnalyserException() throws IOException {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadStateCode(WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void GivenTheStateCodeCSVFile_WhenCorrect_ButDelimiterIncorrect_ReturnsCensusAnalyserException() throws IOException {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadStateCode(STATECODES_CSVFILE);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.DELIMITER_ISSUE, e.type);
        }
    }

    @Test
    public void givenStateCensusData_WhenSortedOnStateFromFirst_ShouldReturnSortedResult() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadIndiaCensusData(STATECENSUS_CSVFILE);
             String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        } catch (CSVBuilderException e) {
            e.printStackTrace( );
        }
    }

    @Test
    public void givenStateCsvData_WhenSortedOnStateFromFirst_ShouldReturnSortedResult() throws CensusAnalyserException {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadStateCode(STATECODES_CSVFILE);
            String sortedCensusData = stateCensusAnalyser. getStateWiseSortedStateCode();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertNotEquals("AD", censusCSV[0].stateName);
        } catch (CensusAnalyserException  e) {
            e.printStackTrace();
        }

    }


}


