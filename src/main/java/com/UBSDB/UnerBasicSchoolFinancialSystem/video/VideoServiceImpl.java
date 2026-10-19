//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.video;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoRepository repo;

    public VideoServiceImpl() {
    }

    public Video saveVideo(Video video) {
        try {
            return (Video)this.repo.save(video);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return null;
        }
    }

    public Optional<Video> findVideo(String id) {
        try {
            return this.repo.findById(id);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return null;
        }
    }

    public List<Video> getVideos() {
        try {
            return this.repo.findAll();
        } catch (Exception var2) {
            Exception ex = var2;
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteVideo(Video video) {
        try {
            this.repo.delete(video);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
        }

    }

    public List<Video> SearchVideo(String search) {
        try {
            return (List)this.repo.findAll().stream().filter((c) -> {
                return c.getSearchString().toUpperCase().matches(search.toUpperCase());
            }).collect(Collectors.toList());
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return null;
        }
    }

    public Video EditVideo(Video v) {
        try {
            return (Video)this.repo.save(v);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return null;
        }
    }
}
