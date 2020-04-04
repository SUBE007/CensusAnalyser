package com.censusdata;
public class CensusAnalyserException extends Exception{
    public CensusExceptionType type;
    private String message;

    public CensusAnalyserException() {
    }

    public enum CensusExceptionType{
         NO_SUCH_FILE, INCORRECT_DATA_ISSUE, INCORRECT_COUNTRY_ENTERED,WRONG_FILE_TYPE,
         DELIMITER_ISSUE,NO_CENSUS_DATA, UNABLE_TO_PARSE,NO_STATE_CODE_DATA,CENSUS_FILE_PROBLEM ,
         INPUT_OUTPUT_OPERATION_FAILED, INVALID_DELIMITER, INVALID_HEADER_COUNT, NO_SUCH_CLASS,

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

    public CensusAnalyserException(Throwable cause, CensusExceptionType type) {
        super(cause);
        this.type = type;
    }
}