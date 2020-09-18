package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static java.time.LocalDate.now;
import static org.junit.Assert.*;
import org.junit.*;
import org.sql2o.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class MonsterTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void monster_instantiatesCorrectly_true() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(true, testMonster instanceof Monster);
    }

    @Test
    public void monster_instantiatesWithName_string() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals("Bubbles", testMonster.getName());
    }

    @Test
    public void Monster_instantiatesWithPersonId_int() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(1, testMonster.getPersonId());
    }
// This test is for overriding equals
    @Test
    public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
        Monster firstMonster = new Monster("Bubbles", 1);
        Monster secondMonster = new Monster("Bubbles", 1);
        assertTrue(firstMonster.equals(secondMonster));
    }

    //Test for saving monster into database
    @Test
    public void save_returnsTrueIfDescriptionAreTheSame() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        assertTrue(Monster.all().get(0).equals(testMonster));
    }

    @Test
    public void save_assignsIdToMonster() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        Monster savedMonster = Monster.all().get(0);
        assertEquals(savedMonster.getId(), testMonster.getId());
    }

    @Test
    public void all_returnsAllInstancesOfMonster_true() {
        Monster firstMonster = new Monster("Bubbles", 1);
        firstMonster.save();
        Monster secondMonster = new Monster("Spud", 1);
        secondMonster.save();
        assertEquals(true, Monster.all().get(0).equals(firstMonster));
        assertEquals(true, Monster.all().get(1).equals(secondMonster));
    }
    @Test
    public void find_returnsMonsterWithSameId_secondMonster() {
        Monster firstMonster = new Monster("Bubbles", 1);
        firstMonster.save();
        Monster secondMonster = new Monster("Spud", 3);
        secondMonster.save();
        assertEquals(Monster.find(secondMonster.getId()), secondMonster);
    }
// To ensure we can associate a Person with many Monsters:
    @Test
    public void save_savesPersonIdIntoDB_true() {
        Person testPerson = new Person("Henry", "henry@henry.com");
        testPerson.save();
        Monster testMonster = new Monster("Bubbles", testPerson.getId());
        testMonster.save();
        Monster savedMonster = Monster.find(testMonster.getId());
        assertEquals(savedMonster.getPersonId(), testPerson.getId());
    }

    // Tests for instantiating constants
    @Test
    public void monster_instantiatesWithHalfFullPlayLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2));
    }
    @Test
    public void monster_instantiatesWithHalfFullSleepLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2));
    }
    @Test
    public void monster_instantiatesWithHalfFullFoodLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2));
    }
//Test for checking life status
    @Test
    public void isAlive_confirmsMonsterIsAliveIfAllLevelsAboveMinimum_true(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.isAlive(), true);
    }
    @Test
    public void depleteLevels_reducesAllLevels(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.depleteLevels();
        assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2) - 1);
        assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2) - 1);
        assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2) - 1);
    }

    //  test whether the playLevel has increased beyond the default after the play() method has been called
    @Test
    public void play_increasesMonsterPlayLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.play();
        assertTrue(testMonster.getPlayLevel() > (Monster.MAX_PLAY_LEVEL / 2));
    }

    @Test
    public void play_increasesMonsterFoodLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.feed();
        assertTrue(testMonster.getFoodLevel() > (Monster.MAX_FOOD_LEVEL / 2));
    }
    @Test
    public void play_increasesMonsterSleepLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.sleep();
        assertTrue(testMonster.getSleepLevel() > (Monster.MAX_SLEEP_LEVEL / 2));
    }
// below tests asserts that throwing this exception prevents the eachLevel from increasing above the maximum value:
    @Test
    public void monster_foodLevelCannotGoBeyondMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++){
            try {
                testMonster.feed();
            } catch (UnsupportedOperationException exception){ }
        }
        assertTrue(testMonster.getFoodLevel() <= Monster.MAX_FOOD_LEVEL);
    }

    @Test
    public void monster_playLevelCannotGoBeyondMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
            try {
                testMonster.play();
            } catch (UnsupportedOperationException exception){ }
        }
        assertTrue(testMonster.getPlayLevel() <= Monster.MAX_PLAY_LEVEL);
    }

    @Test
    public void monster_sleepLevelCannotGoBeyondMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
            try {
                testMonster.sleep();
            } catch (UnsupportedOperationException exception){ }
        }
        assertTrue(testMonster.getPlayLevel() <= Monster.MAX_SLEEP_LEVEL);
    }



    // Test for exception
    @Test(expected = UnsupportedOperationException.class)
    public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++){
            testMonster.feed();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void play_throwsExceptionIfPlayLevelIsAtMaxValue() {
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i<=(Monster.MAX_PLAY_LEVEL); i++){
            testMonster.play();
        }
    }
    @Test(expected = UnsupportedOperationException.class)
    public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
            testMonster.sleep();
        }
    }
//Test for saving monsters birthday into the database
@Test
public void save_recordsTimeOfCreationInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    Timestamp savedMonsterBirthday = Monster.find(testMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedMonsterBirthday.getDay());
}
// this assert that the sleep() method accurately updates the lastSlept value in the database:
    @Test
    public void sleep_recordsTimeLastSleptInDatabase() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        testMonster.sleep();
        Timestamp savedMonsterLastSlept = Monster.find(testMonster.getId()).getLastSlept();
        Timestamp rightNow = new Timestamp(new Date().getTime());
        assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastSlept));
    }

    @Test
    public void feed_recordsTimeLastAteInDatabase() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        testMonster.feed();
        Timestamp savedMonsterLastAte = Monster.find(testMonster.getId()).getLastAte();
        Timestamp rightNow = new Timestamp(new Date().getTime());
        assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastAte));
    }

    @Test
    public void play_recordsTimeLastPlayedInDatabase() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        testMonster.play();
        Timestamp savedMonsterLastPlayed = Monster.find(testMonster.getId()).getLastPlayed();
        Timestamp rightNow = new Timestamp(new Date().getTime());
        assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastPlayed));
    }
    @Test
    public void timer_executesDepleteLevelsMethod() {
        Monster testMonster = new Monster("Bubbles", 1);
        int firstPlayLevel = testMonster.getPlayLevel();
        testMonster.startTimer();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException exception){}
        int secondPlayLevel = testMonster.getPlayLevel();
        assertTrue(firstPlayLevel > secondPlayLevel);
    }

    @Test
    public void timer_haltsAfterMonsterDies() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.startTimer();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException exception){}
        assertFalse(testMonster.isAlive());
        assertTrue(testMonster.getFoodLevel() >= 0);
    }
}