package com.turkcell.paper.testing.controller;

import com.turkcell.paper.testing.dto.CoursesDTO;
import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.service.CoursesService;
import com.turkcell.paper.testing.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {
    private final CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CoursesDTO>> getAll() throws SQLException {
        return ResponseEntity.ok().body(coursesService.getAll());
    }

    @GetMapping("getCoursesEnrolled")
    public ResponseEntity<List<CoursesDTO>> getCoursesEnrolled(@RequestParam int user_id) throws SQLException {
        return ResponseEntity.ok().body(coursesService.getCoursesEnrolled(user_id));
    }

}
