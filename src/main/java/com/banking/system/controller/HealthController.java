package com.banking.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {
	
	private final DataSource dataSource;
	
	@GetMapping("/db")
	public ResponseEntity<Map<String, Object>> checkDatabaseConnection() {
		Map<String, Object> response = new HashMap<>();
		
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			
			// Get database info
			String databaseProductName = metaData.getDatabaseProductName();
			String databaseProductVersion = metaData.getDatabaseProductVersion();
			String driverName = metaData.getDriverName();
			String driverVersion = metaData.getDriverVersion();
			String url = metaData.getURL();
			String username = metaData.getUserName();
			
			// Get table count
			ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
			int tableCount = 0;
			StringBuilder tableNames = new StringBuilder();
			while (tables.next()) {
				tableCount++;
				if (tableNames.length() > 0) {
					tableNames.append(", ");
				}
				tableNames.append(tables.getString("TABLE_NAME"));
			}
			
			response.put("status", "CONNECTED");
			response.put("database", databaseProductName);
			response.put("version", databaseProductVersion);
			response.put("driver", driverName + " " + driverVersion);
			response.put("url", url);
			response.put("username", username);
			response.put("tableCount", tableCount);
			response.put("tables", tableNames.toString());
			response.put("message", "Database connection successful!");
			
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			response.put("status", "FAILED");
			response.put("error", e.getMessage());
			response.put("message", "Database connection failed!");
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> health() {
		Map<String, Object> response = new HashMap<>();
		response.put("status", "UP");
		response.put("application", "Spring Banking System");
		response.put("timestamp", java.time.LocalDateTime.now());
		
		// Memory info
		Runtime runtime = Runtime.getRuntime();
		long totalMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		long usedMemory = totalMemory - freeMemory;
		
		Map<String, Object> memory = new HashMap<>();
		memory.put("total", formatBytes(totalMemory));
		memory.put("used", formatBytes(usedMemory));
		memory.put("free", formatBytes(freeMemory));
		memory.put("usagePercent", String.format("%.2f%%", (usedMemory * 100.0 / totalMemory)));
		response.put("memory", memory);
		
		return ResponseEntity.ok(response);
	}
	
	private String formatBytes(long bytes) {
		if (bytes < 1024) return bytes + " B";
		if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
		if (bytes < 1024 * 1024 * 1024) return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
		return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
	}
}

