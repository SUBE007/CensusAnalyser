package com.censusdata;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StateCensusAnalyser {
    int count = 0;
    Map<String, CensusDAO> censusStateMap = new HashMap<String, CensusDAO>();

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

    public StateCensusAnalyser() {    }
    public enum COUNTRY {INDIA,US}
    private COUNTRY country;

    public StateCensusAnalyser(COUNTRY country) {
        this.country = country;
    }

    public enum SortingMode {AREA,STATE,STATECODE,DENSITY,POPULATION}

    public int loadCensusCSVData(COUNTRY country, String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        CensusCSVAdapter censusDataLoader = CensusCSVAdapterFactory.getCensusData(country);
        censusStateMap = censusDataLoader.loadCensusCSVData(StateCensusCSVDTO.class, csvFilePath);
        return censusStateMap.size();
    }
    public String getSortedCensusData(SortingMode mode) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
         throw new CensusAnalyserException("No State Data", CensusAnalyserException.CensusExceptionType.NO_STATE_CODE_DATA);
        ArrayList censusDTO = censusStateMap.values().stream()
                .sorted(CensusDAO.getSortComparator(mode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
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

    public void checkDelimiter(File file) throws CensusAnalyserException {
        Pattern pattern = Pattern.compile("^[\\w ]*,[\\w ]*,[\\w ]*,[\\w ]*");
        BufferedReader br = null;
        boolean delimiterResult = true;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                delimiterResult = pattern.matcher(line).matches();
                if (!delimiterResult) {
                    throw new CensusAnalyserException("Invalid Delimiter found",
                            CensusAnalyserException.CensusExceptionType.INVALID_DELIMITER);
                }
            }
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

}//class

