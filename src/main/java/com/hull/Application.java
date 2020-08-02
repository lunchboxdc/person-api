package com.hull;

import com.hull.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ResourceLoader resourceLoader;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String...args) {
        PersonService personService = applicationContext.getBean(PersonService.class);

        try {
            Resource[] files = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("classpath*:*.csv");
            for (Resource file : files) {
                personService.loadCsv(file.getURI().getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
