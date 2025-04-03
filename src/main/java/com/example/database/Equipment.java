package com.example.database;
import com.example.database.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


/**
 * Equipment class mirrors the Equipment table in the database.
 * Provides methods to fetch, insert, update, and delete records.
 */
public class Equipment {
    private static final Logger logger = LoggerFactory.getLogger(Equipment.class);

    // Attributes corresponding to the Equipment table columns
    private int equipId;
    private String equipmentName;
    private String equipmentDescription;
    private int equipmentCapacity;

    // User authentication
    private User currentUser;


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

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Check authentication
    private boolean isAuthorized(String action) {
        if (currentUser == null) return false;

        return switch (currentUser.getRole()) {
            case "Admin" -> true;
            case "Editor" -> !action.equals("remove");
            case "General" -> action.equals("fetch");
            default -> false;
        };
    }

    // Fetch Method - Retrieves data for this equipmentId and updates attributes
    public void fetch() throws DLException {
        if (!isAuthorized("fetch")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to FETCH method."));
        }
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
        if (!isAuthorized("put")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to PUT method."));
        }

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
        if (!isAuthorized("post")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to POST method."));
        }

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
        if (!isAuthorized("remove")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to REMOVE method."));
        }

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
     * fetchP - Retrieves data for this equipment using a prepared statement.
     */
    public void fetchP() throws DLException {
        if (!isAuthorized("fetch")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to FETCH method."));
        }

        db.connect();
        String sql = "SELECT * FROM equipment WHERE EquipID = ? LIMIT 1";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(equipId));
        try {
            ArrayList<ArrayList<String>> result = db.getData(sql, params, false);
            if (result != null && !result.isEmpty()) {
                ArrayList<String> row = result.get(0);
                // Assuming column order: EquipID, EquipmentName, EquipmentDescription, EquipmentCapacity
                this.equipmentName = row.get(1);
                this.equipmentDescription = row.get(2);
                this.equipmentCapacity = Integer.parseInt(row.get(3));
                System.out.println("Prepared fetch successful for EquipID: " + equipId);
            } else {
                System.out.println("No data found for EquipID: " + equipId);
            }
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Fetching equipment with prepared statement"));
        } finally {
            db.close();
        }
    }

    /**
     * putP - Updates the equipment record using a prepared statement.
     * Returns true if the update affected at least one row.
     */
    public boolean putP() throws DLException {
        if (!isAuthorized("put")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to PUT method."));
        }

        db.connect();
        String sql = "UPDATE equipment SET EquipmentName = ?, EquipmentDescription = ?, EquipmentCapacity = ? WHERE EquipID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(equipmentName);
        params.add(equipmentDescription);
        params.add(String.valueOf(equipmentCapacity));
        params.add(String.valueOf(equipId));
        try {
            boolean success = db.setData(sql, params);
            if (!success) {
                System.out.println("Prepared update failed for EquipID: " + equipId);
            }
            return success;
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Updating equipment with prepared statement"));
        } finally {
            db.close();
        }
    }

