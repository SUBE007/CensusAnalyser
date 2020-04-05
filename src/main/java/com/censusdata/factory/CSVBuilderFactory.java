package com.censusdata.factory;

import com.censusdata.ICSVBuilder;
import com.censusdata.OpenCSVBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
