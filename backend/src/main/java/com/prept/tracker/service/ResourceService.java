package com.prept.tracker.service;

import com.prept.tracker.domain.entity.Category;
import com.prept.tracker.domain.entity.Resource;
import com.prept.tracker.domain.entity.User;
import com.prept.tracker.domain.enums.ResourceType;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.ResourceRequest;
import com.prept.tracker.dto.response.ResourceResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.ResourceMapper;
import com.prept.tracker.repository.CategoryRepository;
import com.prept.tracker.repository.ResourceRepository;
import com.prept.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResourceResponse create(Long userId, ResourceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Resource resource = resourceMapper.toEntity(request);
        resource.setUser(user);
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            resource.setCategory(category);
        }
        Resource saved = resourceRepository.save(resource);
        return resourceMapper.toResponse(saved);
    }

    @Transactional
    public ResourceResponse update(Long userId, Long id, ResourceRequest request) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (!resource.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Resource not found");
        }
        resourceMapper.updateEntity(request, resource);
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            resource.setCategory(category);
        } else {
            resource.setCategory(null);
        }
        Resource saved = resourceRepository.save(resource);
        return resourceMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (!resource.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Resource not found");
        }
        resourceRepository.delete(resource);
    }

    public ResourceResponse getById(Long userId, Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (!resource.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Resource not found");
        }
        return resourceMapper.toResponse(resource);
    }

    public PageResponse<ResourceResponse> getByUser(Long userId, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<ResourceResponse> getByUserAndType(Long userId, ResourceType type, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserIdAndType(userId, type, pageable);
        return toPageResponse(page);
    }

    public PageResponse<ResourceResponse> getByUserAndCategory(Long userId, Long categoryId, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<ResourceResponse> search(Long userId, String query, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserIdAndNameContainingIgnoreCase(userId, query, pageable);
        return toPageResponse(page);
    }

    private PageResponse<ResourceResponse> toPageResponse(Page<Resource> page) {
        return PageResponse.<ResourceResponse>builder()
                .content(page.getContent().stream().map(resourceMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
