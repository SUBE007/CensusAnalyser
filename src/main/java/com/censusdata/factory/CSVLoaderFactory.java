package com.censusdata.factory;

import com.censusdata.adapter.CensusCSVAdapter;
import com.censusdata.adapter.IndiaCensusCSVAdapter;
import com.censusdata.adapter.USCensusCSVAdapter;

public class CSVLoaderFactory {

    public enum COUNTRY {
        INDIA {
            public CensusCSVAdapter create() {
                return new IndiaCensusCSVAdapter();
            }
        },
        US {
            public CensusCSVAdapter create() {
                return new USCensusCSVAdapter();
            }
        };
        public CensusCSVAdapter create() {
            return null;
        }
    }

    public static CensusCSVAdapter createAdaptor(COUNTRY validatorType) {
        return validatorType.create();
    }
}
