package leachimsumiri.anyline.VideoFrameExtractionService.controller;

import leachimsumiri.anyline.VideoFrameExtractionService.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@ControllerAdvice
public class VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    @Value("${spring.servlet.multipart.max-file-size}")
    private String multipartMaxFileSize;

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public void uploadVideo(@RequestParam("file") MultipartFile file) {

    }
}
