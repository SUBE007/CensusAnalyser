package com.indiancensusdata.test;

import com.censusdata.ISortBy;
import com.censusdata.StateCensusAnalyser;
import com.censusdata.adapter.IndiaCensusCSVAdapter;
import com.censusdata.dao.CensusDAO;
import com.censusdata.dto.StateCensusCSVDTO;
import com.censusdata.exception.CSVBuilderException;
import com.censusdata.exception.CensusAnalyserException;
import com.censusdata.factory.CSVLoaderFactory;
import com.censusdata.sort.SortByArea;
import com.censusdata.sort.SortByState;
import com.censusdata.sort.SortParameter;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndiaAdaptarTest {
    private static final String STATECODES_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\StateCode.csv";
    private static final String STATECENSUS_CSVFILE = "E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\IndianCensusData.csv";
    private static final String USCENSUSDATA_CSVFILE="E:\\BridgrLabz\\IndianCensusAnalyser\\src\\main\\resources\\USCensusData.csv";
    private static final String WRONG_FILE_PATH = "/Wrongfile.txt";

    @Test
    public void givenData_WhenReadIndianCensusDataUsingAdapter_ShouldReturnOutput() {
        try {
            Map<String, CensusDAO> indiaCensusData = new IndiaCensusCSVAdapter().loadCensusCSVData(STATECENSUS_CSVFILE, STATECODES_CSVFILE);
            Assert.assertEquals(29, indiaCensusData.size());
        } catch (CensusAnalyserException | CSVBuilderException e) {
        }
    }

    @Test
    public void givenTestCase_ifWrongDelimiter_shouldThrowException() {
        try {
            StateCensusAnalyser censusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.COUNTRY.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusCSVData(CSVLoaderFactory.COUNTRY.INDIA, WRONG_FILE_PATH, STATECODES_CSVFILE);
        } catch (CensusAnalyserException | CSVBuilderException e) {
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM, e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            StateCensusAnalyser censusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.COUNTRY.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusCSVData(CSVLoaderFactory.COUNTRY.INDIA, WRONG_FILE_PATH);
        } catch (CensusAnalyserException | CSVBuilderException e) {
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM, e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithNullFile_ShouldThrowException() {
        try {
            StateCensusAnalyser censusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.COUNTRY.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusCSVData(CSVLoaderFactory.COUNTRY.INDIA, WRONG_FILE_PATH);
        } catch (CensusAnalyserException | CSVBuilderException e) {
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE_ERROR, e.getMessage());
        }
    }

    @Test
    public void givenData_whenSortByName_ShouldReturnSortedoutput() {
        try {
            StateCensusAnalyser censusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.COUNTRY.INDIA);
            censusAnalyser.loadCensusCSVData(CSVLoaderFactory.COUNTRY.INDIA, STATECENSUS_CSVFILE, STATECODES_CSVFILE);
            ISortBy sortBy = new SortByState();

            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.STATE);
            CensusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", CsvDataList[0].state);
        } catch (CensusAnalyserException | CSVBuilderException e) {
             e.printStackTrace();
        }
    }

    @Test
    public void givenData_whenReverseSortedByAreaUsingInterface_ShouldReturnSortedoutput() {
        try {
            StateCensusAnalyser censusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.COUNTRY.INDIA);
            censusAnalyser.loadCensusCSVData(CSVLoaderFactory.COUNTRY.INDIA, STATECENSUS_CSVFILE, STATECODES_CSVFILE);
            ISortBy iSortBy = new SortByArea();
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(iSortBy);
            StateCensusCSVDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, StateCensusCSVDTO[].class);
            Assert.assertEquals("Rajasthan", CsvDataList[0].state);
        } catch (CensusAnalyserException | CSVBuilderException e) {
            Assert.assertEquals(e.getMessage(), CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenReverseSortedByPopulationWithDensity_ShouldReturnSortedoutput() {
        try {
            StateCensusAnalyser censusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.COUNTRY.INDIA);
            censusAnalyser.loadCensusCSVData(CSVLoaderFactory.COUNTRY.INDIA, STATECODES_CSVFILE);
            String sortedCensusData = censusAnalyser.getStateWithDualSortByParameter();
            StateCensusCSVDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, StateCensusCSVDTO[].class);
            Assert.assertEquals("Bihar", CsvDataList[0].state);
        } catch (CensusAnalyserException | CSVBuilderException e) {
             e.printStackTrace();
        }
    }

}//class
