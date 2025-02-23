package com.example.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Equipment class mirrors the Equipment table in the database.
 * Provides methods to fetch, insert, update, and delete records.
 */
public class Equipment {
    // Attributes corresponding to the Equipment table columns
    private int equipId;
    private String equipmentName;
    private String equipmentDescription;
    private int equipmentCapacity;

    // Database connection
    private MySQLDatabase db;

    // Default Constructor
    public Equipment() {
        this.db = new MySQLDatabase();  // Initialize the database connection
    }

    // Constructor that accepts and sets only equipId
    public Equipment(int equipId) {
        this();
        this.equipId = equipId;
    }

    // Constructor that accepts and sets all attributes
    public Equipment(int equipId, String equipmentName, String equipmentDescription, int equipmentCapacity) {
        this();
        this.equipId = equipId;
        this.equipmentName = equipmentName;
        this.equipmentDescription = equipmentDescription;
        this.equipmentCapacity = equipmentCapacity;
    }

    // Getters and Setters (Accessors and Mutators)
    public int getEquipId() { return equipId; }
    public void setEquipId(int equipId) { this.equipId = equipId; }

    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

    public String getEquipmentDescription() { return equipmentDescription; }
    public void setEquipmentDescription(String equipmentDescription) { this.equipmentDescription = equipmentDescription; }

    public int getEquipmentCapacity() { return equipmentCapacity; }
    public void setEquipmentCapacity(int equipmentCapacity) { this.equipmentCapacity = equipmentCapacity; }

    // Fetch Method - Retrieves data for this equipmentId and updates attributes
    public void fetch() throws DLException {
        db.connect();
        String sql = "SELECT * FROM equipment WHERE EquipID = " + equipId;

        try {
            ArrayList<ArrayList<String>> result = db.getData(sql, false);


            if (!result.isEmpty()) {
                ArrayList<String> row = result.get(0);
                this.equipmentName = row.get(1);
                this.equipmentDescription = row.get(2);
                this.equipmentCapacity = Integer.parseInt(row.get(3));
                System.out.println("Data fetched successfully for EquipID: " + equipId);
            } else {
                // Reset attributes if no data is found
                this.equipmentName = null;
                this.equipmentDescription = null;
                this.equipmentCapacity = 0;
                System.out.println("No data found for EquipID: " + equipId);
            }

        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Fetching equipment"));
        } finally {
            db.close();
        }
    }

    // Put Method - Updates the existing record in the database
    public boolean put() throws DLException {
        db.connect();
        String sql = String.format(
                "UPDATE equipment SET EquipmentName='%s', EquipmentDescription='%s', EquipmentCapacity=%d WHERE EquipID=%d",
                equipmentName, equipmentDescription, equipmentCapacity, equipId
        );

        try {
            return db.setData(sql);
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Updating equipment"));
        } finally {
            db.close();
        }
    }

    // Post Method - Inserts a new record into the database
    public boolean post() throws DLException {
        db.connect();
        String sql = String.format(
                "INSERT INTO equipment (EquipID, EquipmentName, EquipmentDescription, EquipmentCapacity) " +
                        "VALUES (%d, '%s', '%s', %d)",
                equipId, equipmentName, equipmentDescription, equipmentCapacity
        );

        try {
            return db.setData(sql);
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Inserting equipment"));
        } finally {
            db.close();
        }
    }

    // Remove Method - Deletes the record corresponding to this equipmentId
    public boolean remove() throws DLException {
        db.connect();
        String sql = "DELETE FROM equipment WHERE EquipID = " + equipId;

        try {
            return db.setData(sql);
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Deleting equipment"));
        } finally {
            db.close();
        }
    }

    /**
     * Utility method to print Equipment details.
     */
    public void printEquipment() {
        System.out.println("\nEquipment Details:");
        System.out.println("EquipID: " + equipId);
        System.out.println("Equipment Name: " + equipmentName);
        System.out.println("Equipment Description: " + equipmentDescription);
        System.out.println("Equipment Capacity: " + equipmentCapacity);
    }

}
