package kamel.video.streaming.videostreaming.controller;

import jakarta.servlet.http.HttpSession;
import kamel.video.streaming.videostreaming.service.VideoStreamingService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/videos")
public class VideoStreamingRestController {

    private final VideoStreamingService service;

    public VideoStreamingRestController(VideoStreamingService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Resource> getVideo(@PathVariable String name, @RequestHeader HttpHeaders headers, HttpSession session) throws AccessDeniedException {
        if (!session.getAttribute("authentication").equals("true"))
            throw new AccessDeniedException("Access denied");
        byte[] video = service.getVideo(name);
        Resource videoResource = new ByteArrayResource(video);
        if (videoResource.exists() && videoResource.isReadable()) {
            headers.setContentType(MediaType.valueOf("video/mp4"));
            return new ResponseEntity<>(videoResource, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
