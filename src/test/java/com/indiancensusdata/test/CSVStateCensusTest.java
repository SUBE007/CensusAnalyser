package com.indiancensusdata.test;

import com.censusdata.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
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
        catch (CSVBuilderException e) {
            e.printStackTrace();
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
             Assert.assertEquals(CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GivenTheStateCodeCSVFile_WhenCorrect_ButDelimiterIncorrect_ReturnsCensusAnalyserException() throws IOException {
        try {
            File delimiterCheck = new File(STATECENSUS_CSVFILE);
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.checkDelimiter(delimiterCheck);
          } catch (CensusAnalyserException  e) {
             Assert.assertEquals(CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
         catch (CSVBuilderException e) {
            e.printStackTrace( );
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadIndiaCensusData(STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulation();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Sikkim", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList_2() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
            stateCensusAnalyser.loadIndiaCensusData(STATECENSUS_CSVFILE);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulation();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


}


