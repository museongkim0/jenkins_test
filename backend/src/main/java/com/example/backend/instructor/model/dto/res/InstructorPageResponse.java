package com.example.backend.instructor.model.dto.res;

import com.example.backend.instructor.model.Instructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InstructorPageResponse {
    private List<InstructorResponseDto> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public static InstructorPageResponse from(Page<Instructor> page) {
        InstructorPageResponse response = new InstructorPageResponse();
        response.setContent(page.getContent().stream().map(InstructorResponseDto::from).collect(Collectors.toList()));
        response.setCurrentPage(page.getNumber());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        return response;
    }
}
