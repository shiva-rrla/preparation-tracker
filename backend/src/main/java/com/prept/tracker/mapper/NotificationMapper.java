package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Notification;
import com.prept.tracker.dto.response.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);
}