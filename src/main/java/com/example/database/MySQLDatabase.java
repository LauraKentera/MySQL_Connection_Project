package com.example.database;

import java.io.InputStream;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Manages MySQL database connectivity, query execution, and metadata retrieval.
 * Provides utility functions for retrieving table and database metadata.
 */
public class MySQLDatabase {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private int failedLoginAttempts = 0;

    /**
     * Constructor: Loads database credentials from properties file.
     */
    public MySQLDatabase() {
        loadConfig();
    }

    /**
     * Loads database configuration from the `db_config.properties` file.
     */
    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db_config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.url = prop.getProperty("db.url");
            this.username = prop.getProperty("db.username");
            this.password = prop.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    /**
     * Establishes a connection to the database.
     * @return true if the connection is successful, otherwise false.
     * @throws DLException if a database connection error occurs.
     */
    public boolean connect() throws DLException {
        try {
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            throw new DLException(e, Map.of("Database URL", url, "Action", "Connecting to database"));
        }
    }

    /**
     * Closes the current database connection.
     * @return true if the connection is successfully closed, otherwise false.
     * @throws DLException if an error occurs while closing the connection.
     */
    public boolean close() throws DLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            return true;
        } catch (SQLException e) {
            throw new DLException(e, Map.of("Action", "Closing database connection"));
        }
    }

    public Connection getConnection() {
        return connection;
    }


    /**
     * Executes a SELECT SQL query and returns the result as a 2D ArrayList.
     * Optionally includes column headers as the first row.
     *
     * @param sql The SQL SELECT query.
     * @param includeHeaders If true, includes column names as the first row.
     * @return A 2D ArrayList containing query results.
     * @throws DLException if a database query error occurs.
     */
    public ArrayList<ArrayList<String>> getData(String sql, boolean includeHeaders) throws DLException {
        return executeQuery(sql, includeHeaders);
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE SQL statement.
     *
     * @param sql The SQL statement to execute.
     * @return true if the operation is successful, false otherwise.
     * @throws DLException if a database update error occurs.
     */
    public boolean setData(String sql) throws DLException {
        return executeUpdate(sql);
    }

    /**
     * Executes an SQL SELECT query and retrieves results as a 2D list.
     * Uses this helper function to remove redundant code.
     */
    private ArrayList<ArrayList<String>> executeQuery(String sql, boolean includeHeaders) throws DLException {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            if (includeHeaders) {
                results.add(getColumnHeaders(metaData));
            }

            while (rs.next()) {
                results.add(getRowData(rs, metaData.getColumnCount()));
            }

        } catch (SQLException e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Executing Query"));
        }
        return results;
    }

    /**
     * Fetches data using the new getData method.
     * This method exists exclusively to satisfy assignment requirements.
     *
     * @param sql The SQL SELECT query.
     * @param includeHeaders If true, includes column names as the first row.
     * @return A 2D ArrayList containing query results.
     * @throws DLException if a database query error occurs.
     */
    public ArrayList<ArrayList<String>> fetch(String sql, boolean includeHeaders) throws DLException {
        return getData(sql, includeHeaders); // Calls getData() directly to avoid duplication
    }


    /**
     * Executes an UPDATE, INSERT, or DELETE SQL statement.
     */
    private boolean executeUpdate(String sql) throws DLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            throw new DLException(e, Map.of("SQL Query", sql, "Action", "Executing Update"));
        }
    }

    /**
     * Retrieves column headers from a ResultSetMetaData object.
     */
    private ArrayList<String> getColumnHeaders(ResultSetMetaData metaData) throws SQLException {
        ArrayList<String> headers = new ArrayList<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            headers.add(metaData.getColumnName(i));
        }
        return headers;
    }

    /**
     * Retrieves row data from a ResultSet object.
     */
    private ArrayList<String> getRowData(ResultSet rs, int columnCount) throws SQLException {
        ArrayList<String> row = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            row.add(rs.getString(i));
        }
        return row;
    }

    /**
     * Retrieves and prints general database metadata information.
     */
    public void printDatabaseInfo() throws DLException {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println("\n=== Database Metadata ===");
            System.out.println("Product Name: " + metaData.getDatabaseProductName());
            System.out.println("Product Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Driver Name: " + metaData.getDriverName());
            System.out.println("Driver Version: " + metaData.getDriverVersion());

            try (ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    System.out.println("- " + tables.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            throw new DLException(e, Map.of("Action", "Retrieving database metadata"));
        }
    }

    /**
     * Retrieves and prints metadata information for a given table, including columns and primary keys.
     */
    public void printTableInfo(String tableName) throws DLException {
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            // Retrieve column details
            try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
                while (columns.next()) {
                    System.out.println("- " + columns.getString("COLUMN_NAME") + " (" + columns.getString("TYPE_NAME") + ")");
                }
            }

            // Retrieve primary keys
            System.out.println("\n🔑 Primary Keys:");
            boolean hasPrimaryKey = false;
            try (ResultSet pk = metaData.getPrimaryKeys(null, null, tableName)) {
                while (pk.next()) {
                    hasPrimaryKey = true;
                    System.out.println("- " + pk.getString("COLUMN_NAME"));
                }
            }

            if (!hasPrimaryKey) {
                System.out.println("❌ No primary key found.");
            }

        } catch (SQLException e) {
            throw new DLException(e, Map.of("Table", tableName, "Action", "Retrieving table metadata"));
        }
    }

    /**
     * Retrieves and prints metadata information about a SQL query result.
     */
    public void printResultInfo(String query) throws DLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            printColumnMetadata(metaData);
        } catch (SQLException e) {
            throw new DLException(e, Map.of("SQL Query", query, "Action", "Retrieving query metadata"));
        }
    }

    /**
     * Helper method to print column metadata.
     */
    private void printColumnMetadata(ResultSetMetaData metaData) throws SQLException {
        int columnCount = metaData.getColumnCount();
        System.out.println("Column Count: " + columnCount);
        for (int i = 1; i <= columnCount; i++) {
            System.out.println("- " + metaData.getColumnName(i) + " (" + metaData.getColumnTypeName(i) + ")");
        }
    }

    // Prepares a PreparedStatement with the provided SQL and parameter values.
    public PreparedStatement prepare(String sql, ArrayList<String> values) throws DLException {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            // Bind each value to its corresponding placeholder (1-indexed)
            for (int i = 0; i < values.size(); i++) {
                ps.setString(i + 1, values.get(i));
            }
            return ps;
        } catch (SQLException e) {
            throw new DLException(e, Map.of("SQL Statement", sql, "Action", "Preparing statement"));
        }
    }

    /**
     * Executes a SELECT query using a prepared statement.
     * Converts the ResultSet into a 2D ArrayList of strings with the first row being column names (if includeHeaders is true).
     * Returns null if the query fails.
     */
    public ArrayList<ArrayList<String>> getData(String sql, ArrayList<String> values, boolean includeHeaders) throws DLException {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        try {
            PreparedStatement ps = prepare(sql, values);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                if (includeHeaders) {
                    ArrayList<String> headers = new ArrayList<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        headers.add(meta.getColumnName(i));
                    }
                    results.add(headers);
                }
                while (rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            return null;  // Query failed, return null
        }
        return results;
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE query using a prepared statement.
     * Returns true if at least one row was affected, false otherwise.
     */
    public boolean setData(String sql, ArrayList<String> values) throws DLException {
        try {
            PreparedStatement ps = prepare(sql, values);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Executes a stored procedure using a callable statement.
     * The procedure is assumed to return a single integer value.
     *
     * @param procName The name of the stored procedure.
     * @param values An ArrayList of string values to bind as parameters.
     * @return The integer value returned by the stored procedure.
     * @throws DLException if the execution fails.
     */
    public int executeProc(String procName, ArrayList<String> values) throws DLException {
        try {
            // Build the call string dynamically.
            String call = "{ ? = call " + procName + "(";
            for (int i = 0; i < values.size(); i++) {
                call += (i == 0 ? "?" : ",?");
            }
            call += ") }";
            CallableStatement cs = connection.prepareCall(call);
            cs.registerOutParameter(1, Types.INTEGER);
            for (int i = 0; i < values.size(); i++) {
                cs.setString(i + 2, values.get(i));
            }
            cs.execute();
            int result = cs.getInt(1);
            cs.close();
            return result;
        } catch (SQLException e) {
            throw new DLException(e, Map.of("Stored Procedure", procName, "Action", "Executing stored procedure"));
        }
    }


    public void startTrans() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void endTrans() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTrans() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    // Authentication - Exercise 8
    public User authenticateUser(String inputId, String inputPassword) throws DLException {
        connect();
        String sql = "SELECT * FROM User WHERE Id = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(inputId);

        try {
            ArrayList<ArrayList<String>> results = getData(sql, params, false);
            if (results == null || results.isEmpty()) return null;

            ArrayList<String> row = results.get(0);
            String storedHash = row.get(3);
            String inputHash = hashPassword(inputPassword);

            if (storedHash.equals(inputHash)) {
                return new User(row.get(0), row.get(1), row.get(2), storedHash, row.get(4), row.get(5));
            }
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL", sql, "Action", "Authenticating user"));
        } finally {
            close();
        }

        return null;
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return java.util.Base64.getEncoder().encodeToString(hash);
    }


    public User login(String userId, String password) throws DLException {
        User user = authenticateUser(userId, password);

        if (user != null) {
            failedLoginAttempts = 0;  // reset on success
            System.out.println("✅ Login successful for user: " + user.getId());
            return user;
        } else {
            failedLoginAttempts++;
            System.out.println("❌ Login failed. Attempt " + failedLoginAttempts + " of 3.");

            if (failedLoginAttempts >= 3) {
                System.out.println("🚫 Too many failed login attempts. Terminating application.");
                System.exit(1);
            }

            return null;
        }
    }

    public boolean addUser(User newUser, String rawPassword) throws DLException {
        connect();

        String sql = "INSERT INTO User (Id, FirstName, LastName, Password, Role, OrganizationUnit) VALUES (?, ?, ?, ?, ?, ?)";
        ArrayList<String> params = new ArrayList<>();
        try {
            String hashedPassword = hashPassword(rawPassword);
            params.add(newUser.getId());
            params.add(newUser.getFirstName());
            params.add(newUser.getLastName());
            params.add(hashedPassword);
            params.add(newUser.getRole());
            params.add(newUser.getOrganizationUnit());

            boolean success = setData(sql, params);
            if (success) {
                System.out.println("✅ User added successfully.");
            } else {
                System.out.println("❌ Failed to add user.");
            }
            return success;

        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL", sql, "Action", "Adding new user"));
        } finally {
            close();
        }
    }

    public void displayAllEquipment() throws DLException {
        connect();
        String sql = "SELECT * FROM Equipment ORDER BY EquipID";
        try {
            ArrayList<ArrayList<String>> results = getData(sql, true);
            System.out.println("\n📋 Equipment Table:");
            for (ArrayList<String> row : results) {
                System.out.printf("%-6s %-20s %-25s %-10s%n", row.get(0), row.get(1), row.get(2), row.get(3));
            }
        } catch (Exception e) {
            throw new DLException(e, Map.of("SQL", sql, "Action", "Displaying equipment table"));
        } finally {
            close();
        }
    }


}
