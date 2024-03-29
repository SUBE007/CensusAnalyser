package com.censusdata;

import com.censusdata.exception.CSVBuilderException;
import com.censusdata.exception.CensusAnalyserException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder <E> {

      public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException, CensusAnalyserException;
      public List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException, CensusAnalyserException;
}
