package com.example.database;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the MySQLDatabase and Equipment classes.
 */
public class MySQLDatabaseTest {

    /**
     * Test basic database connection and disconnection.
     */
    @Test
    public void testConnection() {
        MySQLDatabase db = new MySQLDatabase();
        assertTrue("Connection should be successful", db.connect());
        assertTrue("Connection should close successfully", db.close());
    }

    /**
     * Test CRUD operations on the Equipment table.
     */
    @Test
    public void testEquipmentCRUDOperations() {
        Equipment equipment = new Equipment(9999, "JUnit Test Equipment", "Test Description", 50);

        // Test Insert (post)
        assertTrue("Inserting equipment should be successful", equipment.post());

        // Test Fetch
        equipment.fetch();
        assertEquals("Equipment Name should match", "JUnit Test Equipment", equipment.getEquipmentName());
        assertEquals("Equipment Description should match", "Test Description", equipment.getEquipmentDescription());
        assertEquals("Equipment Capacity should match", 50, equipment.getEquipmentCapacity());

        // Test Update (put)
        equipment.setEquipmentCapacity(75);
        assertTrue("Updating equipment should be successful", equipment.put());

        // Fetch again to verify update
        equipment.fetch();
        assertEquals("Updated Equipment Capacity should match", 75, equipment.getEquipmentCapacity());

        // Test Delete (remove)
        assertTrue("Removing equipment should be successful", equipment.remove());

        // Fetch after deletion to ensure it no longer exists
        equipment.fetch();
        assertNull("Equipment Name should be null after deletion", equipment.getEquipmentName());
    }
}
