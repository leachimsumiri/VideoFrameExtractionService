package leachimsumiri.anyline.VideoFrameExtractionService.controller;

import leachimsumiri.anyline.VideoFrameExtractionService.model.Image;
import leachimsumiri.anyline.VideoFrameExtractionService.model.Video;
import leachimsumiri.anyline.VideoFrameExtractionService.service.VideoService;
import org.jcodec.api.JCodecException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@ControllerAdvice
public class VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    @Value("${spring.servlet.multipart.max-file-size}")
    private String multipartMaxFileSize;

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public List<Image> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException, JCodecException {
        Pair<Video, List<Image>> fileUploadResult = videoService.handleFileUpload(file);

        videoService.persistVideo(fileUploadResult.getFirst());
        videoService.persistImages(fileUploadResult.getSecond());

        return fileUploadResult.getSecond();
    }
}
