package com.censusdata.sort;

import com.censusdata.dao.CensusDAO;
import com.censusdata.ISortBy;

import java.util.Comparator;

public class SortByDensity implements ISortBy {
    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO,Double>comparing(census -> census.populationDensity,Comparator.reverseOrder());
    }
}
