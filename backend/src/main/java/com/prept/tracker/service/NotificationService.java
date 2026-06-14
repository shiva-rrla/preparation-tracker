package com.prept.tracker.service;

import com.prept.tracker.domain.entity.Notification;
import com.prept.tracker.domain.entity.User;
import com.prept.tracker.domain.enums.NotificationType;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.response.NotificationResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.NotificationMapper;
import com.prept.tracker.repository.NotificationRepository;
import com.prept.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    @Transactional
    public NotificationResponse createNotification(Long userId, NotificationType type, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Notification notification = Notification.builder()
                .type(type)
                .message(message)
                .read(false)
                .user(user)
                .build();
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }

    public PageResponse<NotificationResponse> getByUser(Long userId, Pageable pageable) {
        Page<Notification> page = notificationRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<NotificationResponse> getUnreadByUser(Long userId, Pageable pageable) {
        Page<Notification> page = notificationRepository.findByUserIdAndRead(userId, false, pageable);
        return toPageResponse(page);
    }

    @Transactional
    public NotificationResponse markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        if (!notification.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Notification not found");
        }
        notification.setRead(true);
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }

    public NotificationResponse getById(Long userId, Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        if (!notification.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Notification not found");
        }
        return notificationMapper.toResponse(notification);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        if (!notification.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Notification not found");
        }
        notificationRepository.delete(notification);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndRead(userId, false);
    }

    private PageResponse<NotificationResponse> toPageResponse(Page<Notification> page) {
        return PageResponse.<NotificationResponse>builder()
                .content(page.getContent().stream().map(notificationMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
