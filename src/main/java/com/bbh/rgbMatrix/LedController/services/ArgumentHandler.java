package com.bbh.rgbMatrix.LedController.services;

import com.bbh.rgbMatrix.LedController.Arguments;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
public class ArgumentHandler {
    public static final Logger LOGGER = LogManager.getLogger(ArgumentHandler.class);
    final static Arguments ARGS = new Arguments();

    /**
     * Parst die übergebenen Eingabeargumente
     * <p>
     *
     * @param args Die Eingabeargumente
     * @return Die geparsten Eingabeargumente
     */
    public Arguments handleInputArgs(String[] args) {
        //Ein jCommander wird erstellt
        JCommander jCommander = new JCommander(ARGS);
        // Der Name vom Programm wird definiert
        jCommander.setProgramName("LedController");

        try {
            // Die Argumente werden geparst
            jCommander.parse(args);
            LOGGER.info("Arguments parsed");
        } catch (ParameterException e) {
            LOGGER.error("Parameter Exeption catched");
            showUsage(jCommander);
        }
        // Wenn das Argument --help eingegeben wurde, wird die showUsage Methode aufgerufen
        if (ARGS.help) {
            showUsage(jCommander);
        }
        return ARGS;
    }

    /**
     * Zeigt die Hilfe an
     * <p>
     *
     * @param jCommander Der jCommander, von welchem die Hilfe angezeigt werden soll
     */
    public void showUsage(JCommander jCommander) {
        jCommander.usage();
        System.exit(0);
    }

    public Dimension getDimensions(List<Integer> scale) {
        return new Dimension(scale.get(0), scale.get(1));
    }

    public int getWidth(List<Integer> hight) {
        return hight.get(0);
    }

    public int getHeight(List<Integer> width) {
        return width.get(1);
    }
}