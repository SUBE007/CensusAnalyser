package com.censusdata;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder <E> implements ICSVBuilder {
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException {
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return  csvToBean.iterator();
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.CensusExceptionType.UNABLE_TO_PARSE);
        }
    }
}
