package hei.rattrapage.prog.file.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class ImageResizer {

    private static final int THUMBNAIL_SIZE = 256;

    public byte[] resize(byte[] imageBytes) throws IOException {
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));

        if (original == null) {
            throw new IOException("Unable to read image");
        }

        BufferedImage thumbnail =
                new BufferedImage(
                        THUMBNAIL_SIZE,
                        THUMBNAIL_SIZE,
                        BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = thumbnail.createGraphics();

        try {
            Image scaled =
                    original.getScaledInstance(
                            THUMBNAIL_SIZE,
                            THUMBNAIL_SIZE,
                            Image.SCALE_SMOOTH);

            graphics.drawImage(scaled, 0, 0, null);
        } finally {
            graphics.dispose();
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ImageIO.write(thumbnail, "jpg", output);

        return output.toByteArray();
    }
}