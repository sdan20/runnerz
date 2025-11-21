package com.github.sdan20.runnerz.run;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunRepositoryTest {

    RunRepository repository;

    @BeforeEach
    void setUp() {
        repository = new RunRepository();
        repository.create(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(35, ChronoUnit.MINUTES),
                3,
                Location.INDOOR));

        repository.create(new Run(2,
                "Wednesday Evening Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(55, ChronoUnit.MINUTES),
                5,
                Location.OUTDOOR));
    }

    @Test
    void givenNumRums_whenFindAll_thenReturnNum() {
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size(), "Should have returned 2 runs");
    }

    @Test
    void givenValidId_whenFindById_thenReturnRun() {
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(3, run.miles());
        assertEquals(Location.INDOOR, run.location());
    }

    @Test
    void givenInvalidId_whenFindById_thenThrowRunNotFoundException() {
        RunNotFoundException notFoundException = assertThrows(RunNotFoundException.class,
                () -> repository.findById(3).get());

        assertEquals("Run not found", notFoundException.getMessage());

    }

    @Test
    void givenValidParams_whenCreateRun_thenCreateRun() {
        repository.create(new Run(3,
                "Saturday Evening Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(40, ChronoUnit.MINUTES),
                4,
                Location.OUTDOOR));
        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size(), "Should have returned 3 runs");
    }

    @Test
    void givenValidId_whenUpdateRun_thenUpdateRun() {
        repository.update(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(45, ChronoUnit.MINUTES),
                5,
                Location.OUTDOOR), 1);
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(5, run.miles());
        assertEquals(Location.OUTDOOR, run.location());
    }

    @Test
    void givenValidId_whenDeleteRun_thenDeleteRun() {
        repository.delete(1);
        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size());
    }
}
