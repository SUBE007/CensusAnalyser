package com.censusdata;

import com.opencsv.bean.CsvBindByName;

public class StateCodeCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stName;

    @CsvBindByName(column = "TIN", required = true)
    public String tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stCode;


    public StateCodeCSV() {

    }
    public String getSrNo() {
        return srNo;
    }
    public void setSrNo(String srNo) {
        srNo = srNo;
    }

    public String getStateName() {
        return stName;
    }
    public void setStateName(String stateName) {
        stName = stateName;
    }

    public String getTin(){
        return tin;
    }
    public void setTin(String tin){
        this.tin=tin;
    }

    public String getStateCode() {
        return stCode;
    }
    public void setStateCode(String stateCode) {
        stCode = stateCode;
    }

    @Override
    public String toString() {
        return   ", srNo='" + srNo + '\''+
                ", stName='" + stName + '\''+
                ", tin='" + tin + '\''+
                ", stCode='" + stCode + '\''
                 +"\n";
    }
}
