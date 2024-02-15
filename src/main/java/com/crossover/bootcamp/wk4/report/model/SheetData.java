package com.crossover.bootcamp.wk4.report.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class SheetData {

    private final List<String> headers;

    private final List<Map<String,String>> values;

}
