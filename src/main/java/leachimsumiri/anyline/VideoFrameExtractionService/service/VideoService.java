package leachimsumiri.anyline.VideoFrameExtractionService.service;

import com.google.gson.Gson;
import io.imagekit.sdk.ImageKit;
import leachimsumiri.anyline.VideoFrameExtractionService.model.Image;
import leachimsumiri.anyline.VideoFrameExtractionService.model.ImageKitResponse;
import leachimsumiri.anyline.VideoFrameExtractionService.model.Video;
import leachimsumiri.anyline.VideoFrameExtractionService.repository.ImageRepository;
import leachimsumiri.anyline.VideoFrameExtractionService.repository.VideoRepository;
import leachimsumiri.anyline.VideoFrameExtractionService.utils.FileUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public String getVideoId(MultipartFile file) throws IOException {
        return FileUtils.generateHashFromByteArray(file.getBytes());
    }

    public Pair<Video, List<Image>> handleFileUpload(MultipartFile file) throws IOException, JCodecException {
        String videoId = getVideoId(file);
        Video video = new Video(videoId);
        LOGGER.debug("created Video with id {}", videoId);

        List<Image> images = captureImages(file, video);

        return Pair.of(video, images);
    }

    private List<Image> captureImages(MultipartFile file, Video video) throws IOException, JCodecException {
        Picture frame;
        List<Image> images = new ArrayList<>();

        int frameLength = FileUtils.getFrameLength(file);

        for (int i = 1; i < frameLength; i++) {
            if (i % FRAME_CAP == 0) {
                try {
                    frame = FrameGrab.getFrameFromChannel(NIOUtils.readableChannel(FileUtils.convertMultiPartToFile(file)), i);
                } catch (JCodecException | IOException e) {
                    LOGGER.error("exception at frame grabbing:");
                    e.printStackTrace();
                    throw e;
                }

                byte[] imageAsBytes = FileUtils.pictureToByteArray(frame);
                FileCreateRequest fileCreateRequest = new FileCreateRequest(imageAsBytes, file.getName());

                Result result = ImageKit.getInstance().upload(fileCreateRequest);
                LOGGER.info("response at frame {}: {}", i, result);

                ImageKitResponse imageKitResponse = new Gson().fromJson(result.getRaw(), ImageKitResponse.class);
                images.add(Image.builder()
                        .url(imageKitResponse.getUrl())
                        .video(video)
                        .build());
            }
        }

        FileUtils.fileCleanup(file);

        return images;
    }
}
