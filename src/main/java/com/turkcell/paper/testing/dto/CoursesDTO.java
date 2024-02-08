package com.turkcell.paper.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesDTO {
    private Long id;
    private String course_name;
    private String img;
}
