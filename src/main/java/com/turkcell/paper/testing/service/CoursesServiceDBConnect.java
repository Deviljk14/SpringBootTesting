package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.CoursesDTO;
import com.turkcell.paper.testing.dto.CustomerDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CoursesServiceDBConnect {

    public Connection mainConnection() throws SQLException {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sys",
                    "root" , "Ihateyouniu1@"
            );
            return connection;

        }
        catch (Exception exception){
            System.out.println(exception);
            throw new SQLException(exception);
        }
    }

    public List<CoursesDTO> getAll () throws SQLException {
        Connection connection = mainConnection();
        if(connection != null) {
            Statement statement;
            statement = connection.createStatement();
            try {
                ResultSet resultSet;
                resultSet = statement.executeQuery(
                        "select * from infinity_courses"
                );

                List<CoursesDTO> coursesList = new ArrayList<>();
                while (resultSet.next()) {
                    CoursesDTO coursesDTO = new CoursesDTO(
                            resultSet.getLong("id"),
                            resultSet.getString("course_name"),
                            resultSet.getString("img")
                    );
                    coursesList.add(coursesDTO);
                }
                resultSet.close();
                return coursesList;
            }
            catch (Exception e) {
                throw new SQLException(e);
            }
        }
        return null;
    }

    public List<CoursesDTO> getCoursesEnrolled (int user_id) throws SQLException {
        Connection connection = mainConnection();
        if(connection != null) {
            try {
                ResultSet resultSet;
                PreparedStatement statement = connection.prepareStatement("SELECT * from infinity_courses_enrolled where user_id = ?");
                statement.setInt(1, user_id);
                resultSet = statement.executeQuery();

                List<CoursesDTO> coursesList = new ArrayList<>();
                while (resultSet.next()) {
                    CoursesDTO coursesDTO = new CoursesDTO(
                            resultSet.getLong("id"),
                            resultSet.getString("course_name"),
                            resultSet.getString("img")
                    );
                    coursesList.add(coursesDTO);
                }
                resultSet.close();
                return coursesList;
            }
            catch (Exception e) {
                throw new SQLException(e);
            }
        }
        return null;
    }

}
