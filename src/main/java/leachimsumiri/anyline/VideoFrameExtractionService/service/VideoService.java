package leachimsumiri.anyline.VideoFrameExtractionService.service;

import leachimsumiri.anyline.VideoFrameExtractionService.model.Image;
import leachimsumiri.anyline.VideoFrameExtractionService.model.Video;
import leachimsumiri.anyline.VideoFrameExtractionService.repository.ImageRepository;
import leachimsumiri.anyline.VideoFrameExtractionService.repository.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoService.class);

    private static final int FRAME_CAP = 60;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ImageRepository imageRepository;

    public void persistVideo(Video video) {
        videoRepository.save(video);
    }

    public void persistImages(List<Image> images) {
        imageRepository.saveAll(images);
    }
}
