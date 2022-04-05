package leachimsumiri.anyline.VideoFrameExtractionService.repository;

import leachimsumiri.anyline.VideoFrameExtractionService.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Video findById(Long id);
}
