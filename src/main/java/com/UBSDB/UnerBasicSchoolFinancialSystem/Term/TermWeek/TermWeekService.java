/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Enock
 */
@Service
@Slf4j
public class TermWeekService {
    @Autowired
    private TermWeekRepository termWeekRepository;
    @Autowired
    private TermRepository termRepository;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public String saveTermWeek(TermWeek termWeek) {
        // Validate date range: must be Monday to Friday, max 1 week
        if (!isValidWeekRange(termWeek.getStartDate(), termWeek.getEndDate())) {
          log.info("Invalid week range: Week must start on Monday and end on Friday, and must not exceed 5 days.");
            return "Invalid week range: Week must start on Monday and end on Friday, and must not exceed 5 days.";
        }
        
        // Validate that the week falls within the term's date range
        Term term = termRepository.findById(termWeek.getTerm().getId()).orElse(null);
        if (term == null) {
            return "Term not found.";
        }
        
        LocalDate weekStart = LocalDate.parse(termWeek.getStartDate());
        LocalDate weekEnd = LocalDate.parse(termWeek.getEndDate());
        LocalDate termStart = LocalDate.parse(term.getStartDate());
        LocalDate termEnd = LocalDate.parse(term.getEndDate());
        log.info("Week saved successfully.");
        if (weekStart.isBefore(termStart) || weekEnd.isAfter(termEnd)) {

            return "Week dates must fall within the term date range (" + term.getStartDate() + " to " + term.getEndDate() + ").";
        }
        
        // Check for overlapping weeks within the same term
        List<TermWeek> existingWeeks = termWeekRepository.findByTermId(term.getId());
        for (TermWeek existing : existingWeeks) {
            if (isOverlapping(weekStart, weekEnd, 
                LocalDate.parse(existing.getStartDate()), 
                LocalDate.parse(existing.getEndDate()))) {
                return "Week overlaps with existing week: " + existing.getWeekName() + 
                       " (" + existing.getStartDate() + " to " + existing.getEndDate() + ").";
            }
        }
        
        // Auto-generate week name if not provided
        if (termWeek.getWeekName() == null || termWeek.getWeekName().trim().isEmpty()) {
            int weekNumber = existingWeeks.size() + 1;
            termWeek.setWeekName("Week " + weekNumber);
        }
        
        // Set termWeekId if not set
        if (termWeek.getTermWeekId() == null) {
            termWeek.setTermWeekId(UUID.randomUUID());
        }
        log.info("Week saved successfully.");

        termWeekRepository.save(termWeek);
        return "Week saved successfully.";
    }
    
    public String updateTermWeek(TermWeek termWeek) {
        // Validate date range
        if (!isValidWeekRange(termWeek.getStartDate(), termWeek.getEndDate())) {
            return "Invalid week range: Week must start on Monday and end on Friday, and must not exceed 5 days.";
        }
        
        Term term = termRepository.findById(termWeek.getTerm().getId()).orElse(null);
        if (term == null) {
            return "Term not found.";
        }
        
        LocalDate weekStart = LocalDate.parse(termWeek.getStartDate());
        LocalDate weekEnd = LocalDate.parse(termWeek.getEndDate());
        LocalDate termStart = LocalDate.parse(term.getStartDate());
        LocalDate termEnd = LocalDate.parse(term.getEndDate());
        
        if (weekStart.isBefore(termStart) || weekEnd.isAfter(termEnd)) {
            return "Week dates must fall within the term date range (" + term.getStartDate() + " to " + term.getEndDate() + ").";
        }
        
        // Check for overlapping weeks (excluding the current week being updated)
        List<TermWeek> existingWeeks = termWeekRepository.findByTermId(term.getId());
        for (TermWeek existing : existingWeeks) {
            if (existing.getTermWeekId().equals(termWeek.getTermWeekId())) {
                continue; // Skip the week being updated
            }
            if (isOverlapping(weekStart, weekEnd, 
                LocalDate.parse(existing.getStartDate()), 
                LocalDate.parse(existing.getEndDate()))) {
                return "Week overlaps with existing week: " + existing.getWeekName() + 
                       " (" + existing.getStartDate() + " to " + existing.getEndDate() + ").";
            }
        }
        
        termWeekRepository.save(termWeek);
        return "Week updated successfully.";
    }
    
    public List<TermWeek> getWeeksByTermId(Long termId) {
        return termWeekRepository.findByTermId(termId);
    }

    public List<TermWeek> getAllTermWeeks() {
        return termWeekRepository.findAll();
    }

    public TermWeek getWeekById(UUID weekId) {
        return termWeekRepository.findById(weekId).orElse(null);
    }

    public TermWeek getCurrentWeek(Term term) {
        LocalDate today = LocalDate.now();
        List<TermWeek> weeks = termWeekRepository.findByTermId(term.getId());
        for (TermWeek week : weeks) {
            LocalDate weekStart = LocalDate.parse(week.getStartDate());
            LocalDate weekEnd = LocalDate.parse(week.getEndDate());
            if (!today.isBefore(weekStart) && !today.isAfter(weekEnd)) {
                return week;
            }
        }
        return null;
    }
    
    public String deleteWeek(UUID weekId) {
        termWeekRepository.deleteById(weekId);
        return "Week deleted successfully.";
    }
    
    private boolean isValidWeekRange(String startDateStr, String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
            LocalDate endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
            
            // Check that start is Monday and end is Friday
            if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
                return false;
            }
            if (endDate.getDayOfWeek() != DayOfWeek.FRIDAY) {
                return false;
            }
            
