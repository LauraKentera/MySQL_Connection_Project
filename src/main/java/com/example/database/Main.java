package com.example.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Main class serves as the entry point of the application.
 * It demonstrates how to connect to and disconnect from a MySQL database
 * using the MySQLDatabase class while handling exceptions properly.
 */
public class Main {

    // Manually declaring the logger
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        printBanner();  // Print the startup banner

        try {
            MySQLDatabase db = new MySQLDatabase(); // Create an instance of MySQLDatabase to manage database operations

            // Attempt to connect to the database and handle success or failure
            if (db.connect()) {
                log.info("✅ Connection successful.");
            } else {
                log.error("❌ Connection failed.");
                return; // Stop execution if the connection fails
            }

            // Step 1: Fetch an existing Equipment record
            Equipment equipment = new Equipment(568);  // Example EquipID
            try {
                equipment.fetch();
                equipment.printEquipment();
            } catch (DLException e) {
                log.error("⚠️ Error fetching equipment: {}", e.getMessage());
            }

            // Step 2: Insert a new Equipment record
            Equipment newEquipment = new Equipment(9999, "Test Bus", "Short Range", 50);
            try {
                if (newEquipment.post()) {
                    log.info("\n✅ New Equipment inserted successfully!");
                } else {
                    log.warn("\n❌ Failed to insert new Equipment.");
                }
            } catch (DLException e) {
                log.error("⚠️ Error inserting equipment: {}", e.getMessage());
            }

            // Step 3: Fetch and print the newly inserted equipment
            Equipment fetchedNewEquipment = new Equipment(9999);
            try {
                fetchedNewEquipment.fetch();
                fetchedNewEquipment.printEquipment();
            } catch (DLException e) {
                log.error("⚠️ Error fetching new equipment: {}", e.getMessage());
            }

            // Step 4: Update the Equipment record
            try {
                fetchedNewEquipment.setEquipmentCapacity(75);
                if (fetchedNewEquipment.put()) {
                    log.info("\n✅ Equipment updated successfully!");
                } else {
                    log.warn("\n❌ Failed to update Equipment.");
                }
            } catch (DLException e) {
                log.error("⚠️ Error updating equipment: {}", e.getMessage());
            }

            // Step 5: Fetch and display updated data
            try {
                fetchedNewEquipment.fetch();
                fetchedNewEquipment.printEquipment();
            } catch (DLException e) {
                log.error("⚠️ Error fetching updated equipment: {}", e.getMessage());
            }

            // Step 6: Delete the Equipment record
            try {
                if (fetchedNewEquipment.remove()) {
                    log.info("\n✅ Equipment removed successfully!");
                } else {
                    log.warn("\n❌ Failed to remove Equipment.");
                }
            } catch (DLException e) {
                log.error("⚠️ Error removing equipment: {}", e.getMessage());
            }

            // ===============================
            // ✅ Step 7: Metadata Retrieval
            // ===============================

            try {
                log.info("\n=== 📊 Database Metadata ===");
                db.printDatabaseInfo();  // Prints general database metadata

                log.info("\n=== 📋 Table Metadata: equipment ===");
                db.printTableInfo("equipment");  // Prints metadata for the 'equipment' table

                log.info("\n=== 🔎 Query Metadata ===");
                db.printResultInfo("SELECT * FROM equipment WHERE EquipmentCapacity > 50");  // Prints metadata about a sample query

            } catch (DLException e) {
                log.error("⚠️ Error retrieving metadata: {}", e.getMessage());
            }

            db.close();
            log.info("✅ Database connection closed.");

        } catch (Exception e) {
            log.error("❌ An unexpected error occurred. Please contact the administrator.", e);
        }
    }

    /**
     * Prints the startup banner.
     */
    private static void printBanner() {
        String banner = """
                
                ███    ███ ██    ██ ███████  ██████  ██     \s
                ████  ████  ██  ██  ██      ██    ██ ██     \s
                ██ ████ ██   ████   ███████ ██    ██ ██     \s
                ██  ██  ██    ██         ██ ██ ▄▄ ██ ██     \s
                ██      ██    ██    ███████  ██████  ███████\s
                
                Database Connectivity Project
                ----------------------------
                """;

        try {
            String localAddress = InetAddress.getLocalHost().getHostAddress();
            log.info("\n{}", banner);
            log.info("🌐 Running locally: http://127.0.0.1:3306");
            log.info("🌍 External access: http://{}:3306", localAddress);
            log.info("--------------------------------------------------");
        } catch (UnknownHostException e) {
            log.warn("⚠️ Could not determine external IP.");
        }
    }
}
