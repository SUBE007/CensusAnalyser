package com.censusdata;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class StateCensusAnalyser {
    int count = 0;
    Map<String, CensusDAO> censusStateMap = new HashMap<String, CensusDAO>();
    public StateCensusAnalyser() throws CensusAnalyserException {
        this.censusStateMap = new HashMap<>();
    }

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

    public enum COUNTRY {INDIA,US}

    public int loadCensusCSVData(COUNTRY country, String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        CensusCSVAdapter censusDataLoader = CensusCSVAdapterFactory.getCensusData(country);
        censusStateMap = censusDataLoader.loadCensusCSVData(StateCensusCsv.class, csvFilePath);
        return censusStateMap.size();
    }
    public String getStateWiseSortedStateCode() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
         throw new CensusAnalyserException("No State Data", CensusAnalyserException.CensusExceptionType.NO_STATE_CODE_DATA);
        Comparator<Map.Entry<String, CensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeComparator);
        Collection<CensusDAO> list2 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list2);
        return sortedStateCensusJson;
    }

    public String getStateWiseSortedSPopulationDensity() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
            throw new CensusAnalyserException("No DensityPerSquareKM in StateData", CensusAnalyserException.CensusExceptionType.NO_CENSUS_DATA);
            Comparator<Map.Entry<String, CensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().densityPerSqKm);
            LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(censusComparator);
            ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
            Collections.reverse(list);
            String sortedStateCensusJson = new Gson().toJson(list);
            return sortedStateCensusJson;
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

    public String getStateWiseSortedSPopulation() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
            throw new CensusAnalyserException("No Population in StateData", CensusAnalyserException.CensusExceptionType.NO_CENSUS_DATA);
            Comparator<Map.Entry<String, CensusDAO>> cencusComparator = Comparator.comparing(census -> census.getValue().population);
            System.out.println(censusStateMap);
            LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(cencusComparator);
            ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
            Collections.reverse(list);
            String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    public String getStateWiseSortedStateArea() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
            throw new CensusAnalyserException("No Area of StateData", CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
        Comparator<Map.Entry<String, CensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().areaInSqKm);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(censusComparator);
        ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

}//class

