package com.censusdata;

import com.opencsv.bean.CsvBindByName;

public class StateCodeCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public int srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stName;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stCode;

    public StateCodeCSV() {

    }
    public int getTin(){
        return tin;
    }

    public void setTin(int tin){
        this.tin=tin;
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