    /**
     * postP - Inserts a new equipment record using a prepared statement.
     * Returns true if the insertion was successful.
     */
    public boolean postP() throws DLException {
        if (!isAuthorized("post")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to POST method."));
        }

        db.connect();
        String sql = "INSERT INTO equipment (EquipID, EquipmentName, EquipmentDescription, EquipmentCapacity) VALUES (?, ?, ?, ?)";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(equipId));
        params.add(equipmentName);
        params.add(equipmentDescription);
        params.add(String.valueOf(equipmentCapacity));
        try {
            boolean success = db.setData(sql, params);
            if (!success) {
                System.out.println("Prepared insert failed for EquipID: " + equipId);
            }
            return success;
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Inserting equipment with prepared statement"));
        } finally {
            db.close();
        }
    }

    /**
     * removeP - Deletes the equipment record using a prepared statement.
     * Returns true if the deletion was successful.
     */
    public boolean removeP() throws DLException {
        if (!isAuthorized("remove")) {
            throw new DLException(null, Map.of("Error", "Unauthorized access to REMOVE method."));
        }

        db.connect();
        String sql = "DELETE FROM equipment WHERE EquipID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(equipId));
        try {
            boolean success = db.setData(sql, params);
            if (!success) {
                System.out.println("Prepared delete failed for EquipID: " + equipId);
            }
            return success;
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Deleting equipment with prepared statement"));
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

    public void swapEquipNames(int otherEquipId) throws DLException {
        try {
            // Connect and start transaction
            db.connect();
            db.startTrans();

            // Fetch the other equipment's name and store it locally
            String otherName;
            String sqlSelect = "SELECT EquipmentName FROM equipment WHERE EquipID = ?";
            try (PreparedStatement pstmtSelect = db.getConnection().prepareStatement(sqlSelect)) {
                pstmtSelect.setInt(1, otherEquipId);
                try (ResultSet rs = pstmtSelect.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Equipment not found for EquipID: " + otherEquipId);
                    }
                    // Immediately store the equipment name
                    otherName = rs.getString("EquipmentName");
                }
            }

            // Make sure the current object's equipmentName is loaded (if needed, call fetchP() before swap)
            if (this.equipmentName == null) {
                // Optionally, fetch the current equipment's details
                this.fetchP();
            }
            String tempName = this.equipmentName;

            // Update current equipment's name to the other equipment's name
            String sqlUpdateCurrent = "UPDATE equipment SET EquipmentName = ? WHERE EquipID = ?";
            try (PreparedStatement pstmtUpdateCurrent = db.getConnection().prepareStatement(sqlUpdateCurrent)) {
                pstmtUpdateCurrent.setString(1, otherName);
                pstmtUpdateCurrent.setInt(2, this.equipId);
                pstmtUpdateCurrent.executeUpdate();
            }

            // Update other equipment's name to the current equipment's original name
            String sqlUpdateOther = "UPDATE equipment SET EquipmentName = ? WHERE EquipID = ?";
            try (PreparedStatement pstmtUpdateOther = db.getConnection().prepareStatement(sqlUpdateOther)) {
                pstmtUpdateOther.setString(1, tempName);
                pstmtUpdateOther.setInt(2, otherEquipId);
                pstmtUpdateOther.executeUpdate();
            }

            // Commit transaction
            db.endTrans();
        } catch (SQLException e) {
            try {
                db.rollbackTrans();
            } catch (SQLException ex) {
                logger.error("Error during rollback", ex);
            }
            throw new DLException(e, Map.of("Error", "Error swapping equipment names"));
        } finally {
            try {
                db.close();
            } catch (DLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }

    public void fetchA() throws DLException {
        if (!isAuthorized("fetch")) {
            System.out.println("🚫 Unauthorized access to FETCH method.");
            throw new DLException(null, Map.of("Error", "Unauthorized access to FETCH method."));
        }

        db.connect();
        String sql = "SELECT * FROM equipment WHERE EquipID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(equipId));

        try {
            ArrayList<ArrayList<String>> result = db.getData(sql, params, false);
            if (!result.isEmpty()) {
                ArrayList<String> row = result.get(0);
                this.equipmentName = row.get(1);
                this.equipmentDescription = row.get(2);
                this.equipmentCapacity = Integer.parseInt(row.get(3));
                System.out.println("✅ fetchA successful.");
            } else {
                System.out.println("❌ No equipment found with EquipID: " + equipId);
            }
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Authorized fetch"));
        } finally {
            db.close();
        }
    }

    public boolean putA() throws DLException {
        if (!isAuthorized("put")) {
            System.out.println("🚫 Unauthorized access to PUT method.");
            throw new DLException(null, Map.of("Error", "Unauthorized access to PUT method."));
        }

        db.connect();
        String sql = "UPDATE equipment SET EquipmentName = ?, EquipmentDescription = ?, EquipmentCapacity = ? WHERE EquipID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(equipmentName);
        params.add(equipmentDescription);
        params.add(String.valueOf(equipmentCapacity));
        params.add(String.valueOf(equipId));

        try {
            boolean success = db.setData(sql, params);
            System.out.println(success ? "✅ putA successful." : "❌ putA failed.");
            return success;
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Authorized put"));
        } finally {
            db.close();
        }
    }

    public boolean postA() throws DLException {
        if (!isAuthorized("post")) {
            System.out.println("🚫 Unauthorized access to POST method.");
            throw new DLException(null, Map.of("Error", "Unauthorized access to POST method."));
        }

        db.connect();
        String sql = "INSERT INTO equipment (EquipID, EquipmentName, EquipmentDescription, EquipmentCapacity) VALUES (?, ?, ?, ?)";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(equipId));
        params.add(equipmentName);
        params.add(equipmentDescription);
        params.add(String.valueOf(equipmentCapacity));

        try {
            boolean success = db.setData(sql, params);
            System.out.println(success ? "✅ postA successful." : "❌ postA failed.");
            return success;
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Authorized post"));
        } finally {
            db.close();
        }
    }

    public boolean removeA() throws DLException {
        if (!isAuthorized("remove")) {
            System.out.println("🚫 Unauthorized access to REMOVE method.");
            throw new DLException(null, Map.of("Error", "Unauthorized access to REMOVE method."));
        }

        db.connect();
        String sql = "DELETE FROM equipment WHERE EquipID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(equipId));

        try {
            boolean success = db.setData(sql, params);
            System.out.println(success ? "✅ removeA successful." : "❌ removeA failed.");
            return success;
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Authorized remove"));
        } finally {
            db.close();
        }
    }


}



