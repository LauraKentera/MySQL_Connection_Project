## **🚀 MySQL Connection Project**

## **📌 Project Overview**
This project demonstrates how to establish and manage a connection to a **MySQL database** using Java.

### **🔹 Features:**
- **🛠️ Database Connectivity:** Establishes and manages MySQL connections using JDBC.
- **📄 Data Manipulation:** Perform CRUD operations on the `equipment` table.
- **⚠️ Exception Handling:** Implements structured error handling via `DLException`.
- **📊 Metadata Retrieval:** Extracts table, database, and query metadata dynamically.
- **🧪 Unit Testing:** Ensures robust testing for database operations.

---

## **📂 Project Structure**

```
📁 MySQL_Connection_Project/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/example/database/
│   │   │   ├── 📄 MySQLDatabase.java
│   │   │   ├── 📄 Equipment.java
│   │   │   ├── 📄 DLException.java
│   │   │   ├── 📄 Main.java
│   ├── 📁 resources/
│   │   ├── 📝 db_config.properties
│   │   ├── 📝 travel.sql
│   ├── 📁 test/
│   │   ├── 📄 MySQLDatabaseTest.java
│   │   ├── 📄 EquipmentTest.java
│   │   ├── 📄 DLExceptionTest.java
├── 📝 pom.xml
└── 📝 README.md
```

---

## **⚙️ Prerequisites**
- **☕ Java 21**
- **🐬 MySQL Server**
- **🐘 Maven**
- **🖥️ IntelliJ IDEA** *(optional for development)*

---

## **📦 Setting Up the Database**

### **💻 macOS Users:**
```bash
brew services start mysql
mysql -u root -p
CREATE DATABASE travel23;
mysql -u root -p travel23 < src/main/resources/travel.sql
USE travel23;
SHOW TABLES;
EXIT;
```

### **🖥️ Windows Users:**
```bash
mysql -u root -p
CREATE DATABASE travel23;
mysql -u root -p travel23 < src\main\resources\travel.sql
USE travel23;
SHOW TABLES;
EXIT;
```

### **🐧 Linux Users:**
```bash
sudo systemctl start mysql
mysql -u root -p
CREATE DATABASE travel23;
mysql -u root -p travel23 < src/main/resources/travel.sql
USE travel23;
SHOW TABLES;
EXIT;
```

---

## **🛠️ Configuring the Application**

Update **`db_config.properties`** in **`src/main/resources/`**:
```
db.url=jdbc:mysql://localhost:3306/travel23
db.username=root
db.password=
```
Ensure your credentials **match** your MySQL setup.

---

## **🚀 Building and Running the Project**

### **🏗️ Build the Project:**
```bash
mvn clean install
```

### **▶️ Run the Application:**
```bash
mvn exec:java -Dexec.mainClass=com.example.database.Main
```

### **💻 Running with Manual Java Command:**
```bash
java -cp "target/classes:/path/to/mysql-connector-j-x.x.x.jar" -Djava.library.path=lib com.example.database.Main
```

---

## **📊 Database Metadata Retrieval**

This project **supports retrieving metadata** from the database and tables.  
These methods allow you to **view important details** about the database structure, tables, and query results.

### **📋 Available Metadata Methods**
| 🏷️ Method Name | 📄 Description |
|---------------|-------------|
| `printDatabaseInfo()` | Displays database product name, version, driver details, and all available tables. |
| `printTableInfo(String tableName)` | Prints the structure of a table, including column names, types, and primary keys. |
| `printResultInfo(String query)` | Displays metadata for a query, including column count and column types. |

✅ **Example Output for `printDatabaseInfo()`**
```
=== Database Metadata ===
Product Name: MySQL
Product Version: 9.2.0
Driver Name: MySQL Connector/J
Driver Version: mysql-connector-j-8.0.33
Tables in Database:
- sys_config
- equipment
- locations
- passenger
- phones
- staff
- trip
- trip_directory
- trip_people
- tripcodes
- zips
```

✅ **Example Output for `printTableInfo("equipment")`**
```
=== Table Metadata: equipment ===
- EquipID (INT)
- EquipmentName (VARCHAR)
- EquipmentDescription (VARCHAR)
- EquipmentCapacity (INT)
Primary Keys:
- EquipID
```

