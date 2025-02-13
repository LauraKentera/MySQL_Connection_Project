# MySQL Connection Project

## Project Overview

This project demonstrates how to establish and manage a connection to a MySQL database using Java. It covers the following functionalities:

- **Database Connectivity:**
   - Establishes a connection to the MySQL database using JDBC.
   - Executes SQL queries and handles database interactions.
   - Ensures proper closure of database connections after each operation.

- **Data Manipulation with the `Equipment` Class:**
   - Mirrors the `equipment` table from the database.
   - Provides methods to:
      - **Insert** new records into the database.
      - **Retrieve** records using specific `EquipID`.
      - **Update** existing records with new data.
      - **Delete** records from the database.

- **Unit Testing:**
   - Verifies database connection success and proper resource management.
   - Tests CRUD (Create, Read, Update, Delete) operations on the `equipment` table to ensure data accuracy and integrity.

## Project Structure

```
MySQL_Connection_Project/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── database/
│       │               ├── MySQLDatabase.java
│       │               ├── Equipment.java
│       │               └── Main.java
│       └── resources/
│           ├── db_config.properties
│           └── travel.sql
├── pom.xml
└── README.md
```

## Prerequisites

- Java 21
- MySQL Server
- Maven
- IntelliJ IDEA (optional for development)

## Setting Up the Database

### For macOS Users:

1. **Ensure MySQL Server is Running:**
   ```bash
   brew services start mysql
   ```

2. **Create the Database:**
   ```bash
   mysql -u root -p
   CREATE DATABASE travel23;
   EXIT;
   ```

3. **Import the SQL File:**
   ```bash
   mysql -u root -p travel23 < src/main/resources/travel.sql
   ```

4. **Verify the Database:**
   ```bash
   mysql -u root -p
   USE travel23;
   SHOW TABLES;
   EXIT;
   ```

### For Windows Users:

1. **Ensure MySQL Server is Running:**
    - Open **Services** (type `services.msc` in the Start menu).
    - Find **MySQL** service, right-click, and select **Start**.

2. **Create the Database:**
    - Open **Command Prompt**:
      ```bash
      mysql -u root -p
      CREATE DATABASE travel23;
      EXIT;
      ```

3. **Import the SQL File:**
    - Navigate to the project directory:
      ```bash
      cd path\to\MySQL_Connection_Project
      mysql -u root -p travel23 < src\main\resources\travel.sql
      ```

4. **Verify the Database:**
   ```bash
   mysql -u root -p
   USE travel23;
   SHOW TABLES;
   EXIT;
   ```

### For Linux Users:

1. **Ensure MySQL Server is Running:**
   ```bash
   sudo systemctl start mysql
   ```

2. **Create the Database:**
   ```bash
   mysql -u root -p
   CREATE DATABASE travel23;
   EXIT;
   ```

3. **Import the SQL File:**
   ```bash
   mysql -u root -p travel23 < src/main/resources/travel.sql
   ```

4. **Verify the Database:**
   ```bash
   mysql -u root -p
   USE travel23;
   SHOW TABLES;
   EXIT;
   ```

## Configuring the Application

Update the `db_config.properties` file located in `src/main/resources/`:

```
db.url=jdbc:mysql://localhost:3306/travel23
db.username=root
db.password=
```

Ensure the credentials match your MySQL configuration.

## Building and Running the Project

1. **Build the Project:**
   ```bash
   mvn clean install
   ```

2. **Run the Application:**
   ```bash
   mvn exec:java -Dexec.mainClass=com.example.database.Main
   ```

## Running Tests

This project includes unit tests to verify both database connectivity and CRUD operations on the `Equipment` table.

1. **Run the Tests:**
   ```bash
   mvn test
   ```
   
## Running the Application with Manual Java Command

If you prefer running the program directly with the `java` command instead of Maven, use the following structure:

   ```bash
   java -cp "target/classes:/path/to/mysql-connector-j-x.x.x.jar" -Djava.library.path=lib com.example.database.Main
   ```

### Explanation of the Command:

- **`java -cp`**: Specifies the **classpath**, which tells Java where to find the compiled classes and external libraries.
   - `target/classes`: The directory containing your compiled `.class` files.
   - `/path/to/mysql-connector-j-x.x.x.jar`: The path to the **MySQL JDBC driver**. Replace `x.x.x` with the actual version you have installed.
      - For Maven users, this `.jar` file is usually found in the `~/.m2/repository/com/mysql/mysql-connector-j/` directory.

- **`-Djava.library.path=lib`**: (Optional) Sets the **library path** if your program relies on native libraries. You can omit this if not needed.

- **`com.example.database.Main`**: The fully qualified name of the **main class** to execute.

### Important Notes:

- **Classpath Separators:**
   - Use `:` on **macOS/Linux**.
   - Use `;` on **Windows**.

- **JDBC Driver Path:** Ensure the path to the MySQL Connector `.jar` matches the version installed on your system.

## Troubleshooting

- **Unknown Database Error:** Ensure the database name in `db_config.properties` matches the actual database.
- **Connection Refused:** Verify MySQL is running.
- **Access Denied:** Check MySQL user permissions.

## Notes

- This project uses Maven for dependency management.
- Ensure the `travel.sql` file is correctly imported to avoid runtime errors.
- For any issues with IntelliJ IDEA, rebuild the project or invalidate caches if necessary.
- Basic unit tests are included to verify database connection functionality.

## License

This project is for educational purposes as part of the ISTE-330 course.

