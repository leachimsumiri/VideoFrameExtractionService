package leachimsumiri.anyline.VideoFrameExtractionService.controller;

import leachimsumiri.anyline.VideoFrameExtractionService.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/images")
    public void getImagesByVideo(@RequestParam("file") MultipartFile file) throws IOException {
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException() {
        return "An error occurred at file reading. Please try another file.";
    }
}
