package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.ContactUsDTO;
import com.turkcell.paper.testing.dto.CoursesDTO;
import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.dto.UpdateCustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CoursesService {

    List<CoursesDTO> getAll() throws SQLException;

    List<CoursesDTO> getCoursesEnrolled(int user_id) throws SQLException;


}
