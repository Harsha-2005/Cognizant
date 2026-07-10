package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Main Application Class for Spring Learn.
 */
@SpringBootApplication
public class SpringLearnApplication {

    // Hands on 3: Include logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringLearnApplication.class, args);
        
        displayDate();
        displayCountry();
        displayCountries();
    }

    /**
     * Hands on 2: Load SimpleDateFormat from Spring Configuration XML
     */
    public static void displayDate() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
        try {
            Date date = format.parse("31/12/2018");
            LOGGER.debug("Date parsed from string '31/12/2018' is: {}", date);
        } catch (ParseException e) {
            LOGGER.error("Date parsing failed", e);
        }
        LOGGER.info("END");
    }

    /**
     * Hands on 4 & 5: Load Country from Spring Configuration XML & Scopes
     */
    public static void displayCountry() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        
        // Hands on 4
        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country : {}", country.toString());

        // Hands on 5: Prototype scope demonstration
        // Getting country bean reference one more time from the same context
        Country anotherCountry = context.getBean("country", Country.class);
        LOGGER.debug("Another Country (Prototype Scope Check) : {}", anotherCountry.toString());

        LOGGER.info("END");
    }

    /**
     * Hands on 6: Load list of countries from Spring Configuration XML
     */
    public static void displayCountries() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        
        // The cast to ArrayList helps to suppress warnings or it can be generic List
        @SuppressWarnings("unchecked")
        ArrayList<Country> countries = context.getBean("countryList", ArrayList.class);
        
        LOGGER.debug("List of Countries: {}", countries);
        
        LOGGER.info("END");
    }
}
