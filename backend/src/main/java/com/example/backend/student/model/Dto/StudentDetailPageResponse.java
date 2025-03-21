package com.example.backend.student.model.Dto;

import com.example.backend.student.model.StudentDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetailPageResponse {
    @Schema(description = "학생 목록 페이지 번호")
    private int page;
    @Schema(description = "학생 목록 페이지 크기")
    private int size;
    @Schema(description = "학생 목록 총 갯수")
    private long totalElements;
    @Schema(description = "총 페이지 수")
    private int totalPages;
    @Schema(description = "다음 페이지 여부")
    private boolean hasNext;
    @Schema(description = "이전 페이지 여부")
    private boolean hasPrevious;
    @Schema(description = "해당 페이지 학생 목록")
    private List<StudentResponseDto> studentList;

    public static StudentDetailPageResponse from(Page<StudentDetail> studentPage) {
        return StudentDetailPageResponse.builder()
                .page(studentPage.getNumber())
                .size(studentPage.getSize())
                .totalElements(studentPage.getTotalElements())
                .totalPages(studentPage.getTotalPages())
                .hasNext(studentPage.hasNext())
                .hasPrevious(studentPage.hasPrevious())
                .studentList(studentPage.stream().map(StudentResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}
