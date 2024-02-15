package com.crossover.bootcamp.wk4.report.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.crossover.bootcamp.wk4.report.model.SheetData;

class SheetDataMapperTest {

    @Test
    void mapToSheetData() {
        // Given
        List<List<Object>> data = List.of (
                List.of("First Name", "Last Name", "Email Address", "Department"),
                List.of("John", "Wick",	"johnwick@gmail.com", "murder"),
                List.of("Bruce", "Lee",	"brucelee@gmail.com", "kungfu")
        );
        // When
        SheetData actual = SheetDataMapper.mapToSheetData(data);
        // Then
        assertThat(actual.getHeaders()).hasSize(4);
        assertThat(actual.getValues()).hasSize(2);

        assertThat(actual.getValues().get(0))
                    .hasSize(4)
                    .containsEntry("First Name", "John")
                    .containsEntry("Last Name", "Wick");

        assertThat(actual.getValues().get(1))
                .hasSize(4)
                .containsEntry("First Name", "Bruce")
                .containsEntry("Last Name", "Lee");
    }
}