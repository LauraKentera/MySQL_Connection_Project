package com.example.database;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;


/**
 * Unit tests for metadata retrieval in MySQLDatabase.
 */
public class MetadataTest {

    /**
     * Test retrieving general database metadata.
     */
    @Test
    public void testDatabaseMetadata() {
        MySQLDatabase db = new MySQLDatabase();
        try {
            db.connect();
            db.printDatabaseInfo(); // Should not throw an exception
            assertTrue(true); // Test passes if no exception is thrown
        } catch (DLException e) {
            fail("Retrieving database metadata should not fail. Exception: " + e.getMessage());
        } finally {
            try {
                db.close();
            } catch (DLException ignored) {}
        }
    }

    /**
     * Test retrieving metadata for the 'equipment' table.
     */
    @Test
    public void testTableMetadata() {
        MySQLDatabase db = new MySQLDatabase();
        try {
            db.connect();
            db.printTableInfo("equipment"); // Should not throw an exception
            assertTrue(true);
        } catch (DLException e) {
            fail("Retrieving table metadata should not fail. Exception: " + e.getMessage());
        } finally {
            try {
                db.close();
            } catch (DLException ignored) {}
        }
    }

    /**
     * Test retrieving metadata for a query result.
     */
    @Test
    public void testQueryMetadata() {
        MySQLDatabase db = new MySQLDatabase();
        try {
            db.connect();
            db.printResultInfo("SELECT * FROM equipment WHERE EquipmentCapacity > 50"); // Should not throw an exception
            assertTrue(true);
        } catch (DLException e) {
            fail("Retrieving query metadata should not fail. Exception: " + e.getMessage());
        } finally {
            try {
                db.close();
            } catch (DLException ignored) {}
        }
    }

    @Test
    public void testFetchWithHeaders() {
        MySQLDatabase db = new MySQLDatabase();
        try {
            db.connect();
            ArrayList<ArrayList<String>> data = db.fetch("SELECT * FROM equipment", true);
            assertNotNull("Data should not be null", data);
            assertFalse("Result should contain at least one row", data.isEmpty());
        } catch (DLException e) {
            fail("Fetching with headers should not fail: " + e.getMessage());
        } finally {
            try {
                db.close();
            } catch (DLException ignored) {}
        }
    }

}
