package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.StudySession;
import com.prept.tracker.dto.request.StudySessionRequest;
import com.prept.tracker.dto.response.StudySessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ResourceMapper.class})
public interface StudySessionMapper {

    @Mapping(target = "resource", source = "resource", qualifiedByName = "toResourceResponse")
    StudySessionResponse toResponse(StudySession session);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    StudySession toEntity(StudySessionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(StudySessionRequest request, @MappingTarget StudySession session);

    @Named("toResourceResponse")
    default com.prept.tracker.dto.response.ResourceResponse mapResource(
            com.prept.tracker.domain.entity.Resource resource) {
        if (resource == null) {
            return null;
        }
        return ResourceMapper.INSTANCE.toResponse(resource);
    }
}