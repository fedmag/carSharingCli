package com.fedmag.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbClient {

  private final static String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
  private final static String JDBC_DRIVER = "org.h2.Driver";

  public DbClient() {
    try {
      Class.forName(JDBC_DRIVER);
    } catch (ClassNotFoundException e) {
      System.out.println("ERROR: Unable to obtain driver class.");
      throw new RuntimeException(e);
    }
  }

  public void run(String str) {
    System.out.println("Run: " + str);
    try (
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement statement = conn.createStatement()
    ) {
      statement.executeUpdate(str);
    } catch (SQLException e) {
      System.out.println("ERROR: unable to create the DB from String: " + str);
      e.printStackTrace();
    }
  }

  public List<Object[]> runForResult(String query) {
    List<Object[]> results = new ArrayList<>();
    try (
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query)
    ) {
      int columnCount = rs.getMetaData().getColumnCount();
      while (rs.next()) {
        Object[] row = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
          row[i] = rs.getObject(i + 1);
        }
        results.add(row);
      }
    } catch (SQLException e) {
      System.out.println("ERROR: unable to retrieve the ResultSet.");
      e.printStackTrace();
    }
    return results;
  }

  public List<Object[]> runForResultWithParams(String query, List<Object> params)
      throws SQLException {
    List<Object[]> results = new ArrayList<>();
    try (
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement statement = conn.prepareStatement(query)
    ) {
      for (int i = 0; i < params.size(); i++) {
        statement.setObject(i + 1, params.get(i));
      }
      try (ResultSet rs = statement.executeQuery()) {
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
          Object[] row = new Object[columnCount];
          for (int i = 0; i < columnCount; i++) {
            row[i] = rs.getObject(i + 1);
          }
          results.add(row);
        }
      }
    }
    return results;
  }
}