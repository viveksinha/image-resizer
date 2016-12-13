package com.vivekwiki.transformers.handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

/**
 * @author Vivek Wiki
 */
public class ImageResizeHandler {
    private static final float QUALITY = 0.85f;

    public static boolean resizeImage(InputStream inputImageStream,
                                      String outputImagePath, int scaledWidth, int scaledHeight,
                                      String outputFormat, boolean maintainAspectRatio)
            throws IOException {

        BufferedImage image = ImageIO.read(inputImageStream);
        BufferedImage scaled = getScaledInstance(
                image, scaledWidth, scaledHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true,
                maintainAspectRatio);
        System.out.println("path used: " + outputImagePath);
        return writeImage(scaled, new FileOutputStream(outputImagePath), QUALITY, outputFormat);
    }


    private static BufferedImage getScaledInstance(BufferedImage originalImage, int targetWidth,
                                                   int targetHeight, Object hint,
                                                   boolean higherQuality, boolean maintainAspectRatio) {
        int type = (originalImage.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        BufferedImage image = originalImage;

        /**
         * higherQuality ?
         * Use multi-step technique: start with original size, then scale down in multiple
         * passes with drawImage() until the target size is reached
         *
         * !higherQuality ?
         * Use one-step technique: scale directly from original size to target size with
         * a single drawImage() call
         *
         */
        int scaledHeight = higherQuality ? originalImage.getHeight() : targetHeight;
        int scaledWidth = higherQuality ? originalImage.getWidth() : targetWidth;
        do {
            if (higherQuality && scaledWidth > targetWidth) {
                scaledWidth /= 2;
                if (scaledWidth < targetWidth) {
                    scaledWidth = targetWidth;
                }
            }
            if (higherQuality && scaledHeight > targetHeight) {
                scaledHeight /= 2;
                if (scaledHeight < targetHeight) {
                    scaledHeight = targetHeight;
                }
            }
            BufferedImage tempImage = new BufferedImage(scaledWidth, scaledHeight, type);
            Graphics2D graphics2D = tempImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            graphics2D.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
            graphics2D.dispose();
            image = tempImage;
        }
        while (!maintainAspectRatio && (scaledWidth != targetWidth || scaledHeight != targetHeight));
        return image;
    }

    private static boolean writeImage(BufferedImage bufferedImage, OutputStream outputStream,
                                      float quality, String outputFormat) throws IOException {

        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(outputFormat);
        if (iterator.hasNext()) {
            ImageWriter imageWriter = iterator.next();
            ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);

            try {
                ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
                imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                imageWriteParam.setCompressionQuality(quality);

                imageWriter.setOutput(imageOutputStream);
                IIOImage iioimage = new IIOImage(bufferedImage, null, null);
                imageWriter.write(null, iioimage, imageWriteParam);
                return true;
            } finally {
                imageOutputStream.flush();
            }
        }
        return false;
    }
}