            // Check that the range is exactly 5 days (Monday to Friday)
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
            if (daysBetween != 4) { // Monday to Friday is 4 days difference
                return false;
            }
            
            // Check that start is before end
            if (startDate.isAfter(endDate)) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }

    public List<TermWeek> calculateTermWeeks_(
            LocalDate termStartDate,
            LocalDate termEndDate) {

        List<TermWeek> weeks = new ArrayList<>();

        if (termStartDate == null || termEndDate == null) {
            throw new IllegalArgumentException(
                    "Start date and end date cannot be null"
            );
        }

        if (termStartDate.isAfter(termEndDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }

        // If the term starts on Saturday or Sunday,
        // move it to the following Monday.
        LocalDate currentStart = termStartDate;

        if (currentStart.getDayOfWeek() == DayOfWeek.SATURDAY) {
            currentStart = currentStart.plusDays(2);
        } else if (currentStart.getDayOfWeek() == DayOfWeek.SUNDAY) {
            currentStart = currentStart.plusDays(1);
        }

        int weekNumber = 1;

        while (!currentStart.isAfter(termEndDate)) {

            // A school week runs from Monday to Friday.
            LocalDate currentEnd = currentStart;

            // Move forward until Friday.
            while (currentEnd.getDayOfWeek() != DayOfWeek.FRIDAY
                    && !currentEnd.isAfter(termEndDate)) {

                currentEnd = currentEnd.plusDays(1);
            }

            // Don't allow the week to go beyond the term end date.
            if (currentEnd.isAfter(termEndDate)) {
                currentEnd = termEndDate;
            }

            // Calculate number of school days only.
            int numberOfDays = 0;
            LocalDate date = currentStart;

            while (!date.isAfter(currentEnd)) {

                if (date.getDayOfWeek() != DayOfWeek.SATURDAY
                        && date.getDayOfWeek() != DayOfWeek.SUNDAY) {

                    numberOfDays++;
                }

                date = date.plusDays(1);
            }

            TermWeek termWeekRequest =
                    TermWeek.builder()
                            .weekName("Week " + weekNumber)
                            .numberOfDays(String.valueOf(numberOfDays))
                            .endDate(currentEnd.toString())
                            .startDate(currentStart.toString())
                            .build();

            weeks.add(termWeekRequest);

            // Move to the following Monday.
            currentStart = currentEnd.plusDays(1);

            while (currentStart.getDayOfWeek() == DayOfWeek.SATURDAY
                    || currentStart.getDayOfWeek() == DayOfWeek.SUNDAY) {

                currentStart = currentStart.plusDays(1);
            }

            weekNumber++;
        }

        return weeks;
    }

public List<TermWeek> calculateTermWeeks(
        LocalDate termStartDate,
        LocalDate termEndDate) {

    List<TermWeek> weeks = new ArrayList<>();

    if (termStartDate == null || termEndDate == null) {
        throw new IllegalArgumentException(
                "Start date and end date cannot be null"
        );
    }

    if (termStartDate.isAfter(termEndDate)) {
        throw new IllegalArgumentException(
                "Start date cannot be after end date"
        );
    }

    LocalDate currentStart = termStartDate;

    // If the term starts on Saturday or Sunday,
    // move to the next Monday.
    if (currentStart.getDayOfWeek() == DayOfWeek.SATURDAY) {
        currentStart = currentStart.plusDays(2);
    } else if (currentStart.getDayOfWeek() == DayOfWeek.SUNDAY) {
        currentStart = currentStart.plusDays(1);
    }

    int weekNumber = 1;

    while (!currentStart.isAfter(termEndDate)) {

        /*
         * Find Friday of the current week.
         */
        LocalDate currentEnd;

        if (currentStart.getDayOfWeek() == DayOfWeek.MONDAY) {
            currentEnd = currentStart.plusDays(4);

        } else if (currentStart.getDayOfWeek() == DayOfWeek.TUESDAY) {
            currentEnd = currentStart.plusDays(3);

        } else if (currentStart.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            currentEnd = currentStart.plusDays(2);

        } else if (currentStart.getDayOfWeek() == DayOfWeek.THURSDAY) {
            currentEnd = currentStart.plusDays(1);

        } else {
            // Friday
            currentEnd = currentStart;
        }

        /*
         * Don't allow the week to go beyond
         * the actual term end date.
         */
        if (currentEnd.isAfter(termEndDate)) {
            currentEnd = termEndDate;
        }

        /*
         * Calculate school days only.
         */
        int numberOfDays = 0;

        LocalDate date = currentStart;

        while (!date.isAfter(currentEnd)) {

            if (date.getDayOfWeek() != DayOfWeek.SATURDAY
                    && date.getDayOfWeek() != DayOfWeek.SUNDAY) {

                numberOfDays++;
            }

            date = date.plusDays(1);
        }

        TermWeek termWeekRequest =
                TermWeek.builder()
                        .weekName("Week " + weekNumber)
                        .numberOfDays(String.valueOf(numberOfDays))
                        .startDate(currentStart.toString())
                        .endDate(currentEnd.toString())
                        .build();

        weeks.add(termWeekRequest);

        /*
         * Move to the next Monday.
         */
        currentStart = currentEnd.plusDays(1);

        while (currentStart.getDayOfWeek() == DayOfWeek.SATURDAY
                || currentStart.getDayOfWeek() == DayOfWeek.SUNDAY) {

            currentStart = currentStart.plusDays(1);
        }

        weekNumber++;
    }

    return weeks;
}


}
