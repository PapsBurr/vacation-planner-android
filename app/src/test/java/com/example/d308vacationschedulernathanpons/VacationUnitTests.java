package com.example.d308vacationschedulernathanpons;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.Vacation;

public class VacationUnitTests {

    @Test
    public void vacation_objectIsCreated() {
        Vacation testVacation = new Vacation(1, "Test Vacation Title", "Test Hotel", "12/12/12", "12/13/14", 2);

        assertNotNull(testVacation);
        assertEquals(1, testVacation.getId());
        assertEquals("Test Vacation Title", testVacation.getVacationTitle());
        assertEquals("Test Hotel", testVacation.getHotelName());
        assertEquals("12/12/12", testVacation.getStartDate());
        assertEquals("12/13/14", testVacation.getEndDate());
    }

    @Test
    public void excursion_objectIsCreated() {
        Excursion testExcursion = new Excursion(1, "Excursion Name", "12/12/12", 3, 1);

        assertNotNull(testExcursion);
        assertEquals(1, testExcursion.getId());
        assertEquals("Excursion Name", testExcursion.getExcursionName());
        assertEquals("12/12/12", testExcursion.getExcursionDate());
        assertEquals(3, testExcursion.getVacationId());
        assertEquals(1, testExcursion.getUserId());


        Excursion testExcursion2 = new Excursion(2, "Excursion Name2", "06/01/25", 4, 2);

        assertNotNull(testExcursion2);
        assertEquals(2, testExcursion2.getId());
        assertEquals("Excursion Name2", testExcursion2.getExcursionName());
        assertEquals("06/01/25", testExcursion2.getExcursionDate());
        assertEquals(4, testExcursion2.getVacationId());
        assertEquals(2, testExcursion2.getUserId());
    }

}