package ru.savadevel.wthl;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SpringMain {
    public static void main(String[] args) {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            //on classpath
        } catch (ClassNotFoundException e) {
            //not on classpath
            e.printStackTrace();
        }

        try (GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext()) {
            applicationContext.load("spring/spring-db.xml");
            applicationContext.refresh();

            System.out.println("Bean definition names:\n" + Arrays.stream(applicationContext.getBeanDefinitionNames())
                    .sorted()
                    .collect(Collectors.joining("\n")));
        }
    }
}
