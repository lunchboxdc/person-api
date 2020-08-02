package com.hull.service;

import com.hull.csv.CsvReader;
import com.hull.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private CsvReader csvReader;
    private final List<Person> people = new ArrayList<>();

    public void loadCsv(String filePath) {
        List<Person> csvPeople = csvReader.readPersonCsv(filePath);
        if (csvPeople != null) {
            people.addAll(csvPeople);
        }
    }

    public void addPerson(Person person) {
        if (person != null) {
            people.add(person);
        }
    }

    public List<Person> getAllSortedByGender() {
        List<Person> sorted = new ArrayList<>(people);
        sorted.sort((p1, p2) -> {
            if (p1.getGender().equals(p2.getGender())) {
                return 0;
            }
            return "M".equalsIgnoreCase(p1.getGender()) ? -1 : 1;
        });
        return sorted;
    }

    public List<Person> getAllSortedByBirthDate() {
        List<Person> sorted = new ArrayList<>(people);
        sorted.sort((p1, p2) -> {
            if (p1.getDateOfBirth().equals(p2.getDateOfBirth())) {
                return 0;
            }
            return p1.getDateOfBirth().isBefore(p2.getDateOfBirth()) ? -1 : 1;
        });
        return sorted;
    }

    public List<Person> getAllSortedByLastName() {
        List<Person> sorted = new ArrayList<>(people);
        sorted.sort((p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()));
        return sorted;
    }
}
