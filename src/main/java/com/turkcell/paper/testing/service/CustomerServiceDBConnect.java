package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.dto.UpdateCustomerDTO;
import com.turkcell.paper.testing.exception.InvalidCustomerRequestException;
import com.turkcell.paper.testing.exception.UserNotFoundException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public  class CustomerServiceDBConnect {
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

    public List<CustomerDTO> getAll () throws SQLException {
        Connection connection = mainConnection();
        if(connection != null) {
            Statement statement;
            statement = connection.createStatement();
            try {
                ResultSet resultSet;
                resultSet = statement.executeQuery(
                        "select * from infinity_users"
                );

                List<CustomerDTO> customerList = new ArrayList<>();
                while (resultSet.next()) {
                    CustomerDTO customerDTO = new CustomerDTO(
                            resultSet.getLong("id"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("profile_pic"),
                            resultSet.getString("user_role")


                    );
                    customerList.add(customerDTO);
                }
                resultSet.close();
                return customerList;
            }
            catch (Exception e) {
                throw new SQLException(e);
            }
        }
        return null;
    }

    public CustomerDTO login (CustomerDTO customerDTO) throws SQLException {
        Connection connection = mainConnection();
        if(connection!=null){
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * from infinity_users where email = ?");
                statement.setString(1, customerDTO.getEmail());
                ResultSet resultSet;
                resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new UserNotFoundException();
                }
                if (!Objects.equals(customerDTO.getPassword(), resultSet.getString("password"))) {
                    throw new InvalidCustomerRequestException("Invalid Credentials");
                }

                return new CustomerDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        null,
                        resultSet.getString("profile_pic"),
                        resultSet.getString("user_role")
                );
            }
            catch (Exception e){
                throw new SQLException(e);
            }
        }
        return null;
    }

    public boolean checkEmailAlreadyExists(String email) throws SQLException{
        Connection connection = mainConnection();
        if(connection!=null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * from infinity_users where email = ?");
                statement.setString(1, email);
                ResultSet resultSet;
                resultSet = statement.executeQuery();
                return resultSet.next();
            }
            catch (Exception e){
                throw new SQLException(e);
            }
        }
        throw new SQLException();
    }

    public boolean checkEmailAlreadyExistsForUpdate(String email, String id) throws SQLException{
        Connection connection = mainConnection();
        if(connection!=null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * from infinity_users where email = ? and id  != ?");
                statement.setString(1, email);
                statement.setString(2,id);
                ResultSet resultSet;
                resultSet = statement.executeQuery();
                return resultSet.next();
            }
            catch (Exception e){
                throw new SQLException(e);
            }
        }
        throw new SQLException();
    }

    public boolean isValidOldPass (UpdateCustomerDTO updateCustomerDTO) throws SQLException {
        Connection connection = mainConnection();
        if(connection!=null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * from infinity_users where id = ? and password = ?");
                statement.setString(1, String.valueOf(updateCustomerDTO.getId()));
                statement.setString(2, updateCustomerDTO.getOldPassword());
                ResultSet resultSet;
                resultSet = statement.executeQuery();
                return resultSet.next();
            }
            catch (Exception e){
                throw new SQLException(e);
            }
        }
        throw new SQLException();
    }

    public CustomerDTO save (CustomerDTO customerDTO) throws SQLException {
        Connection connection = mainConnection();
        if(connection!=null){
            try {
                PreparedStatement statement = connection.prepareStatement("insert into infinity_users Values (null, ?, ?, ?, ?,?)");
                statement.setString(1, customerDTO.getUsername());
                statement.setString(2, customerDTO.getEmail());
                statement.setString(3, customerDTO.getPassword());
                statement.setString(4,null);
                statement.setString(5, customerDTO.getUser_role());

                statement.executeUpdate();
                return getCustomerDTO(connection, statement, customerDTO.getEmail());
            }
            catch (Exception e){
                throw new SQLException(e);
            }
        }
        return null;
    }

    public CustomerDTO update (UpdateCustomerDTO updatecustomerDTO) throws SQLException {
        Connection connection = mainConnection();
        if(connection!=null){
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "update infinity_users set username=COALESCE(?,username),email=COALESCE(?,email),password=COALESCE(?,password),profile_pic=COALESCE(?,profile_pic) where id=?");

                String sql = "update infinity_users set";
                statement.setString(1, updatecustomerDTO.getUsername());
                statement.setString(2, updatecustomerDTO.getEmail());
                if ((updatecustomerDTO.getIsChangePassword())) {
                    statement.setString(3, updatecustomerDTO.getNewPassword());
                } else {
                    statement.setString(3, null);
                }
//                statement.setString(4, updatecustomerDTO.getProfile_pic());
                if(updatecustomerDTO.getProfile_pic() != null && !updatecustomerDTO.getProfile_pic().isEmpty()){
                    byte [] array = updatecustomerDTO.getProfile_pic().getBytes();
                    String base64ImageString = updatecustomerDTO.getProfile_pic().split(",")[1];;
                    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64ImageString);
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));

                    File out = new File("C:\\public_infinity\\profile_pictures\\" + "profile_pic_" + updatecustomerDTO.getId() + ".jpg");
                    ImageIO.write(img, "jpg", out);
                    statement.setString(4, "http://localhost/public_infinity/profile_pictures/" + "profile_pic_" + updatecustomerDTO.getId() + ".jpg");

                }

                else {
                    statement.setString(4, null);

                }

                statement.setString(5, String.valueOf(updatecustomerDTO.getId()));
                statement.executeUpdate();
                return getCustomerDTO(connection, statement, updatecustomerDTO.getEmail());
            }
            catch (Exception e){
                throw new SQLException(e);
            }
        }
        return null;
    }

    private CustomerDTO getCustomerDTO(Connection connection, PreparedStatement statement, String email) throws SQLException {
        ResultSet resultSet;
        statement = connection.prepareStatement("SELECT * from infinity_users where email = ?");
        statement.setString(1, email);
        resultSet = statement.executeQuery();

        resultSet.next();
        return new CustomerDTO(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                null,
                resultSet.getString("profile_pic"),
                resultSet.getString("user_role")

        );
    }


}
