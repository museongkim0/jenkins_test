package com.example.backend.manager.model.dto;

import com.example.backend.manager.model.Test;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

@Data
public class TestPageResponse {
    @Schema(description = "해당 페이지 시험 목록")
    private List<TestResponseDto> content;
    @Schema(description = "시험 목록 현재 페이지")
    private int currentPage;
    @Schema(description = "총 페이지 수")
    private int totalPages;
    @Schema(description = "시험 목록 총 갯수")
    private long totalElements;

    public static TestPageResponse from(Page<Test> page) {
        TestPageResponse response = new TestPageResponse();
        response.setContent(page.getContent().stream()
                .map(TestResponseDto::of)
                .collect(Collectors.toList()));
        response.setCurrentPage(page.getNumber());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        return response;
    }
}
