package com.prept.tracker.service;

import com.prept.tracker.dto.request.CategoryRequest;
import com.prept.tracker.dto.response.CategoryResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.CategoryMapper;
import com.prept.tracker.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void shouldGetCategoriesByUser() {
        when(categoryRepository.findByUserId(anyLong(), any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        var result = categoryService.getByUser(1L, PageRequest.of(0, 20));

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        verify(categoryRepository).findByUserId(eq(1L), any());
    }

    @Test
    void shouldThrowWhenCategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getById(1L, 99L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
