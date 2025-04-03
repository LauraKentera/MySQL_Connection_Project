package com.example.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Custom exception class to handle and log database-related and general exceptions.
 */
public class DLException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(DLException.class);

    /**
     * Constructor that accepts a single exception and logs it.
     *
     * @param e The caught exception
     */
    public DLException(Exception e) {
        super("Unable to complete operation. Please contact the administrator.");
        log(e, null);
    }

    /**
     * Constructor that accepts an exception and additional key-value pair details.
     * Used for adding more debugging context.
     *
     * @param e The caught exception
     * @param additionalInfo Additional context about the error (key-value pairs)
     */
    public DLException(Exception e, Map<String, String> additionalInfo) {
        super("Unable to complete operation. Please contact the administrator.");
        log(e, additionalInfo);
    }

    /**
     * Logs exception details to a file and SLF4J.
     *
     * @param e The caught exception
     * @param additionalInfo Additional context for debugging (if any)
     */
    private void log(Exception e, Map<String, String> additionalInfo) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        try (FileWriter fw = new FileWriter("error_log.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("=== ERROR LOG ===");
            pw.println("Timestamp: " + timestamp);

            if (e != null) {
                pw.println("Exception Type: " + e.getClass().getName());
                pw.println("Message: " + e.getMessage());

                // Handle SQL Exceptions specifically
                if (e instanceof SQLException sqlEx) {
                    pw.println("SQLState: " + sqlEx.getSQLState());
                    pw.println("Vendor Error Code: " + sqlEx.getErrorCode());
                    pw.println("Reason: " + sqlEx.getCause());
                }
            } else {
                pw.println("Exception: (none - manually thrown error)");
            }

            if (additionalInfo != null && !additionalInfo.isEmpty()) {
                pw.println("Additional Info:");
                additionalInfo.forEach((key, value) -> pw.println("  " + key + ": " + value));
            }

            pw.println("=================\n");

            // Log to SLF4J
            if (e != null) {
                if (additionalInfo != null && !additionalInfo.isEmpty()) {
                    logger.error("Timestamp: {} | Exception: {} | Additional Info: {}", timestamp, e.getMessage(), additionalInfo, e);
                } else {
                    logger.error("Timestamp: {} | Exception: {}", timestamp, e.getMessage(), e);
                }
            } else {
                logger.error("Timestamp: {} | Manual DLException triggered. Info: {}", timestamp, additionalInfo);
            }

        } catch (Exception logException) {
            logger.error("Timestamp: {} | Failed to write to log file: {}", timestamp, logException.getMessage());
        }
    }

}
