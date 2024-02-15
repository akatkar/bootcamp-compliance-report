package com.crossover.bootcamp.wk4.report.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crossover.bootcamp.wk4.report.model.SheetData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SheetDataMapper {

    public static SheetData mapToSheetData(List<List<Object>> data) {
        var headers = parseHeaders(data);
        var values = parseValues(headers, data);
        return SheetData.of(headers, values);
    }

    private static List<String> parseHeaders(List<List<Object>> data) {
        return data.stream()
                .limit(1)
                .flatMap(List::stream)
                .map(Object::toString)
                .toList();
    }

    private static List<Map<String, String>> parseValues(List<String> headers, List<List<Object>> data) {
        return data.stream()
                .skip(1)
                .map(values -> toMap(headers, values))
                .toList();
    }

    private static Map<String, String> toMap(List<String> headers, List<Object> values) {

        Map<String, String> results = new HashMap<>();
        for (int i = 0; i < values.size() ; i++) {
            results.put(headers.get(i), values.get(i).toString());
        }
        return results;
    }

}
