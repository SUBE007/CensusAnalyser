package com.indiancensusdata.test;

import com.censusdata.CensusAnalyserException;
import com.censusdata.StateCodeCSV;
import com.censusdata.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CSVStateCensusTest {

    public static final String STATECODES_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\StateCode.csv";
    public static final String STATECENSUS_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\IndianCensusData.csv";
    public static final String WRONG_FILE_PATH = "/Wrongfile.txt";

    @Test
    public void GivenTheStateCensusCsvFile_IfHasCorrectNumberOfRecords_ShouldReturnTrue() throws IOException {

        try {
            StateCensusAnalyser analyse=new StateCensusAnalyser();
            int count =analyse.loadIndiaCensusData(STATECENSUS_CSVFILE);
             Assert.assertEquals(29, count);
          } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GivenTheStateCodeCsvFile_IfIncorrect_ShouldThrowCensusAnalyserException() throws IOException {
        try {
            StateCensusAnalyser stateCensusAnalyser=new StateCensusAnalyser();
            ExpectedException exceptionRule=ExpectedException.none();
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
            StateCensusAnalyser stateCensusAnalyser=new StateCensusAnalyser();
            ExpectedException exceptionRule=ExpectedException.none();
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
            StateCensusAnalyser stateCensusAnalyser=new StateCensusAnalyser();
            ExpectedException exceptionRule=ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadStateCode(STATECODES_CSVFILE);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.DELIMITER_ISSUE, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_shouldReturnExactCount() throws CensusAnalyserException{
        try {
              StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();
            int numOfStateCode = censusAnalyser.loadStateCode(STATECODES_CSVFILE);
             Assert.assertEquals(37,numOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }

    }

}
