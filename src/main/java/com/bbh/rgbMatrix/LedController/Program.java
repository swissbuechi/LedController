package com.bbh.rgbMatrix.LedController;

import com.bbh.rgbMatrix.LedController.gateways.ComPort;
import com.bbh.rgbMatrix.LedController.services.*;
import com.bbh.rgbMatrix.LedController.utils.LedActions;
import com.bbh.rgbMatrix.LedController.utils.SampleImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;


@Component
public class Program {

    @Autowired
    private ImageInput imageInput = new ImageInput();

    @Autowired
    private ComPort comPort = new ComPort();

    @Autowired
    private ImageToRGB imageToRGB = new ImageToRGB();

    @Autowired
    private LedActions ledActions = new LedActions();

    @Autowired
    private ImageTransformation imageTransformation = new ImageTransformation();

    @Autowired
    private SaveImage saveImage = new SaveImage();

    @Autowired
    private SampleImages sampleImages = new SampleImages();

    /**
     * In dieser Methode befindet sich der gsesamte logische Ablauf vom LedController
     * <p>
     *
     * @param args Die Eingabeargumente
     */
    public void main(String[] args) throws IOException {
        ArgumentHandler argumentHandler = new ArgumentHandler();
        Arguments arguments = argumentHandler.handleInputArgs(args);
        OutputStream outputStream;
        // Es wird überprüft ob ein bestimmter serieller Port angegeben wurde
        if (arguments.customPort != null) {
            // Der Outputstream für den angegeben Port wird erstellt
            outputStream = comPort.open(arguments.customPort);
        } else {
            // Der Outputstream für den ersten verfügbaren Port wird erstellt
            outputStream = comPort.open();
        }
        // Es wird überprüft ob die LEDs zurückgesetzt werden sollten
        if (arguments.reset) {
            comPort.write(outputStream, ledActions.DisableAllLeds());
        } else {
            // Es wird überprüft ob kein Bild zur darstellung angegeben wurde
            if (arguments.inputFile == null) {
                System.err.println("No input image file provided: Displaying sample image");
                //Schreibt das Sample Image auf den seriellen Port
                comPort.write(outputStream, sampleImages.sampleImage());

                //Fehlermeldungen
                if (arguments.cropImage.size() != 0) {
                    System.err.println("Please provide an input image to use --crop");
                }
                if (arguments.scale.size() != 0) {
                    System.err.println("Please provide an input image to use --scale");
                }
                if (arguments.square > 0) {
                    System.err.println("Please provide an input image to use --square");
                }
                if (arguments.transpose) {
                    System.err.println("Please provide an input image to use --transpose");
                }
                if (arguments.rotate != null) {
                    System.err.println("Please provide an input image to use --rotate");
                }
                if (arguments.saveFile != null) {
                    System.err.println("Please provide an input image to use --save");
                }
            } else {
                // Das Bild wird eingelesen
                BufferedImage image = imageInput.getImage(arguments.inputFile);
                int panelWidth = argumentHandler.getWidth(arguments.matrix);
                int panelHeight = argumentHandler.getHeight(arguments.matrix);
                // Das Bild wird zugeschnitten
                if (arguments.cropImage.size() != 0) {
                    System.out.println("Image cropped to x-axis, y-axis, width, height: " + arguments.cropImage);
                    image = imageTransformation.getSubimage(image, arguments.cropImage);
                }
                // Das Bild wird skalliert
                if (arguments.scale.size() != 0) {
                    System.out.println("Image scaled to " + arguments.scale);
                    image = imageTransformation.getScaledImage(image, argumentHandler.getDimensions(arguments.scale));
                }
                // Das Bild wird quadratisch eingepasst
                if (arguments.square > 0) {
                    image = imageTransformation.squareFit(image, arguments.square);
                    System.out.println("Image scaled to " + arguments.square + "x" + arguments.square + " square");
                }
                // Das Bild wird transponiert
                if (arguments.transpose) {
                    image = imageTransformation.transpose(image);
                    System.out.println("Image transposed");
                }
                // Das Bild wird gedreht
                if (arguments.rotate != null) {
                    image = imageTransformation.rotate(image, arguments.rotate);
                    System.out.println("Image rotated by " + arguments.rotate + " degree");
                }
                // Das Bild wird gespeichert
                if (arguments.saveFile != null) {
                    saveImage.save(image, arguments.saveFile);
                }
                if (outputStream != null) {
                    // Wenn das Auto-Scaling deaktiviert werden soll
                    if (arguments.disableAutoscale) {
                        System.out.println("Autoscaling disabled");
                    }
                    // Autoscaling aktiv
                    if (image.getWidth() > panelWidth || image.getHeight() > panelHeight && !arguments.disableAutoscale) {
                        image = imageTransformation.getScaledImage(image, argumentHandler.getDimensions(arguments.matrix));
                        System.err.println("Image is to big to be displayed on the " + panelWidth + "x" + panelHeight + " matrix");
                        System.err.println("Autoscaling image down to: " + panelWidth + "x" + panelHeight);
                    }
                    // Die Grösse der Matrix
                    if (panelHeight != 16 && panelWidth != 16) {
                        System.out.println("LED matrix size set to " + arguments.matrix);
                    }
                    // Die Helligkeit der LEDs
                    double brightness = 0.1d;
                    if (arguments.brightness > 0.0d && arguments.brightness < 1.0d) {
                        brightness = arguments.brightness;
                    }
                    String grbString = imageToRGB.imageToString(image, arguments.brightness, panelWidth, panelHeight);
                    if (brightness != 0.1) {
                        System.out.println("LED brightness level " + arguments.brightness);
                    }
                    // Das Bild wird auf den seriellen Port geschrieben
                    System.out.println("Writing image to serial port");
                    comPort.write(outputStream, grbString.getBytes());
                    comPort.close(outputStream);
                }
            }
        }
    }
}
