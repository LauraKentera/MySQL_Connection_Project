package com.example.database;

/**
 * Main class serves as the entry point of the application.
 * It demonstrates how to connect to and disconnect from a MySQL database
 * using the MySQLDatabase class while handling exceptions properly.
 */
public class Main {
    public static void main(String[] args) {
        try {
            MySQLDatabase db = new MySQLDatabase(); // Create an instance of MySQLDatabase to manage database operations

            // Attempt to connect to the database and handle success or failure
            if (db.connect()) {
                System.out.println("Connection successful.");
                db.close();  // Close after checking the connection
            } else {
                System.out.println("Connection failed.");
                return; // Stop execution if the connection fails
            }

            // Step 1: Fetch an existing Equipment record
            Equipment equipment = new Equipment(568);  // Example EquipID
            try {
                equipment.fetch();
                equipment.printEquipment();
            } catch (DLException e) {
                System.out.println(e.getMessage()); // Print a safe message
            }

            // Step 2: Insert a new Equipment record
            Equipment newEquipment = new Equipment(9999, "Test Bus", "Short Range", 50);
            try {
                if (newEquipment.post()) {
                    System.out.println("\nNew Equipment inserted successfully!");
                } else {
                    System.out.println("\nFailed to insert new Equipment.");
                }
            } catch (DLException e) {
                System.out.println(e.getMessage());
            }

            // Step 3: Fetch and print the newly inserted equipment
            Equipment fetchedNewEquipment = new Equipment(9999);
            try {
                fetchedNewEquipment.fetch();
                fetchedNewEquipment.printEquipment();
            } catch (DLException e) {
                System.out.println(e.getMessage());
            }

            // Step 4: Update the Equipment record
            try {
                fetchedNewEquipment.setEquipmentCapacity(75);
                if (fetchedNewEquipment.put()) {
                    System.out.println("\nEquipment updated successfully!");
                } else {
                    System.out.println("\nFailed to update Equipment.");
                }
            } catch (DLException e) {
                System.out.println(e.getMessage());
            }

            // Step 5: Fetch and display updated data
            try {
                fetchedNewEquipment.fetch();
                fetchedNewEquipment.printEquipment();
            } catch (DLException e) {
                System.out.println(e.getMessage());
            }

            // Step 6: Delete the Equipment record
            try {
                if (fetchedNewEquipment.remove()) {
                    System.out.println("\nEquipment removed successfully!");
                } else {
                    System.out.println("\nFailed to remove Equipment.");
                }
            } catch (DLException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Program execution completed.");

        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please contact the administrator.");
        }
    }
}
