package com.censusdata.sort;

import com.censusdata.dao.CensusDAO;
import com.censusdata.ISortBy;

import java.util.Comparator;

public class SortByState implements ISortBy {
    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO, String>comparing(census -> census.state,Comparator.reverseOrder());
    }
}
