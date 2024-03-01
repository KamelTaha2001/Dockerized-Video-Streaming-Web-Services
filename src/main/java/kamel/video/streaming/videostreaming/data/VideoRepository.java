package kamel.video.streaming.videostreaming.data;

import kamel.video.streaming.videostreaming.model.VideoFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends CrudRepository<VideoFile, Long> {
    Optional<VideoFile> findVideoFileByName(String name);
}
