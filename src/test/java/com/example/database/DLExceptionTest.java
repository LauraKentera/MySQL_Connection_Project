package com.example.database;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;

/**
 * Tests DLException handling and logging.
 */
public class DLExceptionTest {

    /**
     * Test if DLException can be thrown and contains a safe user message.
     */
    @Test
    public void testDLExceptionMessage() {
        try {
            throw new DLException(new Exception("Test Exception"));
        } catch (DLException e) {
            assertEquals("Unable to complete operation. Please contact the administrator.", e.getMessage());
        }
    }

    /**
     * Test DLException with additional debugging details.
     */
    @Test
    public void testDLExceptionWithAdditionalInfo() {
        try {
            throw new DLException(new Exception("Database Failure"), Map.of("Action", "Testing DLException"));
        } catch (DLException e) {
            assertEquals("Unable to complete operation. Please contact the administrator.", e.getMessage());
        }
    }

    /**
     * Test if DLException is thrown in real database operations.
     */
    @Test
    public void testDLExceptionInDatabaseOperation() {
        MySQLDatabase db = new MySQLDatabase();
        try {
            db.connect();
            db.getData("INVALID SQL SYNTAX", false); // This should cause an exception
            fail("DLException should have been thrown");
        } catch (DLException e) {
            assertNotNull(e.getMessage()); // Ensure the exception was properly thrown
        } finally {
            try {
                db.close();
            } catch (DLException ignored) {}
        }
    }
}
