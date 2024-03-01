package kamel.video.streaming.videostreaming.controller;

import jakarta.servlet.http.HttpSession;
import kamel.video.streaming.videostreaming.model.VideoFile;
import kamel.video.streaming.videostreaming.service.VideoStreamingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/videos")
public class VideoStreamingController {

    private final VideoStreamingService service;

    public VideoStreamingController(VideoStreamingService service) {
        this.service = service;
    }

    @GetMapping()
    public String getAllVideos(Model model, HttpSession session) throws AccessDeniedException {
        System.out.println("Videos page");
        if (!session.getAttribute("authentication").equals("true"))
            throw new AccessDeniedException("Access denied");
        Iterable<VideoFile> videos = service.getAllVideos();
        if (videos == null)
            videos = new ArrayList<>();
        model.addAttribute("videos", videos);
        return "videos_page";
    }
}
