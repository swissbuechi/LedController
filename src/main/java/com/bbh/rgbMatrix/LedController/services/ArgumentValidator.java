package com.bbh.rgbMatrix.LedController.services;

import com.beust.jcommander.IParameterValidator;

import java.util.Arrays;
import java.util.List;

/**
 * Validiert die Eingabeargumente
 */
public class ArgumentValidator {
    public static class PositiveInteger implements IParameterValidator {
        /**
         * Der Integert Wert muss positiv sein
         * <p>
         *
         * @param name  Der Name vom Argument
         * @param value Der Wert vom Argument
         */
        public void validate(String name, String value) {
            int n = Integer.parseInt(value);
            if (n < 0) {
                System.err.println("Parameter " + name + " should be positive (found " + value + ")");
                System.err.println("Example: --square 5");
                System.exit(1);
            }
        }
    }

    public static class RotateInRange implements IParameterValidator {
        /**
         * Der Doubble Wert muss zwischen + und - 360.0 sein
         * <p>
         *
         * @param name  Der Name vom Argument
         * @param value Der Wert vom Argument
         */
        public void validate(String name, String value) {
            double n = Double.parseDouble(value);
            if (n > 360.0d || n < -360.0d) {
                System.err.println("Parameter " + name + " only accepts values from -360.0 - 360.0 (found " + value + ")");
                System.err.println("Example:" + name + " 2,2,10,10");
                System.exit(1);
            }
        }
    }

    public static class ListValue2 implements IParameterValidator {
        /**
         * Die länge einer Liste muss 2 sein
         * <p>
         *
         * @param name  Der Name vom Argument
         * @param value Der Wert vom Argument
         */
        public void validate(String name, String value) {
            String[] str = value.split(",");
            List<String> n = Arrays.asList(str);
            if (n.size() != 2) {
                System.err.println("Parameter " + name + " requires two arguments (found " + value + ")");
                System.err.println("Example: " + name + " 20,20");
                System.exit(1);
            }
        }
    }

    public static class ListValue4 implements IParameterValidator {
        /**
         * Die länge einer Liste muss 4 sein
         * <p>
         *
         * @param name  Der Name vom Argument
         * @param value Der Wert vom Argument
         */
        public void validate(String name, String value) {
            String[] str = value.split(",");
            List<String> n = Arrays.asList(str);
            if (n.size() != 4) {
                System.err.println("Parameter " + name + " requires four arguments (found " + value + ")");
                System.err.println("Requires: x-axis, y-axis, width, height");
                System.err.println("Example: " + name + " 2,2,10,10");
                System.exit(1);
            }
        }
    }

    public static class BrighnessInRange implements IParameterValidator {
        /**
         * Der Wert vom Doubble muss zwischen 0.0 und 1.0 liegen
         * <p>
         *
         * @param name  Der Name vom Argument
         * @param value Der Wert vom Argument
         */
        public void validate(String name, String value) {
            double n = Double.parseDouble(value);
            if (n > 1.0d || n < 0.0d) {
                System.err.println("Parameter " + name + " only accepts values from 0.1 - 1.0 (found " + value + ")");
                System.err.println("Example: " + name + " 0.2");
                System.exit(1);
            }
        }
    }
}