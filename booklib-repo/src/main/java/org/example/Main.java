package org.example;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
        System.out.println(
                "SELECT * FROM AUTHOR; SELECT * FROM GENRE; SELECT * FROM BOOK; SELECT * FROM IMAGE; SELECT * FROM COMMENT;"
        );
        Console.main(args);
    }
}