---

## **🔄 Equipment Swap Functionality**

The project now includes a transactional equipment swap feature that allows you to swap the names of two equipment records in a single atomic operation.

### **How It Works:**
- **Atomic Swap:**  
  The `swapEquipNames()` method in the `Equipment` class performs the swap within a transaction. This ensures that both equipment records are updated together—either both updates succeed or both fail, preserving data integrity.

- **Swap Operation:**  
  The method fetches the name of the second equipment (using its EquipID), stores the current equipment’s name in a temporary variable, and then updates both records to swap their names.

- **Logging & Error Handling:**  
  Robust logging via SLF4J captures the swap process, including headers and details on which IDs are being swapped. In case of an error, the transaction is rolled back to prevent partial updates.

### **Usage:**
- **Implementation:**  
  The swap functionality is invoked in `Main.java`.

- **Verification:**  
  The operation is verified through console outputs, ensuring that the swap occurred as expected.

Add this functionality to further enhance secure, transactional data manipulation within your MySQL Connection Project.

---

## **🧪 Running Tests**

This project includes unit tests to verify:
- ✅ **Database connectivity & CRUD operations** (`EquipmentTest.java`)
- ✅ **Exception handling & error logging** (`DLExceptionTest.java`)
- ✅ **General database operations** (`MySQLDatabaseTest.java`)

### **▶️ Run All Tests:**
```bash
mvn test
```

### **🔍 Run a Specific Test (e.g., DLExceptionTest only):**
```bash
mvn -Dtest=DLExceptionTest test
```

---

## **⚠️ Exception Handling and Logging**

This project **implements structured exception handling** using the custom `DLException` class.

### **🔹 How Exception Handling Works:**
- **All database operations** are wrapped in try-catch blocks and throw `DLException`.
- **Errors are logged** to `error_log.txt` and SLF4J for debugging.
- **Ensures safe user messages** instead of exposing sensitive system errors.

### **📄 Where Errors Are Logged:**
- **📝 File Log:** `error_log.txt`
- **🖥️ Console Log:** SLF4J structured logging

✅ **Example Error Log for an Invalid Query**
```
=== ERROR LOG ===
Timestamp: 2025-02-23 18:22:53
Exception Type: java.sql.SQLException
Message: Statement.executeQuery() cannot issue statements that do not produce result sets.
SQLState: S1009
Vendor Error Code: 0
Reason: null
Additional Info:
  SQL Query: INVALID SQL SYNTAX
  Action: Executing Query
=================
```

By ensuring **safe error handling**, we prevent **sensitive database information** from being exposed.

---

## **📜 License**

This project is developed for **educational purposes** as part of the **ISTE-330 Database Connectivity and Access** course at Rochester Institute of Technology (RIT) Croatia.

### **🔖 License Type**
This project is licensed under the **MIT License**, meaning you can freely use, modify, and distribute it under the following conditions:
- **Attribution Required** – If you modify or distribute this project, you must include the original license notice.
- **No Warranty** – This software is provided "as-is" without any guarantees or warranties of any kind.

### **📄 Third-Party Dependencies & Licenses**
| 📦 Dependency | 📜 License |  
|--------------|------------|  
| **MySQL Connector/J** | GNU General Public License v2 (with FOSS exception) |  
| **SLF4J (Simple Logging Facade for Java)** | MIT License |  
| **JUnit** | Eclipse Public License 2.0 |  
| **Lombok** | MIT License |  

🔗 **License References:**
- [MySQL Connector/J License](https://dev.mysql.com/doc/connector-j/en/)
- [SLF4J License](http://www.slf4j.org/license.html)
- [JUnit License](https://www.eclipse.org/legal/epl-2.0/)
- [Lombok License](https://github.com/projectlombok/lombok/blob/master/LICENSE)

---

## **📢 Disclaimer**
This software is **strictly for educational purposes** and is **not intended for production use**.  
By using this project, you acknowledge that it comes **without any warranty** or **guarantee of correctness**.

---