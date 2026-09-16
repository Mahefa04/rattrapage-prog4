package hei.rattrapage.prog.file.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

class ImageResizerTest {

    private final ImageResizer imageResizer = new ImageResizer();

    @Test
    void shouldResizeImageTo256x256() throws Exception {
        BufferedImage original = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        ByteArrayOutputStream input = new ByteArrayOutputStream();
        ImageIO.write(original, "png", input);

        byte[] result = imageResizer.resize(input.toByteArray());

        assertNotNull(result);

        BufferedImage thumbnail = ImageIO.read(new java.io.ByteArrayInputStream(result));

        assertNotNull(thumbnail);
        assertEquals(256, thumbnail.getWidth());
        assertEquals(256, thumbnail.getHeight());
    }

    @Test
    void shouldRejectInvalidImage() {
        byte[] invalidImage = "not an image".getBytes();

        org.junit.jupiter.api.Assertions.assertThrows(
                java.io.IOException.class,
                () -> imageResizer.resize(invalidImage));
    }
}