package org.junit5.model;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit5.model.Race.HOBBIT;

class DataServiceTest {

    // TODO initialize before each test
    DataService dataService;
    @BeforeEach
    void setup(){
        this.dataService = new DataService();
    }
    @Test
    void ensureThatInitializationOfTolkeinCharactorsWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);

        // TODO check that age is 33
        assertEquals(33, frodo.age);
        // TODO check that name is "Frodo"

        assertEquals("Frodo", frodo.getName());

        // TODO check that name is not "Frodon"
        assertNotEquals("Fronon", frodo.getName());
        //fail("not yet implemented");
    }

    @Test
    void ensureThatEqualsWorksForCharaters() {
        Object jake = new TolkienCharacter("Jake", 43, HOBBIT);
        Object sameJake = jake;
        Object jakeClone = new TolkienCharacter("Jake", 12, HOBBIT);
        // TODO check that:
        // jake is equal to sameJake
        assertEquals(jake, sameJake);
        // jake is not equal to jakeClone
        assertNotEquals(jake, jakeClone);
    }

    @Test
    void checkInheritance() {
        TolkienCharacter tolkienCharacter = dataService.getFellowship().getFirst();
        // TODO check that tolkienCharacter.getClass is not a movie class
        assertNotEquals(tolkienCharacter.getClass(), Movie.class);
    }

    @Test
    void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {
        // TODO imlement a check that dataService.getFellowshipCharacter returns null for an
        // unknow felllow, e.g. "Lars"
        assertNull(dataService.getFellowshipCharacter("Lars"));
    }

    @Test
    void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {
        // TODO imlement a check that dataService.getFellowshipCharacter returns a fellow for an
        // existing felllow, e.g. "Frodo"
        assertNotNull(dataService.getFellowshipCharacter("Frodo"));
    }


    @Test
    void ensureThatFrodoAndGandalfArePartOfTheFellowsip() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO check that Frodo and Gandalf are part of the fellowship
        assertTrue(fellowship.stream().anyMatch(fellow -> fellow.getName().equals("Frodo")));
        assertTrue(fellowship.stream().anyMatch(fellow -> fellow.getName().equals("Gandalf")));

    }

    @Test
    void ensureThatOneRingBearerIsPartOfTheFellowship() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO test that at least one ring bearer is part of the fellowship
        var ringBearer = dataService.getRingBearers();
        boolean found = false;
        for(TolkienCharacter tolkienCharacter: ringBearer.values()){
            found = found || fellowship.stream().anyMatch(fellow -> fellow.getName().equals(tolkienCharacter.getName()));
        }
        assertTrue(found);
    }

    // TODO Use @RepeatedTest(int) to execute this test 1000 times
    @Tag("slow")
    @RepeatedTest(1000)
    @DisplayName("Minimal stress testing: run this test 1000 times to ")
    void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
        dataService = new DataService();
        assertNotNull(dataService.getFellowship());
    }

    @Test
    void ensureOrdering() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        // TODO ensure that the order of the fellowship is:
        // TODO frodo, sam, merry,pippin, gandalf,legolas,gimli,aragorn,boromir
        List<String> fellowshipName = fellowship.stream().map(TolkienCharacter::getName).toList();
        String[] data = new String[]{"Frodo", "Sam", "Merry","Pippin", "Gandalf","Legolas","Gimli","Aragorn","Boromir"};
        assertIterableEquals(Arrays.stream(data).toList(), fellowshipName);
    }

    @Test
    void ensureAge() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        // TODO test ensure that all hobbits and men are younger than 100 years
        // TODO also ensure that the elfs, dwars the maia are all older than 100 years
        assertTrue(fellowship.stream()
                .filter(fellow -> fellow.getRace().equals(HOBBIT) || fellow.getRace().equals(Race.MAN))
                .allMatch(fellow -> fellow.age < 100));

        assertTrue(
                fellowship
                        .stream().filter(fellow -> fellow.getRace().equals(Race.ELF)
                                || fellow.getRace().equals(Race.DWARF) || fellow.getRace().equals(Race.MAIA))
                        .allMatch(fellow -> fellow.age > 100));
     }

    @Test
    void ensureThatFellowsStayASmallGroup() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO Write a test to get the 20 element from the fellowship throws an
        // IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> fellowship.get(20));
    }
    @Test
    void ensureTimeLimit(){
        assertTimeout(Duration.ofMillis(2005), () ->dataService.update());
    }

}