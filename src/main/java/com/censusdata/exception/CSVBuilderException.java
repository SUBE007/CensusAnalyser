package com.censusdata.exception;

    public class CSVBuilderException extends Exception {
        enum CsvExceptionType {
            INPUT_OUTPUT_OPERATION_FAILED, INVALID_DELIMITER, INVALID_HEADER_COUNT,
            NO_SUCH_FILE_EXIST, UNABLE_TO_PARSE, WRONG_FILE_TYPE, INVALID_COUNTRY;
        }

        CsvExceptionType type;

        public CSVBuilderException(String message, CsvExceptionType type) {
            super(message);
            this.type = type;
        }

        public CSVBuilderException(Throwable cause, CsvExceptionType type) {
            super(cause);
            this.type = type;
        }
}
