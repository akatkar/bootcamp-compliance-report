package com.crossover.bootcamp.wk4.report.sheet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.crossover.bootcamp.wk4.report.config.SheetConfig;

@ExtendWith(MockitoExtension.class)
class FastExcelSheetReaderTest {

    private static final String SHEET_SOURCE = "data/TestFile.xlsx";
    private static final String SHEET_NAME = "Sheet1";

    private final SheetConfig sheetConfig = createSheetConfig();

    @Mock
    private ApplicationContext context;
    @Mock
    private Resource resource;
    private FastExcelSheetReader underTest;

    @BeforeEach
    void setUp() throws IOException {
        underTest = new FastExcelSheetReader(sheetConfig, context);
        given(context.getResource(anyString())).willReturn(resource);
        given(resource.getFile()).willReturn(getResource());
    }

    @Test
    void readData() throws IOException {
        List<List<Object>> actual = underTest.readData();
        assertThat(actual)
                .hasSize(3)
                .contains(List.of("First Name", "Last Name", "email", "Department"))
                .contains(List.of("Ali", "Katkar", "alikatkar@gmail.com", "test"))
                .contains(List.of("Veli", "Katkar", "alikatkar@gmail.com", "test"));
        assertThat(sheetConfig.getAppName()).isEqualTo("app");
        assertThat(sheetConfig.getSource()).isEqualTo(SHEET_SOURCE);
        assertThat(sheetConfig.getRange()).isEqualTo(SHEET_NAME);
    }

    private static SheetConfig createSheetConfig() {
        SheetConfig sheetConfig = new SheetConfig();
        sheetConfig.setAppName("app");
        sheetConfig.setSource(SHEET_SOURCE);
        sheetConfig.setRange(SHEET_NAME);
        return sheetConfig;
    }

    private static File getResource() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(SHEET_SOURCE);
        return Optional.ofNullable(url)
                .map(URL::getPath)
                .map(File::new)
                .orElseThrow(IllegalStateException::new);
    }

}
