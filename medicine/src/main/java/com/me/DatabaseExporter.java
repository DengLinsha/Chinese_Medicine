package com.me;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class DatabaseExporter implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws IOException {
        String exportFilePath = "medicine.sql";
        String exportSql = "SELECT * INTO OUTFILE '" + exportFilePath + "' FROM your_table_name";

        jdbcTemplate.execute(exportSql);

        System.out.println("Successfully exported database to " + exportFilePath);
    }
}
