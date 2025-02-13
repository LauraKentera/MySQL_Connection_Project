package com.example.database;

/*
 * MySQLDatabase class handles connecting to a MySQL database.
 * It provides methods to establish and close the connection,
 * and allows configuration through external properties.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class MySQLDatabase {
    // Database connection parameters
    private String url;             // Holds the JDBC URL for the database
    private String username;        // Username for MySQL authentication
    private String password;        // Password for MySQL authentication
    private Connection connection;  // JDBC Connection object to manage the database connection

    /**
     * Constructor initializes the database configuration
     * by loading connection details from the properties file.
     */
    public MySQLDatabase() {
        loadConfig();
    }

    /**
     * Loads database configuration from the 'db_config.properties' file.
     * Retrieves the URL, username, and password required for the connection.
     */
    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db_config.properties")) {
            Properties prop = new Properties();         // Create a Properties object to hold configuration key-value pairs
            prop.load(input);                           // Load properties from the input stream (db_config.properties)
            this.url = prop.getProperty("db.url");
            this.username = prop.getProperty("db.username");
            this.password = prop.getProperty("db.password");

            // Debugging print to confirm the URL
            System.out.println("Connecting to database: " + this.url);
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    /**
     * Establishes a connection to the MySQL database using provided credentials.
     * @return true if the connection is successful, false otherwise.
     */
    public boolean connect() throws DLException {
        try {
            connection = DriverManager.getConnection(url, username, password);  // Attempt to establish the database connection
            System.out.println("Database connection established successfully.");
            return true;
        } catch (SQLException e) {
            throw new DLException(e, Map.of("Database URL", url, "Action", "Connecting to database"));
        }
    }

    /**
     * Closes the active database connection if it exists.
     * @return true if the connection is closed successfully, false otherwise.
     */
    public boolean close() throws DLException {
        try {
            if (connection != null && !connection.isClosed()) { // Check if the connection exists and is open before attempting to close
                connection.close();                             // Close the database connection
                System.out.println("Database connection closed successfully.");
                return true;
            }
        } catch (SQLException e) {
            throw new DLException(e, Map.of("Action", "Closing database connection"));
        }
        return false;
    }

    // Setters (optional for changing parameters after initialization)
    public void setUrl(String url) { this.url = url; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    /**
     * Executes a SELECT SQL query and returns the result as a 2D array of strings.
     * This method follows the JDBC Statement and ResultSet usage from the lecture.
     * @param sql The SELECT statement to execute.
     * @return A 2D ArrayList containing the query result.
     */
    public ArrayList<ArrayList<String>> getData(String sql) throws DLException {
        ArrayList<ArrayList<String>> resultData = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();  // Creating a statement
            ResultSet rs = stmt.executeQuery(sql);          // Executing the SELECT statement

            int numCols = rs.getMetaData().getColumnCount();  // Get the number of columns

            // Retrieving results using ResultSet
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 1; i <= numCols; i++) {
                    row.add(rs.getString(i));  // Retrieving all columns as Strings
                }
                resultData.add(row);
            }

            rs.close();   // Cleaning up ResultSet
            stmt.close(); // Cleaning up Statement

        } catch (SQLException e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Updating database"));
        }

        return resultData;
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE SQL statement.
     * This method uses executeUpdate() as outlined in the lecture.
     * @param sql The SQL statement to execute.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean setData(String sql) {
        try {
            Statement stmt = connection.createStatement();   // Creating a statement
            stmt.executeUpdate(sql);                         // Executing the update
            stmt.close();                                    // Cleaning up Statement
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
