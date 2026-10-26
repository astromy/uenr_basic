package com.UBSDB.UnerBasicSchoolFinancialSystem.Atenary;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtenaryService {
    private @Autowired ArtenaryRepository repo;
    private @Autowired TermWeekService termWeekService;

    public Artenary SaveArtenary(ArtenaryRequest artenaryRequest) {
        try {
            TermWeek termWeek = termWeekService.getWeekById(UUID.fromString(artenaryRequest.getWeekId()));
            if (termWeek == null) {
                throw new RuntimeException("TermWeek not found with id: " + artenaryRequest.getWeekId());
            }
            return repo.save(Artenary
                    .builder()
                    .week(termWeek)
                    .activity(artenaryRequest.getActivity())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Artenary EditArtenary(ArtenaryRequest artenaryRequest) {
        try {
            var artenary = repo.findById(UUID.fromString(artenaryRequest.getId()));

            if (artenary.isPresent()) {
                var finalArtenary = artenary.get();
                TermWeek termWeek = termWeekService.getWeekById(UUID.fromString(artenaryRequest.getWeekId()));
                if (termWeek == null) {
                    throw new RuntimeException("TermWeek not found with id: " + artenaryRequest.getWeekId());
                }
                finalArtenary.setWeek(termWeek);
                finalArtenary.setActivity(artenaryRequest.getActivity());
                return repo.save(finalArtenary);
            } else {
                return new Artenary();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Page<ArtenaryResponse> geArtenaryResponse(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "artenaryId"));
        Page<Artenary> artenaryPage = repo.findAll(pageable);
        return artenaryPage.map(Artenary::getResponse);
    }

    public Optional<Artenary> findById(UUID id) {
        return repo.findById(id);
    }

    public void deleteArtenary(UUID id) {
        repo.deleteById(id);
    }

    public List<ArtenaryResponse> getAllArtenaryWithWeekInfo() {
        List<Artenary> allArtenary = repo.findAll(Sort.by(Sort.Direction.DESC, "artenaryId"));
        List<ArtenaryResponse> responses = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Artenary artenary : allArtenary) {
            TermWeek week = artenary.getWeek();
            if (week != null) {
                try {
                    LocalDate startDate = LocalDate.parse(week.getStartDate());
                    LocalDate endDate = LocalDate.parse(week.getEndDate());
                    boolean isCurrentWeek = !today.isBefore(startDate) && !today.isAfter(endDate);

                    ArtenaryResponse response = artenary.getResponse();
                    response.setWeekName(week.getWeekName());
                    response.setCurrentWeek(isCurrentWeek);
                    responses.add(response);
                } catch (Exception e) {
                    // Skip if date parsing fails
                    responses.add(artenary.getResponse());
                }
            }
        }
        return responses;
    }

    public List<ArtenaryResponse> getCurrentWeekActivities() {
        List<ArtenaryResponse> allActivities = getAllArtenaryWithWeekInfo();
        List<ArtenaryResponse> currentWeekActivities = new ArrayList<>();

        for (ArtenaryResponse activity : allActivities) {
            if (activity.isCurrentWeek()) {
                currentWeekActivities.add(activity);
            }
        }
        return currentWeekActivities;
    }
}
