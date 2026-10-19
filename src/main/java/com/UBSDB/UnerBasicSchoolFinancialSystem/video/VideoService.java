//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.video;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface VideoService {
    Video saveVideo(Video video);

    Optional<Video> findVideo(String id);

    Video EditVideo(Video v);

    List<Video> getVideos();

    void deleteVideo(Video id);

    List<Video> SearchVideo(String search);
}
