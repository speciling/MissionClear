package client.db;

import org.json.simple.JSONObject;
import server.db.DBManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class ClientDBManager extends DBManager {

    public static void init() {
        try {
            path = Path.of("./missioncleardata/client/pictures");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn = DriverManager.getConnection("jdbc:sqlite:missioncleardata/client/client.db");

            createTable("USER", """
                    CREATE TABLE IF NOT EXISTS USER 
                    (uid integer not null, nickname string not null, pfp string, groups string default '')""");
            executeSQL("INSERT INTO USER VALUES (-1, '', '', '')");

            createTable("GROUPS", """
                    CREATE TABLE IF NOT EXISTS GROUPS (gid integer unique, 
                    title string not null, description string not null, mission string not null, 
                    capacity integer not null, category integer not null, usercnt integer default 1, 
                    deadline string not null, startDate string not null, endDate string not null, 
                    password string, users string not null)""");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveInitData(JSONObject data) {}

}
