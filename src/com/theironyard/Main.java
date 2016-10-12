package com.theironyard;

import org.h2.tools.Server;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Server.createWebServer().start();

    }
}
