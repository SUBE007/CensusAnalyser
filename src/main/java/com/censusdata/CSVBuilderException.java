package com.censusdata;

    public class CSVBuilderException extends Exception {
        enum CsvExceptionType {
            DELIMITER_ISSUE,UNABLE_TO_PARSE,CENSUS_FILE_PROBLEM
        }

        CsvExceptionType type;

        public CSVBuilderException(String message, CsvExceptionType type) {
            super(message);
            this.type = type;
        }

}
