package com.hull.api;

import com.hull.config.TestSpringConfiguration;
import com.hull.domain.Person;
import com.hull.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringConfiguration.class})
public class RecordsTest {

    @Mock
    private PersonService personService;
    @Autowired
    @InjectMocks
    private Records records;

    private final List<Person> testPeople = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        Person person1 = new Person();
        person1.setFirstName("Maeve");
        person1.setLastName("Millay");
        person1.setGender("F");
        person1.setFavoriteColor("Red");
        person1.setDateOfBirth(LocalDate.of(1976, 4, 9));
        testPeople.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Arnold");
        person2.setLastName("Weber");
        person2.setGender("M");
        person2.setFavoriteColor("Blue");
        person2.setDateOfBirth(LocalDate.of(1972, 8, 28));

        testPeople.add(person2);
    }

    @Test
    public void testGetSortedByGender() {
        when(personService.getAllSortedByGender()).thenReturn(testPeople);
        Response response = records.getAllSortedByGender();
        assertThat(response.getStatus(), is(200));
        List<Person> responseEntity = (List<Person>) response.getEntity();
        assertThat(responseEntity.size(), is(2));
    }

    @Test
    public void testGetSortedByBirthDate() {
        when(personService.getAllSortedByBirthDate()).thenReturn(testPeople);
        Response response = records.getAllSortedByBirthDate();
        assertThat(response.getStatus(), is(200));
        List<Person> responseEntity = (List<Person>) response.getEntity();
        assertThat(responseEntity.size(), is(2));
    }

    @Test
    public void testGetSortedByLastName() {
        when(personService.getAllSortedByLastName()).thenReturn(testPeople);
        Response response = records.getAllSortedByLastName();
        assertThat(response.getStatus(), is(200));
        List<Person> responseEntity = (List<Person>) response.getEntity();
        assertThat(responseEntity.size(), is(2));
    }

    @Test
    public void testPostPerson() {
        Person person = new Person();
        person.setFirstName("Teddy");
        person.setLastName("Flood");
        person.setGender("M");
        person.setFavoriteColor("Yellow");
        person.setDateOfBirth(LocalDate.of(1971, 3, 10));

        Response response = records.postPerson(person);
        assertThat(response.getStatus(), is(200));
        verify(personService, times(1)).addPerson(person);
    }
}
