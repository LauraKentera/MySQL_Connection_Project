package com.example.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for testing Equipment prepared statement methods.
 */
public class EquipmentPreparedTest {

    private Equipment testEquipment;
    private final int testEquipId = 12345;

    @BeforeEach
    public void setup() throws DLException {
        // Create and insert a test Equipment record using the prepared method.
        testEquipment = new Equipment(testEquipId, "JUnit Test", "JUnit Description", 10);
        boolean inserted = testEquipment.postP();
        assertTrue(inserted, "Equipment record should be inserted successfully.");
    }

    @AfterEach
    public void cleanup() throws DLException {
        // Delete the test record to keep the database clean.
        boolean removed = testEquipment.removeP();
        assertTrue(removed, "Equipment record should be removed successfully.");
    }

    @Test
    public void testFetchP() throws DLException {
        Equipment fetchedEquipment = new Equipment(testEquipId);
        fetchedEquipment.fetchP();
        assertEquals("JUnit Test", fetchedEquipment.getEquipmentName(), "Equipment name should match.");
        assertEquals("JUnit Description", fetchedEquipment.getEquipmentDescription(), "Equipment description should match.");
        assertEquals(10, fetchedEquipment.getEquipmentCapacity(), "Equipment capacity should match.");
    }

    @Test
    public void testPutP() throws DLException {
        // Update the record's capacity.
        testEquipment.setEquipmentCapacity(20);
        boolean updated = testEquipment.putP();
        assertTrue(updated, "Equipment update should succeed.");

        Equipment fetchedEquipment = new Equipment(testEquipId);
        fetchedEquipment.fetchP();
        assertEquals(20, fetchedEquipment.getEquipmentCapacity(), "Equipment capacity should be updated to 20.");
    }

    @Test
    public void testExecuteProc() throws DLException {
        // Test the stored procedure. Make sure the stored procedure getTotalEquipment exists in your DB.
        MySQLDatabase db = new MySQLDatabase();
        try {
            db.connect();
            ArrayList<String> procParams = new ArrayList<>(); // No parameters needed for this procedure.
            int total = db.executeProc("getTotalEquipment", procParams);
            // We expect the total count to be non-negative.
            assertTrue(total >= 0, "Total equipment count should be non-negative.");
        } finally {
            db.close();
        }
    }
}
