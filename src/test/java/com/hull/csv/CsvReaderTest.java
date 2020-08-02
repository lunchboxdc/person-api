package com.hull.csv;


import com.hull.config.TestSpringConfiguration;
import com.hull.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringConfiguration.class})
public class CsvReaderTest {

    @Autowired
    private ResourceLoader resourceLoader;
    @Spy
    @Autowired
    private CsvReader csvReader;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private void validatePeopleList(List<Person> people) {
        people.forEach(person-> {
            assertThat(person.getFirstName().length(), greaterThan(0));
            assertThat(person.getLastName().length(), greaterThan(0));
            assertThat(person.getGender().length(), greaterThan(0));
            assertThat(person.getFavoriteColor().length(), greaterThan(0));
            assertThat(person.getDateOfBirth(), notNullValue());
        });
    }

    @Test
    public void testReadPersonCsv_Comma() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_people_comma.csv");

        doReturn(',').when(csvReader).getSeparator(any());

        List<Person> people = csvReader.readPersonCsv(commaCsv.getURI().getPath());
        assertThat(people.size(), is(10));
        validatePeopleList(people);
    }

    @Test
    public void testReadPersonCsv_Pipe() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_people_pipe.csv");

        doReturn('|').when(csvReader).getSeparator(any());

        List<Person> people = csvReader.readPersonCsv(commaCsv.getURI().getPath());
        assertThat(people.size(), is(10));
        validatePeopleList(people);
    }

    @Test
    public void testReadPersonCsv_Space() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_people_space.csv");

        doReturn(' ').when(csvReader).getSeparator(any());

        List<Person> people = csvReader.readPersonCsv(commaCsv.getURI().getPath());
        assertThat(people.size(), is(10));
        validatePeopleList(people);
    }

    @Test
    public void testReadPersonCsv_Dash() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_header_dash.csv");

        doReturn(null).when(csvReader).getSeparator(any());

        List<Person> people = csvReader.readPersonCsv(commaCsv.getURI().getPath());
        assertThat(people, nullValue());
    }

    @Test
    public void testGetSeparator_Comma() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_people_comma.csv");
        Path csvPath = Paths.get(commaCsv.getURI().getPath());

        Character separator = csvReader.getSeparator(csvPath);
        assertThat(separator, is(','));
    }

    @Test
    public void testGetSeparator_Pipe() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_people_pipe.csv");
        Path csvPath = Paths.get(commaCsv.getURI().getPath());

        Character separator = csvReader.getSeparator(csvPath);
        assertThat(separator, is('|'));
    }

    @Test
    public void testGetSeparator_Space() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_people_space.csv");
        Path csvPath = Paths.get(commaCsv.getURI().getPath());

        Character separator = csvReader.getSeparator(csvPath);
        assertThat(separator, is(' '));
    }

    @Test
    public void testGetSeparator_Dash() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_header_dash.csv");
        Path csvPath = Paths.get(commaCsv.getURI().getPath());

        Character separator = csvReader.getSeparator(csvPath);
        assertThat(separator, nullValue());
    }

    @Test
    public void testGetSeparator_Empty() throws IOException {
        Resource commaCsv = resourceLoader.getResource("test_empty.csv");
        Path csvPath = Paths.get(commaCsv.getURI().getPath());

        Character separator = csvReader.getSeparator(csvPath);
        assertThat(separator, nullValue());
    }
}
