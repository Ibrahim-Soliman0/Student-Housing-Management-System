package org.studenthousingsystem;

import com.google.zxing.LuminanceSource;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class BufferedImageLuminanceSource extends LuminanceSource {

    private final BufferedImage image;
    private final byte[] luminances;

    public BufferedImageLuminanceSource(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.image = image;

        // Extract luminance data from the BufferedImage
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            this.luminances = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        } else {
            int width = image.getWidth();
            int height = image.getHeight();
            int[] rgbData = new int[width * height];
            image.getRGB(0, 0, width, height, rgbData, 0, width);
            this.luminances = new byte[width * height];
            for (int i = 0; i < rgbData.length; i++) {
                int pixel = rgbData[i];
                int luminance = (306 * ((pixel >> 16) & 0xFF) +
                        601 * ((pixel >> 8) & 0xFF) +
                        117 * (pixel & 0xFF) + 512) >> 10;
                this.luminances[i] = (byte) luminance;
            }
        }
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        System.arraycopy(luminances, y * width, row, 0, width);
        return row;
    }

    @Override
    public byte[] getMatrix() {
        return luminances;
    }
}
