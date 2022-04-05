package leachimsumiri.anyline.VideoFrameExtractionService.service;

import leachimsumiri.anyline.VideoFrameExtractionService.model.Image;
import leachimsumiri.anyline.VideoFrameExtractionService.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> findByVideoId(String id) {
        return imageRepository.findAllByVideo_Id(id);
    }

    public List<String> getImageUrlsByVideoId(String id) {
        List<Image> images = findByVideoId(id);

        return images.stream().map(Image::getUrl).collect(Collectors.toList());
    }
}
