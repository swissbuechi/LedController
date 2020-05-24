package com.bbh.rgbMatrix.LedController;

import com.bbh.rgbMatrix.LedController.services.ArgumentValidator;
import com.beust.jcommander.Parameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// In dieser Klasse sind alle Command Line Argumente definiert

@Component
public class Arguments {

    @Parameter(names = {"-f", "--file"}, order = 0,
            description = "Path and name or internet URL of the source image File. Example: -f image.png")
    public String inputFile;

    @Parameter(names = {"-c", "--comport", "--serialport"}, order = 1,
            description = "Select a custom serial port")
    public String customPort;

    @Parameter(names = {"-s", "--save"}, order = 2,
            description = "Path and name to Save the transformed and scaled image. Example: --s output.png")
    public String saveFile;

    @Parameter(names = {"--reset"}, order = 3,
            description = "Resetting all LEDs")
    public boolean reset;

    @Parameter(names = "--scale", validateWith = ArgumentValidator.ListValue2.class,
            description = "Scaling the Image to a custom Resolution. Example: --scale 20,20")
    public List<Integer> scale = new ArrayList<>();

    @Parameter(names = {"--disable-autoscale"},
            description = "Disable the automatic scaling for too big Images")
    public Boolean disableAutoscale = false;

    @Parameter(names = {"--square"}, validateWith = {ArgumentValidator.PositiveInteger.class},
            description = "Transforming the Image to a square, requires the pixels of one Site. Example: --square 5")
    public int square;

    @Parameter(names = {"--transpose"},
            description = "Transpose the Image")
    public boolean transpose;

    @Parameter(names = {"--crop"}, validateWith = ArgumentValidator.ListValue4.class,
            description = "Crops a part out of the input image to be displayed, requires x-axis, y-axis, width, height. Example: --crop 2,2,10,10")
    public List<Integer> cropImage = new ArrayList<>();

    @Parameter(names = {"--rotate"}, validateWith = ArgumentValidator.RotateInRange.class,
            description = "Rotates the Image from 0-360 degree. Example: --rotate 90")
    public Double rotate;

    @Parameter(names = {"-b", "--brightness"}, validateWith = ArgumentValidator.BrighnessInRange.class,
            description = "Setting a custom brightness from range 0.0 to 1.0. Example: -b 0.2")
    public double brightness = 0.1d;

    @Parameter(names = {"--matrix"}, validateWith = ArgumentValidator.ListValue2.class,
            description = "Setting a custom size for the Matrix")
    public List<Integer> matrix = Arrays.asList(16, 16);

    @Parameter(names = {"-h", "--help"},
            description = "Help / Usage",
            help = true)
    public boolean help;
}