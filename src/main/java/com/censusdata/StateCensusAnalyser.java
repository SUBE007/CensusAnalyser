package com.censusdata;

import com.censusdata.adapter.CensusCSVAdapter;
import com.censusdata.dao.CensusDAO;
import com.censusdata.exception.CSVBuilderException;
import com.censusdata.exception.CensusAnalyserException;
import com.censusdata.factory.CSVLoaderFactory;
import com.censusdata.factory.CensusCSVAdapterFactory;
import com.censusdata.sort.SortByDensity;
import com.censusdata.sort.SortByPopulation;
import com.censusdata.sort.SortParameter;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StateCensusAnalyser {


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

    public StateCensusAnalyser() {
        this.censusMap = new HashMap<>();
    }
    public enum COUNTRY {INDIA,US}
    public COUNTRY country;
    Map<String, CensusDAO> censusMap = null;

    public StateCensusAnalyser(COUNTRY country) {
        this.country = country;
        censusMap = new HashMap<>();
    }

    public enum SortingMode {AREA,STATE,STATECODE,DENSITY,POPULATION}

    public int loadCensusCSVData(COUNTRY country, String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        CensusCSVAdapter censusDataLoader = CensusCSVAdapterFactory.getCensusData(country);
        censusMap = censusDataLoader.loadCensusCSVData(csvFilePath);
        return censusMap.size();
    }

    public Map loadCensusCSVData(CSVLoaderFactory.COUNTRY country, String... csvFilePath) throws CensusAnalyserException, CSVBuilderException {
        CensusCSVAdapter censusAdaptor = CSVLoaderFactory.createAdaptor(country);
        censusMap = censusAdaptor.loadCensusCSVData(csvFilePath);
        return censusMap;
    }

    public String getStateWithSortByParameter(SortParameter.Parameter parameter) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusCSVComparator = SortParameter.getParameter(parameter);
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS.sort(censusCSVComparator);
        List<Object> censusDto = new ArrayList<>();
        for (CensusDAO censusDAO : censusDAOS
        ) {
            censusDto.add(censusDAO.getCensusDTO(country));
        }
        String sortedData = new Gson().toJson(censusDAOS);
        return sortedData;
    }

    public String getStateWithSortByParameter(ISortBy iSortBy) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusCSVComparator = iSortBy.getComparator();
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS.sort(censusCSVComparator);
        List<Object> censusDto = new ArrayList<>();
        for (CensusDAO censusDAO : censusDAOS
        ) {
            censusDto.add(censusDAO.getCensusDTO(country));
        }
        String sortedData = new Gson().toJson(censusDto);
        return sortedData;
    }

    public String getStateWithDualSortByParameter() throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> PopulationComparator = new SortByPopulation().getComparator();
        Comparator<CensusDAO> DensityComparator = new SortByDensity().getComparator();
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        List<CensusDAO> sortedList= sort(censusDAOS, PopulationComparator, DensityComparator);
        sortedList.forEach(System.out::println);
        String sortedData = new Gson().toJson(sortedList);
        return sortedData;
    }
    public String getSortedCensusData(SortingMode mode) throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0)
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE_ERROR);
        ArrayList censusDTO = censusMap.values().stream()
                .sorted(CensusDAO.getSortComparator(mode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }

    private List sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> populationComparater, Comparator<CensusDAO> densityComparater) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO censusCSV1 = censusDAOS.get(j);
                CensusDAO censusCSV2 = censusDAOS.get(j + 1);
                if (populationComparater.compare(censusCSV1, censusCSV2) == 0) {
                    if (densityComparater.compare(censusCSV1, censusCSV2) > 0) {
                        censusDAOS.set(j, censusCSV2);
                        censusDAOS.set(j + 1, censusCSV1);
                    }
                } else if (populationComparater.compare(censusCSV1, censusCSV2) > 0) {
                    censusDAOS.set(j, censusCSV2);
                    censusDAOS.set(j + 1, censusCSV1);
                }
            }
        }
        return censusDAOS;
    }

}//class

