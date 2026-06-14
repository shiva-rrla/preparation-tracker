package com.prept.tracker.controller;

import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.response.NotificationResponse;
import com.prept.tracker.security.service.AuthorizationService;
import com.prept.tracker.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@Tag(name = "Notifications", description = "Notification management endpoints")
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthorizationService authorizationService;

    @GetMapping
    @Operation(summary = "Get all notifications", description = "Retrieve all notifications for the current user")
    public ResponseEntity<PageResponse<NotificationResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID", description = "Retrieve a specific notification by its ID")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getById(userId, id));
    }

    @GetMapping("/unread")
    @Operation(summary = "Get unread notifications", description = "Retrieve all unread notifications")
    public ResponseEntity<PageResponse<NotificationResponse>> getUnread(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getUnreadByUser(userId, pageable));
    }

    @GetMapping("/count/unread")
    @Operation(summary = "Get unread notification count", description = "Get count of unread notifications")
    public ResponseEntity<Long> getUnreadCount() {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getUnreadCount(userId));
    }

    @PatchMapping("/{id}/read")
    @Operation(summary = "Mark notification as read", description = "Mark a notification as read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(notificationService.markAsRead(userId, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification", description = "Delete a notification by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = authorizationService.getCurrentUserId();
        notificationService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}