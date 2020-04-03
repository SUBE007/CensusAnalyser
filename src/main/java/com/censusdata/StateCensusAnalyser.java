package com.censusdata;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    int count=0;
    HashMap<String, CensusDAO> censusStateMap = null;
   public Set set;
    public StateCensusAnalyser() throws CensusAnalyserException {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath)throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCensusCsv> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCensusCsv.class);
            while (csvFileIterator.hasNext()) {
                CensusDAO indianCensusDAO = new CensusDAO(csvFileIterator.next());
                this.censusStateMap.put(CensusDAO.densityPerSqKm, indianCensusDAO);
            }
            return this.censusStateMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.CsvExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new  CSVBuilderException("UNABLE_TO_PARSE", CSVBuilderException.CsvExceptionType.UNABLE_TO_PARSE);
        }
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

    public  int loadStateCode(String STATECODES_CSVFILE) throws CensusAnalyserException, CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(STATECODES_CSVFILE));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSV> stateCsvIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
            while (stateCsvIterator.hasNext()) {
                CensusDAO  censusDAO = new CensusDAO(stateCsvIterator.next());
                this.censusStateMap.put(censusDAO.stateName, censusDAO);
            }
            return this.censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CSVBuilderException("No of data fields does not match number of headers", CSVBuilderException.CsvExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries= (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedSPopulationDensity() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
            throw new CensusAnalyserException("No DensityPerSquareKM in State Data", CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
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
    public void checkDelimiter(File file) throws CSVBuilderException {
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
                            CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
                }
            }
        } catch (FileNotFoundException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.CsvExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.CsvExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    public String getStateWiseSortedSPopulation() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0)
            throw new CensusAnalyserException("No Population in State Data", CensusAnalyserException.CensusExceptionType.CENSUS_FILE_PROBLEM);
        Comparator<Map.Entry<String, CensusDAO>> cencusComparator = Comparator.comparing(census -> census.getValue().population);
        System.out.println(censusStateMap);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(cencusComparator);
        ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }
}//class

