package co.drytools.util;

import java.awt.image.BufferedImage;

public class ImageDifference {

    private final double pixelError;
    private final BufferedImage image;

    public ImageDifference(double pixelError, BufferedImage image) {
        this.pixelError = pixelError;
        this.image = image;
    }

    public double getPixelError() {
        return pixelError;
    }

    public BufferedImage getImage() {
        return image;
    }
}
