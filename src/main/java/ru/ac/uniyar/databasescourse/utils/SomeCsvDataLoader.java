package ru.ac.uniyar.databasescourse.utils;

import de.siegmar.fastcsv.reader.CsvReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SomeCsvDataLoader {

        private static Path path;

        public static List<List> data;

        public static void selectPath(Path p) {
            path = p;
        }

        public static void loadRows() throws IOException {
            try (CsvReader csvReader = CsvReader.builder().build(path)) {
                List<List> result = new ArrayList<>();

                csvReader.stream().skip(1).forEach(csv -> {
                    result.add(csv.getFields());
                });

                data = result;
            }
        }
}
