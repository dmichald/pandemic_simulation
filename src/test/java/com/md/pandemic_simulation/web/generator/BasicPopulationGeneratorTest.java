package com.md.pandemic_simulation.web.generator;

import com.md.pandemic_simulation.data.model.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class BasicPopulationGeneratorTest {
    BasicPopulationGenerator basicPopulationGenerator;
    @BeforeEach
    void setUp() {
        basicPopulationGenerator = new BasicPopulationGenerator();
    }

    @Test
    void generate() {
        for (int i = 0; i < 1000; i++) {
            Simulation simulation = random();
            basicPopulationGenerator.generate(simulation).forEach(daySummary -> assertAll(() -> {
                assertTrue(daySummary.getPi() >=0);
                assertTrue(daySummary.getPv() >=0);
                assertTrue(daySummary.getPm() >=0);
                assertTrue(daySummary.getPr() >=0);
                assertEquals(daySummary.getPi() + daySummary.getPr() + daySummary.getPm() + daySummary.getPv(), simulation.getP());

            }));

        }
    }

    private Simulation random() {

        return Simulation.builder()
                .P(randomNumber(5000,100000))
                .I(randomNumber(1,4999))
                .M(randomIndicator())
                .R(randomNumber(1,100))
                .N("test")
                .Ts(randomNumber(1,5000))
                .Ti(randomNumber(1,100))
                .Tm(randomNumber(1,100))
                .build();
    }

    private int randomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private double randomIndicator() {
        double numb = ThreadLocalRandom.current().nextDouble(0, 1);
        return numb;
    }
}
