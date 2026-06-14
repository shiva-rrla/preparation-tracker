package com.prept.tracker.controller;

import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.CategoryRequest;
import com.prept.tracker.dto.response.CategoryResponse;
import com.prept.tracker.security.service.AuthorizationService;
import com.prept.tracker.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@Tag(name = "Categories", description = "Category management endpoints")
public class CategoryController {

    private final CategoryService categoryService;
    private final AuthorizationService authorizationService;

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all categories for the current user")
    public ResponseEntity<PageResponse<CategoryResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(categoryService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a specific category by its ID")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(categoryService.getById(userId, id));
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Create a new category")
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category", description = "Update an existing category")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        Long userId = authorizationService.getCurrentUserId();
        return ResponseEntity.ok(categoryService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Delete a category by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = authorizationService.getCurrentUserId();
        categoryService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}