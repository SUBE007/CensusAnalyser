package com.censusdata.sort;

import com.censusdata.dao.CensusDAO;
import com.censusdata.ISortBy;

import java.util.Comparator;

public class SortByArea implements ISortBy {
    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO,Double>comparing(census -> census.totalArea,Comparator.reverseOrder());
    }
}
