package com.hull;

import com.hull.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(Application.class);

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
                logger.info("Loaded {}", file.getURI().getPath());
            }
        } catch (IOException e) {
            logger.error("Error loading csv files from resources directory", e);
        }

        if (args.length > 0) {
            try {
                for (String arg : args) {
                    if (arg.contains("csv.dir")) {
                        String directoryPath = arg.split("=")[1];
                        File csvDir = new File(directoryPath);
                        if (csvDir.isDirectory()) {
                            String[] csvFiles = csvDir.list((dir, name) -> name.endsWith(".csv"));
                            if (csvFiles != null) {
                                for (String csvFile : csvFiles) {
                                    String fullPath = directoryPath + "/" + csvFile;
                                    personService.loadCsv(fullPath);
                                    logger.info("Loaded {}", fullPath);
                                }
                            }
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("Error loading csv files from csv.dir argument", e);
            }
        }
    }
}
