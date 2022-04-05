package leachimsumiri.anyline.VideoFrameExtractionService.service;

import leachimsumiri.anyline.VideoFrameExtractionService.utils.FileUtils;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileUtilsTests {

    @Test
    public void testPictureToByteArray() throws IOException {
        Picture picture = Picture.create(16, 16, ColorSpace.RGB);
        byte[] bytes = FileUtils.pictureToByteArray(picture);

        assertNotNull(bytes);
        assert(bytes.length > 100);
    }

    @Test
    public void testHashGenerationIsSteady() throws NoSuchAlgorithmException, IOException {
        byte[] randomBytes = new byte[20];
        SecureRandom.getInstanceStrong().nextBytes(randomBytes);

        String firstIterationString = FileUtils.generateHashFromByteArray(randomBytes);
        String secondIterationString = FileUtils.generateHashFromByteArray(randomBytes);

        assertEquals(firstIterationString, secondIterationString);
    }
}
