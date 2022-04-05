package leachimsumiri.anyline.VideoFrameExtractionService.controller;

import leachimsumiri.anyline.VideoFrameExtractionService.service.ImageService;
import leachimsumiri.anyline.VideoFrameExtractionService.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private VideoService videoService;

    @PostMapping("/images")
    public List<String> getImagesByVideo(@RequestParam("file") MultipartFile file) throws IOException {
        String videoId = videoService.getVideoId(file);

        return imageService.getImageUrlsByVideoId(videoId);
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException() {
        return "An error occurred at file reading. Please try another file.";
    }
}
