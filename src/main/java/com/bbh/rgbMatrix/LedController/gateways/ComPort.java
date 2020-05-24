package com.bbh.rgbMatrix.LedController.gateways;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class ComPort {

    public static final int BITRATE = 921600;
    private static final Logger LOGGER = LogManager.getLogger(ComPort.class);
    // Serial no parity mode
    private static final int NO_PARITY = 0;

    // Serial timeout not blocking
    private static final int TIMEOUT_NONBLOCKING = 0;

    private SerialPort customPort;

    /**
     * Öffnet einen Outputstream über einen bestimmten Seriellen Port
     * <p>
     *
     * @param CustomComPort Der Serielle Port
     * @return Den Outputstream über den Seriellen Port
     */
    public OutputStream open(String CustomComPort) {
        SerialPort customPort = SerialPort.getCommPort(CustomComPort);
        // Erstellt ein Array mit allen verfügbaren seriellen Ports
        SerialPort[] allAvailableComPorts = SerialPort.getCommPorts();
        boolean portAvailable = false;
        for (SerialPort allAvailableComPort : allAvailableComPorts) {
            // Überprüft ob der Serielle Port verfügbar ist
            if (allAvailableComPort.getSystemPortName().equals(customPort.getSystemPortName())) {
                portAvailable = true;
                break;
            }
        }

        if (portAvailable) {
            //
            customPort.setComPortParameters(BITRATE, 8, 1, NO_PARITY);
            // Setzt die Timeouts vom Seriellen Port
            customPort.setComPortTimeouts(TIMEOUT_NONBLOCKING, 0, 0);
            // Öffnet den Seriellen Port
            customPort.openPort();
            System.out.println("Opened the Port " + customPort.getSystemPortName());
            LOGGER.info("Opened the Port " + customPort.getSystemPortName());
            // Erstelllt einen Outputstream
            return customPort.getOutputStream();
        } else {
            System.err.println("The custom serial port " + CustomComPort + " is not available");
            LOGGER.error("The custom serial port " + CustomComPort + " is not available");
            return null;
        }
    }

    /**
     * Öffnet einen Outputstream über den ersten verfügbaren Seriellen Port am System
     * <p>
     *
     * @return Den Outputstream über den Seriellen Port
     */
    public OutputStream open() {
        SerialPort firstAvailableComPort;
        // Erstellt ein Array mit allen verfügbaren seriellen Ports
        SerialPort[] allAvailableComPorts = SerialPort.getCommPorts();
        if (allAvailableComPorts.length == 0) {
            System.err.println("No serial port found");
            LOGGER.error("No serial port found");
            // Das Array wird ausgegeben
        } else {
            System.out.println("Number of all available serial ports: " + allAvailableComPorts.length);
            for (SerialPort eachComPort : allAvailableComPorts) {
                System.out.println("List of all available serial ports: " + eachComPort.getSystemPortName());
            }
            // Der erste verfügbare Port wird ausgewählt
            firstAvailableComPort = allAvailableComPorts[0];
            // Setzt die Parameter vom Seriellen Port
            firstAvailableComPort.setComPortParameters(921600, 8, 1, NO_PARITY);
            // Setzt die Timeouts vom Seriellen Port
            firstAvailableComPort.setComPortTimeouts(TIMEOUT_NONBLOCKING, 0, 0);
            // Öffnet den Seriellen Port
            firstAvailableComPort.openPort();
            System.out.println("Opened the first available serial port: " + firstAvailableComPort.getSystemPortName());
            LOGGER.info("Opened the first available serial port: " + firstAvailableComPort.getSystemPortName());
            // Erstelllt einen Outputstream
            return firstAvailableComPort.getOutputStream();
        }
        return null;
    }

    /**
     * Schreibt ein ByteArray auf einen Outputstream
     * <p>
     *
     * @param outputStream Den Outputstream, auf welchen geschriebenw erden seoll
     * @param bytestream   das ByteArray, welches auf den Outputstream geschriben werden soll
     */
    public void write(OutputStream outputStream, byte[] bytestream) throws IOException {
        outputStream.write(bytestream);
        LOGGER.info("Writing bytestram to outputstream " + outputStream);
    }

    /**
     * Schiesst den Outputstream.
     * <p>
     *
     * @param outputStream Der Outputstream, welcher geschlossen werden soll
     */
    public void close(OutputStream outputStream) throws IOException {
        outputStream.close();
        LOGGER.info("Closed OutputStream " + outputStream);
    }
}

