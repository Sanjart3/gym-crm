package org.example;

import org.example.config.MyAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws SQLException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyAppConfig.class);
    }
}
