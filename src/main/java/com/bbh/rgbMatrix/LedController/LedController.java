package com.bbh.rgbMatrix.LedController;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Die Spring Boot Applikation wird gestartet
// Der CommandLineRunner ist die Basis der Applikation
@SpringBootApplication
public class LedController implements CommandLineRunner {
    /**
     * Startet die Spring Boot Applikation
     * <p>
     *
     * @param args Die eingegebenen Konsolenargumente
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LedController.class);
        app.run(args);
    }

    /**
     * Startet das LedController Programm (Logischer ablauf)
     * <p>
     *
     * @param args Die eingegebenen Konsolenargumente
     */
    @Override
    public void run(String... args) throws Exception {
        Program run = new Program();
        run.main(args);
    }
}