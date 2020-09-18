package models;

import org.junit.*;

import static org.junit.Assert.*;
import org.sql2o.*;

import java.util.Arrays;

public class PersonTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void person_instantiatesCorrectly_true() throws Exception{
        Person testPerson = new Person("John", "email");
        assertEquals(true, testPerson instanceof Person);
    }

    @Test
    public void getName_personInstantiatesWithName_Henry() {
        Person testPerson = new Person("George", "email");
        assertEquals("George", testPerson.getName());
    }

    @Test
    public void getEmail_personInstantiatesWithEmail_String() {
        Person testPerson = new Person("George", "gokumu12@gmail.com");
        assertEquals("gokumu12@gmail.com", testPerson.getEmail());
    }

    @Test
    public void equals_returnsTrueIfNameAndEmailAreTheSame_true() {
        Person firstPerson = new Person("George", "gokumu12@gmail.com");
        Person secondPerson = new Person("George", "gokumu12@gmail.com");
        assertEquals(true, firstPerson.equals(secondPerson));
    }
    @Test
    public void save_insertsObjectIntoDatabase_Person() {
        Person testPerson = new Person("George", "email");
        testPerson.save();
        assertTrue(Person.all().get(0).equals(testPerson));
    }

    @Test
    public void all_returnsAllInstancesOfPerson_true() {
        Person firstPerson = new Person("George", "gokumu12@gmail.com");
        firstPerson.save();
        Person secondPerson = new Person("Harriet", "harriet@harriet.com");
        secondPerson.save();
        assertEquals(true, Person.all().get(0).equals(firstPerson));
        assertEquals(true, Person.all().get(1).equals(secondPerson));
    }
    @Test
    public void save_assignsIdToPerson() {
        Person testPerson = new Person("George", "gokumu12@gmail.com");
        testPerson.save();
        Person savedPerson = Person.all().get(0);
        assertEquals(savedPerson.getId(), testPerson.getId());
    }

    @Test
    public void find_returnsPersonWithSameId_secondPerson() {
        Person firstPerson = new Person("George", "gokumu12@gmail.com");
        firstPerson.save();
        Person secondPerson = new Person("Harriet", "harriet@harriet.com");
        secondPerson.save();
        assertEquals(Person.find(secondPerson.getId()), secondPerson);
    }

//    @Test
//    public void getMonsters_retrievesAllMonstersFromDatabase_monstersList() {
//        Person testPerson = new Person("Henry", "henry@henry.com");
//        testPerson.save();
//        Monster firstMonster = new Monster("Bubbles", testPerson.getId());
//        firstMonster.save();
//        Monster secondMonster = new Monster("Spud", testPerson.getId());
//        secondMonster.save();
//        Monster[] monsters = new Monster[] { firstMonster, secondMonster };
//        assertTrue(testPerson.getMonsters().containsAll(Arrays.asList(monsters)));
//    }
}