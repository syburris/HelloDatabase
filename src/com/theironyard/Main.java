package com.theironyard;

import org.h2.engine.ConnectionInfo;
import org.h2.tools.Server;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
            // create webserver
        Server.createWebServer().start();
            //connect to sql driver
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS players (id IDENTITY, name VARCHAR, score INT, health DOUBLE, is_alive BOOLEAN)");
        stmt.execute("INSERT INTO players VALUES (NULL, 'Alice', 10, 100, true)");
        stmt.execute("UPDATE players SET health = 50 Where name = 'Alice'");
        stmt.execute("DELETE FROM players WHERE name = 'Alice'");

        String name = "Charlie";

        // BAD

        stmt.execute(String.format("INSERT INTO players Values (NULL, '%s', 10, 100, true)",name));

        // GOOD

        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO players Values (NULL, ?, 10, 100, true)");
        stmt2.setString(1, name);
        stmt2.execute();

        PreparedStatement stmt3 = conn.prepareStatement("SELECT * FROM players Where name = ?");
        stmt3.setString(1, name);
        ResultSet results = stmt3.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            int score = results.getInt("score");
            double health = results.getDouble("health");
            boolean isAlive = results.getBoolean("is_alive");
            System.out.printf("%s %s %s %s %s\n", name, id, score, health, isAlive);
        }
    }
}
