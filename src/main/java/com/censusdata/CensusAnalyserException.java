package com.censusdata;
public class CensusAnalyserException extends Exception{
    public CensusExceptionType type;
    private String message;

    public CensusAnalyserException() {
    }

    public enum CensusExceptionType{
        NO_SUCH_FILE, INCORRECT_DATA_ISSUE, CSV_FILE_INTERNAL_ISSUES,
        SOME_OTHER_IO_EXCEPTION, DELIMITER_ISSUE,
        NO_SUCH_CLASS,CENSUS_FILE_PROBLEM , UNABLE_TO_PARSE

    }
    public CensusAnalyserException(String message, CensusExceptionType type) {
        super(message);
        this.type = type;
    }
    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = CensusExceptionType.valueOf(name);
    }

    public CensusAnalyserException(CensusExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}