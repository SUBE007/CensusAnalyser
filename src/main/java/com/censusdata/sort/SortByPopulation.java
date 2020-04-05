package com.censusdata.sort;

import com.censusdata.dao.CensusDAO;
import com.censusdata.ISortBy;

import java.util.Comparator;

public class SortByPopulation implements ISortBy {
    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO,Long>comparing(census -> census.population,Comparator.reverseOrder());
    }
}
