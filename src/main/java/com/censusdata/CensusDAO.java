package com.censusdata;

public class CensusDAO {

    public static String state;
    public String stateCode;
    public String srNo;
    public String tin;
    public String stateName;


    public CensusDAO(StateCodeCSV indiaCodeCSV) {
        srNo = indiaCodeCSV.getSrNo();
        stateCode = indiaCodeCSV.getStateCode();
        tin = indiaCodeCSV.getTin();
        stateName = indiaCodeCSV.getStateName();
    }
}
