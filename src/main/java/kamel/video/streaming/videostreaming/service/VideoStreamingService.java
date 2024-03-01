package kamel.video.streaming.videostreaming.service;

import kamel.video.streaming.videostreaming.model.VideoFile;
import kamel.video.streaming.videostreaming.data.VideoRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class VideoStreamingService {

    private final RestTemplate restTemplate;
    private final VideoRepository videoRepository;
    private final Environment environment;

    public VideoStreamingService(RestTemplate restTemplate, VideoRepository videoRepository, Environment environment) {
        this.restTemplate = restTemplate;
        this.videoRepository = videoRepository;
        this.environment = environment;
    }

    public boolean authenticate(Map<String, String> headers) {
        boolean contains = headers.containsKey("authentication");
        if (!contains)
            return false;
        return headers.get("authentication").equals("true");
    }

    public Iterable<VideoFile> getAllVideos() {
        return videoRepository.findAll();
    }

    public byte[] getVideo(String name) {
        Optional<VideoFile> video = videoRepository.findVideoFileByName(name);
        if (video.isEmpty())
            return new byte[0];
        String fileSystemDomain = environment.getProperty("file.system.domain");
        String fileSystemPort = environment.getProperty("file.system.port");
        byte[] bytes = restTemplate.getForObject("http://{domain}:{port}/file-system/{path}", byte[].class , fileSystemDomain, fileSystemPort, video.get().getName());
        if (bytes == null)
            return new byte[0];
        return bytes;
    }
}
