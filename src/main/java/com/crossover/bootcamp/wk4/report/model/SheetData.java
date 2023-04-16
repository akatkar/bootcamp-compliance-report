package com.crossover.bootcamp.wk4.report.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class SheetData {

    private List<String> headers;

    private List<Map<String,String>> values;

    public SheetData(List<List<Object>> data ){

        headers = data.stream()
                .limit(1)
                .flatMap(List::stream)
                .map(Object::toString)
                .collect(Collectors.toList());

        values = data.stream()
                .skip(1)
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    private Map<String, String> toMap(List<Object> values) {

        Map<String, String> results = new HashMap<>();
        for (int i = 0; i < values.size() ; i++) {
            results.put(headers.get(i), values.get(i).toString());
        }
        return results;
    }
}
