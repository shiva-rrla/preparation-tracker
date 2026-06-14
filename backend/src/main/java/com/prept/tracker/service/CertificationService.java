package com.prept.tracker.service;

import com.prept.tracker.domain.entity.Certification;
import com.prept.tracker.domain.entity.User;
import com.prept.tracker.domain.enums.CertificationStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.CertificationRequest;
import com.prept.tracker.dto.response.CertificationResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.CertificationMapper;
import com.prept.tracker.repository.CertificationRepository;
import com.prept.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;
    private final UserRepository userRepository;

    @Transactional
    public CertificationResponse create(Long userId, CertificationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Certification certification = certificationMapper.toEntity(request);
        certification.setUser(user);
        Certification saved = certificationRepository.save(certification);
        return certificationMapper.toResponse(saved);
    }

    @Transactional
    public CertificationResponse update(Long userId, Long id, CertificationRequest request) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found"));
        if (!certification.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Certification not found");
        }
        certificationMapper.updateEntity(request, certification);
        Certification saved = certificationRepository.save(certification);
        return certificationMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found"));
        if (!certification.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Certification not found");
        }
        certificationRepository.delete(certification);
    }

    public CertificationResponse getById(Long userId, Long id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found"));
        if (!certification.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Certification not found");
        }
        return certificationMapper.toResponse(certification);
    }

    public PageResponse<CertificationResponse> getByUser(Long userId, Pageable pageable) {
        Page<Certification> page = certificationRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<CertificationResponse> getByUserAndStatus(Long userId, CertificationStatus status, Pageable pageable) {
        Page<Certification> page = certificationRepository.findByUserIdAndStatus(userId, status, pageable);
        return toPageResponse(page);
    }

    public List<CertificationResponse> getUpcoming(Long userId, LocalDate beforeDate) {
        List<Certification> certifications = certificationRepository.findByTargetDateBeforeAndStatusNot(beforeDate, CertificationStatus.PASSED);
        return certifications.stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .map(certificationMapper::toResponse)
                .toList();
    }

    private PageResponse<CertificationResponse> toPageResponse(Page<Certification> page) {
        return PageResponse.<CertificationResponse>builder()
                .content(page.getContent().stream().map(certificationMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
