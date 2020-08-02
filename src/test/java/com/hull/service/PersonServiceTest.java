package com.hull.service;

import com.hull.config.TestSpringConfiguration;
import com.hull.csv.CsvReader;
import com.hull.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringConfiguration.class})
public class PersonServiceTest {

    @InjectMocks
    @Autowired
    private PersonService personService;
    @Mock
    private CsvReader mockCsvReader;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Person getTestPerson1() {
        Person person = new Person();
        person.setFirstName("Bernard");
        person.setLastName("Lowe");
        person.setGender("M");
        person.setFavoriteColor("Red");
        person.setDateOfBirth(LocalDate.of(1979, 10, 2));
        return person;
    }

    private Person getTestPerson2() {
        Person person = new Person();
        person.setFirstName("Dolores");
        person.setLastName("Abernathy");
        person.setGender("F");
        person.setFavoriteColor("White");
        person.setDateOfBirth(LocalDate.of(1982, 9, 21));
        return person;
    }

    @Test
    public void testLoadCsv() {
        Person testPerson = getTestPerson1();
        List<Person> people = mock(ArrayList.class);
        people.add(testPerson);
        ReflectionTestUtils.setField(personService, "people", people);

        when(mockCsvReader.readPersonCsv(anyString())).thenReturn(people);

        String filePath = "/some/file/path/file.csv";
        personService.loadCsv(filePath);

        verify(mockCsvReader, times(1)).readPersonCsv(filePath);
        verify(people, times(1)).addAll(any());
        // todo assert person in list
    }

    @Test
    public void testLoadCsv_null() {
        List<Person> people = mock(ArrayList.class);
        ReflectionTestUtils.setField(personService, "people", people);

        when(mockCsvReader.readPersonCsv(anyString())).thenReturn(null);

        String filePath = "/some/file/path/file.csv";
        personService.loadCsv(filePath);

        verify(mockCsvReader, times(1)).readPersonCsv(filePath);
        verify(people, times(0)).addAll(any());
    }

    @Test
    public void testAddPerson() {
        Person testPerson = getTestPerson1();
        List<Person> people = mock(ArrayList.class);
        people.add(testPerson);
        ReflectionTestUtils.setField(personService, "people", people);

        personService.addPerson(testPerson);
        verify(people, times(2)).add(testPerson);
        // todo revisit
//        assertThat(mockPeople.size(), is(1));
//        assertThat(mockPeople.get(1), is(testPerson));
    }

    @Test
    public void testAddPerson_null() {
        List<Person> people = mock(ArrayList.class);
        ReflectionTestUtils.setField(personService, "people", people);

        personService.addPerson(null);
        verify(people, times(0)).add(any());
    }

    @Test
    public void testGetAllSortedByGender() {
        List<Person> people = new ArrayList<>();
        people.add(getTestPerson2());
        people.add(getTestPerson1());
        ReflectionTestUtils.setField(personService, "people", people);

        List<Person> sorted = personService.getAllSortedByGender();
        assertThat(sorted.size(), is(2));
        assertThat(sorted.get(0), is(people.get(1)));
        assertThat(sorted.get(1), is(people.get(0)));
    }

    @Test
    public void testGetAllSortedByBirthDate() {
        List<Person> people = new ArrayList<>();
        people.add(getTestPerson2());
        people.add(getTestPerson1());
        ReflectionTestUtils.setField(personService, "people", people);

        List<Person> sorted = personService.getAllSortedByBirthDate();
        assertThat(sorted.size(), is(2));
        assertThat(sorted.get(0), is(people.get(1)));
        assertThat(sorted.get(1), is(people.get(0)));
    }

    @Test
    public void testGetAllSortedByLastName() {
        List<Person> people = new ArrayList<>();
        people.add(getTestPerson1());
        people.add(getTestPerson2());
        ReflectionTestUtils.setField(personService, "people", people);

        List<Person> sorted = personService.getAllSortedByLastName();
        assertThat(sorted.size(), is(2));
        assertThat(sorted.get(0), is(people.get(1)));
        assertThat(sorted.get(1), is(people.get(0)));
    }
}
