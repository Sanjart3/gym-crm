package org.example;

import org.example.config.MyAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyAppConfig.class);
        Facade facade = context.getBean(Facade.class);
        facade.getTraineeByUsername("bob.johnson");
    }
}
