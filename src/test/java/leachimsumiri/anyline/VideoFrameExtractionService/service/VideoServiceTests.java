package leachimsumiri.anyline.VideoFrameExtractionService.service;

import leachimsumiri.anyline.VideoFrameExtractionService.Application;
import leachimsumiri.anyline.VideoFrameExtractionService.model.Image;
import leachimsumiri.anyline.VideoFrameExtractionService.model.Video;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.utils.Utils;
import org.jcodec.api.JCodecException;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import io.imagekit.sdk.config.Configuration;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VideoServiceTests {

    @Autowired
    VideoService videoService;

    @BeforeAll
    static void setup() throws IOException {
        ImageKit imageKit = ImageKit.getInstance();
        Configuration config = Utils.getSystemConfig(Application.class);
        imageKit.setConfig(config);
    }

    @Test
    public void testHandleFileUpload() throws JCodecException, IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("sample.mov");
        MultipartFile multipartFile = new MockMultipartFile(
                "sample.mov",
                "sample.mov",
                "application/form-data",
                Files.readAllBytes(Path.of(url.getPath()))
        );

        Pair<Video, List<Image>> result = videoService.handleFileUpload(multipartFile);

        assertNotNull(result);
        assertNotNull(result.getFirst().getId());
        assertTrue(result.getSecond().size() > 0);
    }
}
