package com.censusdata;

import com.opencsv.bean.CsvBindByName;

public class StateCensus {
//    @CsvBindByName(column = "State")
//    private String stateName;
//
//    @CsvBindByName(column = "Population",required = true)
//    private String population;
//
//    @CsvBindByName(column = "AreaInSqKm")
//    private String areaInSqKm;
//
//    @CsvBindByName(column = "DensityPerSqKm", required = true)
//    private String densityPerSqKm;



    @CsvBindByName(column = "SrNo", required = true)
    public int srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stName;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stCode;

    public StateCensus() {

    }
  /*  public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public void setDensityPerSqKm(String densityPerSqKm) {
        this.densityPerSqKm = densityPerSqKm;
    }

    public int getTin(){
        return tin;
    }

    public void setTin(int tin){
        this.tin=tin;
    }*/

    @Override
    public String toString() {
        return  //"stateName='" + stateName + '\'' +
                //", population='" + population + '\'' +
                //", areaInSqKm='" + areaInSqKm + '\'' +
                //", densityPerSqKm='" + densityPerSqKm + '\''+
                ", srNo='" + srNo + '\''+
                ", stName='" + stName + '\''+
                ", tin='" + tin + '\''+
                ", stCode='" + stCode + '\''
                 +"\n";
    }
}
