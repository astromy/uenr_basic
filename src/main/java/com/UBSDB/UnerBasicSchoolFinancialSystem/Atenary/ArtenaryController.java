package com.UBSDB.UnerBasicSchoolFinancialSystem.Atenary;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class ArtenaryController {
    @Autowired
    private ArtenaryService artenaryService;
    @Autowired
    private com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeekService termWeekService;

    public ArtenaryController() {
    }

    @GetMapping({"/manageArtenary"})
    public String manageArtenary(Model model, Principal principal) {
        try {
            model.addAttribute("adminArtenary", true);
            model.addAttribute("artenaryList", artenaryService.getAllArtenaryWithWeekInfo());
            model.addAttribute("allWeeks", termWeekService.getAllTermWeeks());
            model.addAttribute("artenaryForm", new ArtenaryRequest());
            return "index";
        } catch (Exception e) {
            model.addAttribute("err", "Error loading weekly activities: " + e.getMessage());
            return "index";
        }
    }

    @PostMapping({"/saveArtenary"})
    @ResponseBody
    public ArtenaryResponse saveArtenary(@RequestBody ArtenaryRequest artenaryRequest) {
        try {
            return artenaryService.SaveArtenary(artenaryRequest).getResponse();
        } catch (Exception e) {
            System.out.println("Error saving Artenary: " + e.getMessage());
            return ArtenaryResponse.builder().build();
        }
    }

    @PostMapping({"/editArtenary"})
    @ResponseBody
    public ArtenaryResponse editArtenary(@RequestBody ArtenaryRequest artenaryRequest) {
        try {
            return artenaryService.EditArtenary(artenaryRequest).getResponse();
        } catch (Exception e) {
            System.out.println("Error editing Artenary: " + e.getMessage());
            return ArtenaryResponse.builder().build();
        }
    }

    @GetMapping({"/getAllArtenary"})
    @ResponseBody
    public Page<ArtenaryResponse> getAllArtenary(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return artenaryService.geArtenaryResponse(page, size);
        } catch (Exception e) {
            System.out.println("Error getting all Artenary: " + e.getMessage());
            return Page.empty();
        }
    }

    @GetMapping({"/getArtenary/{id}"})
    @ResponseBody
    public ResponseEntity<ArtenaryResponse> getArtenary(@PathVariable("id") String id) {
        try {
            return artenaryService.findById(UUID.fromString(id))
                    .map(artenary -> ResponseEntity.ok(artenary.getResponse()))
                    .orElse(ResponseEntity.ok( ArtenaryResponse.builder().build()));
        } catch (Exception e) {
            System.out.println("Error getting Artenary: " + e.getMessage());
            return ResponseEntity.ok(ArtenaryResponse.builder().build());
        }
    }

    @GetMapping({"/api/artenary/current"})
    @ResponseBody
    public List<ArtenaryResponse> getCurrentWeekActivities() {
        try {
            return artenaryService.getCurrentWeekActivities();
        } catch (Exception e) {
            System.out.println("Error getting current week activities: " + e.getMessage());
            return List.of();
        }
    }

    @GetMapping({"/api/artenary/all"})
    @ResponseBody
    public List<ArtenaryResponse> getAllArtenaryApi() {
        try {
            return artenaryService.getAllArtenaryWithWeekInfo();
        } catch (Exception e) {
            System.out.println("Error getting all artenary: " + e.getMessage());
            return List.of();
        }
    }

    @DeleteMapping({"/deleteArtenary/{id}"})
    @ResponseBody
    public String deleteArtenary(@PathVariable("id") String id) {
        try {
            UUID artenaryId = UUID.fromString(id);
            if (artenaryService.findById(artenaryId).isPresent()) {
                artenaryService.deleteArtenary(artenaryId);
                return "Artenary deleted successfully";
            }
            return "Artenary not found";
        } catch (Exception e) {
            System.out.println("Error deleting Artenary: " + e.getMessage());
            return "Error deleting Artenary: " + e.getMessage();
        }
    }
}
