package com.prept.tracker.service;

import com.prept.tracker.domain.enums.GoalStatus;
import com.prept.tracker.domain.enums.PlanStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.response.CertificationResponse;
import com.prept.tracker.dto.response.DashboardResponse;
import com.prept.tracker.dto.response.StudySessionResponse;
import com.prept.tracker.repository.CertificationRepository;
import com.prept.tracker.repository.GoalRepository;
import com.prept.tracker.repository.NotificationRepository;
import com.prept.tracker.repository.PreparationPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PreparationPlanRepository preparationPlanRepository;
    private final CertificationRepository certificationRepository;
    private final GoalRepository goalRepository;
    private final NotificationRepository notificationRepository;
    private final StudySessionService studySessionService;
    private final CertificationService certificationService;

    public DashboardResponse getDashboardData(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        Integer totalStudyHoursWeek = studySessionService.getTotalHoursForUser(userId, weekStart, weekEnd);

        long activePlansCount = preparationPlanRepository.findByUserIdAndStatus(userId, PlanStatus.IN_PROGRESS, Pageable.unpaged()).getTotalElements();
        long certificationsCount = certificationRepository.findByUserId(userId, Pageable.unpaged()).getTotalElements();
        long goalsCount = goalRepository.findByUserIdAndStatus(userId, GoalStatus.ACTIVE, Pageable.unpaged()).getTotalElements();

        List<CertificationResponse> upcomingCertifications = certificationService.getUpcoming(userId, today.plusMonths(3));

        Pageable recentSessionsPageable = PageRequest.of(0, 5, Sort.by("sessionDate").descending());
        PageResponse<StudySessionResponse> recentSessionsPage = studySessionService.getByUser(userId, recentSessionsPageable);
        List<StudySessionResponse> recentStudySessions = recentSessionsPage.getContent() != null ? recentSessionsPage.getContent() : Collections.emptyList();

        long unreadNotificationsCount = notificationRepository.countByUserIdAndRead(userId, false);

        Integer learningStreak = 0;

        return DashboardResponse.builder()
                .totalStudyHoursWeek(totalStudyHoursWeek)
                .activePlansCount(activePlansCount)
                .certificationsCount(certificationsCount)
                .goalsCount(goalsCount)
                .upcomingCertifications(upcomingCertifications)
                .recentStudySessions(recentStudySessions)
                .unreadNotificationsCount(unreadNotificationsCount)
                .learningStreak(learningStreak)
                .build();
    }
}
