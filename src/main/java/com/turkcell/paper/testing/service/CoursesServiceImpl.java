package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.CoursesDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoursesServiceImpl implements  CoursesService{


    private final CoursesServiceDBConnect coursesServiceDBConnect;

    public CoursesServiceImpl( CoursesServiceDBConnect coursesServiceDBConnect) {
        this.coursesServiceDBConnect = coursesServiceDBConnect;
    }
    @Override
    public List<CoursesDTO> getAll() throws SQLException {
        return coursesServiceDBConnect.getAll();
    }

    @Override
    public List<CoursesDTO> getCoursesEnrolled(int user_id) throws SQLException {
        return coursesServiceDBConnect.getCoursesEnrolled(user_id);
    }
}
