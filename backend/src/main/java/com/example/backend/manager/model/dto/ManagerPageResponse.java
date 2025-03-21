package com.example.backend.manager.model.dto;

import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ManagerPageResponse {
    @Schema(description = "해당 페이지 매니저 목록")
    private List<ManagerResponseDto> content;
    @Schema(description = "매니저 목록 현재 페이지")
    private int currentPage;
    @Schema(description = "총 페이지 수")
    private int totalPages;
    @Schema(description = "시험 목록 총 갯수")
    private long totalElements;

    public static ManagerPageResponse from(Page<User> page) {
        ManagerPageResponse response = new ManagerPageResponse();
        response.setContent(page.getContent().stream().map(ManagerResponseDto::of).collect(Collectors.toList()));
        response.setCurrentPage(page.getNumber());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        return response;
    }
}
