package com.hull.csv;

import com.hull.domain.Person;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Component
public class CsvReader {

    private final char COMMA = ',';
    private final char PIPE = '|';
    private final char SPACE = ' ';

    public List<Person> readPersonCsv(String filePath) {
        Path csvPath = Paths.get(filePath);
        Character separator = getSeparator(csvPath);

        if (separator == null) {
            //log
        } else {
            try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
                HeaderColumnNameMappingStrategy<Person> strategy = new HeaderColumnNameMappingStrategy<>();
                strategy.setType(Person.class);

                CsvToBean<Person> csvToBean = new CsvToBeanBuilder<Person>(br)
                        .withSeparator(separator)
                        .withMappingStrategy(strategy)
                        .build();

                return csvToBean.parse();
            } catch (Exception e) {
                Logger.getLogger(CsvReader.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return null;
    }

    private Character getSeparator(Path csvPath) {
        try (Stream<String> lines = Files.lines(csvPath)) {
            Optional<String> headerOptional = lines.findFirst();
            if (!headerOptional.isPresent()) {
                return null;
            } else {
                String header = headerOptional.get();
                if (header.indexOf(COMMA) > 0) {
                    return COMMA;
                } else if (header.indexOf(PIPE) > 0) {
                    return PIPE;
                } else if (header.indexOf(SPACE) > 0) {
                    return SPACE;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CsvReader.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
}
