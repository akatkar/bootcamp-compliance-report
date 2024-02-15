package com.crossover.bootcamp.wk4.report.sheet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.crossover.bootcamp.wk4.report.config.SheetConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("!drive")
public class FastExcelSheetReader implements ExcelSheetReader {

    private final SheetConfig sheetConfig;
    private final ApplicationContext context;

    @Override
    public List<List<Object>> readData() throws IOException {
        File file = resolveFile(sheetConfig.getSource());
        try (ReadableWorkbook wb = new ReadableWorkbook(file)) {
            return wb.findSheet(sheetConfig.getRange())
                    .map(this::rowsData)
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    @SneakyThrows
    private List<List<Object>> rowsData(Sheet sheet) {
        try (Stream<Row> rows = sheet.openStream()) {
            return rows.map(Row::stream)
                    .map(this::rowData)
                    .toList();
        }
    }

    private List<Object> rowData(Stream<Cell> row) {
        return row.map(Cell::getRawValue)
                .map(Object.class::cast)
                .toList();
    }

    private File resolveFile(String name) throws IOException {
        return context.getResource(name).getFile();
    }
}
