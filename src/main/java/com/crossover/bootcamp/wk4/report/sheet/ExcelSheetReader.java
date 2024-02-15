package com.crossover.bootcamp.wk4.report.sheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface ExcelSheetReader {
    List<List<Object>> readData() throws IOException, GeneralSecurityException;

}
