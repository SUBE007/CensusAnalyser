package com.censusdata.exception;
public class CensusAnalyserException extends Exception{
    public CensusExceptionType type;
    private String message;

    public CensusAnalyserException() {
    }

    public enum CensusExceptionType{
         WRONG_FILE_TYPE,  CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE,NO_STATE_CODE_DATA,
         INPUT_OUTPUT_OPERATION_FAILED, INVALID_DELIMITER,NO_SUCH_FILE_ERROR, INVALID_HEADER_COUNT
    }
    public CensusAnalyserException(String message, CensusExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(CensusExceptionType type,String message,Throwable cause) {
        super(message, cause);
        this.type = type;
    }




}