package com.example.database;

/**
 * Main class serves as the entry point of the application.
 * It demonstrates how to connect to and disconnect from a MySQL database
 * using the MySQLDatabase class.
 */

public class Main {
    /**
     * The main method initializes the MySQLDatabase object,
     * attempts to establish a database connection,
     * and ensures the connection is properly closed afterward.
     */
    public static void main(String[] args) {
        MySQLDatabase db = new MySQLDatabase(); // Create an instance of MySQLDatabase to manage database operations

        // Attempt to connect to the database and handle success or failure
        if (db.connect()) {
            System.out.println("Connection successful.");   // Confirm successful database connection

            // Instantiate Equipment object using the equipId constructor
            Equipment equipment = new Equipment(568);  // Example EquipID; adjust as needed

            // Fetch data from the database
            equipment.fetch();

            // Display fetched data
            equipment.printEquipment();

            // Test inserting a new record (post)
            Equipment newEquipment = new Equipment(9999, "Test Bus", "Short Range", 50);
            if (newEquipment.post()) {
                System.out.println("\nNew Equipment inserted successfully!");
            } else {
                System.out.println("\\nFailed to insert new Equipment.");
            }

            // Fetch and print the newly inserted equipment
            Equipment fetchedNewEquipment = new Equipment(9999);
            fetchedNewEquipment.fetch();
            fetchedNewEquipment.printEquipment();

            // Test updating the new record (put)
            fetchedNewEquipment.setEquipmentCapacity(75);
            if (fetchedNewEquipment.put()) {
                System.out.println("\nEquipment updated successfully!");
            } else {
                System.out.println("\nFailed to update Equipment.");
            }

            // Fetch and display updated data
            fetchedNewEquipment.fetch();
            fetchedNewEquipment.printEquipment();

            // Test deleting the record (remove)
            if (fetchedNewEquipment.remove()) {
                System.out.println("\nEquipment removed successfully!");
            } else {
                System.out.println("\nFailed to remove Equipment.");
            }

        } else {
            System.out.println("Connection failed.");       // Notify if the connection attempt fails
        }

        // Attempt to close the database connection and handle success or failure
        if (db.close()) {
            System.out.println("Connection closed successfully.");  // Confirm that the database connection was closed
        } else {
            System.out.println("Failed to close connection.");      // Notify if the connection closure fails
        }
    }
}
