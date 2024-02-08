package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.ContactUsDTO;
import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.dto.UpdateCustomerDTO;
import com.turkcell.paper.testing.exception.InvalidCustomerRequestException;
import com.turkcell.paper.testing.util.EmailUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final CustomerServiceDBConnect customerServiceDBConnect;

    public CustomerServiceImpl( CustomerServiceDBConnect customerServiceDBConnect) {
        this.customerServiceDBConnect = customerServiceDBConnect;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) throws SQLException {
        this.validateSave(customerDTO);
//        return toDto(customerRepository.save(toEntity(customerDTO)));
        return customerServiceDBConnect.save(customerDTO);

    }

    @Override
    public CustomerDTO update(UpdateCustomerDTO updateCustomerDTO) throws SQLException {
        this.validateUpdate(updateCustomerDTO);
        return customerServiceDBConnect.update(updateCustomerDTO);
    }

    @Override
    public void contactUs(@NotNull ContactUsDTO contactUsDTO){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("souvikchoudhury14@gmail.com");
        mailMessage.setSubject("Question related to Infinity Tech Resource");
        mailMessage.setText("Name: " + contactUsDTO.getFirstName() + " " +
                contactUsDTO.getLastName() + "\nEmail: " + contactUsDTO.getEmail() +
                "\nMessage: " + contactUsDTO.getMessage()
        );
        javaMailSender.send(mailMessage);
    }

    @Override
    public CustomerDTO login(CustomerDTO customerDTO) throws SQLException {
        return customerServiceDBConnect.login(customerDTO);
    }

    private void validateSave(CustomerDTO customerDTO) throws SQLException {
        validateId(customerDTO.getId());
        validateEmail(customerDTO.getEmail());
        validateEmailExistence(customerDTO.getEmail());
        notNullCheckForSignUp(customerDTO);
    }

    private void validateUpdate(UpdateCustomerDTO updateCustomerDTO) throws SQLException {
        validateEmail(updateCustomerDTO.getEmail());
        notNullCheck(updateCustomerDTO);
        validatePassWords(updateCustomerDTO);
        validateEmailExistenceForUpdate(updateCustomerDTO.getEmail() , String.valueOf(updateCustomerDTO.getId()));
    }

    private void validatePassWords(UpdateCustomerDTO updateCustomerDTO) throws SQLException {
        if(updateCustomerDTO.getIsChangePassword()){
            if(updateCustomerDTO.getNewPassword().equals(updateCustomerDTO.getOldPassword())) {
                throw new InvalidCustomerRequestException("New Password cannot be same as old password");
            }
            else if(!updateCustomerDTO.getNewPassword().equals(updateCustomerDTO.getConfPassword())){
                throw new InvalidCustomerRequestException("New Password and confirm Password do not match");
            }
            else if(!customerServiceDBConnect.isValidOldPass(updateCustomerDTO)){
                throw new InvalidCustomerRequestException("Invalid Old Password");
            }
        }
    }
    private void notNullCheck(UpdateCustomerDTO updateCustomerDTO){
        if(updateCustomerDTO.getEmail() == null || updateCustomerDTO.getEmail().isEmpty() ) {
            throw new InvalidCustomerRequestException("Email cannot be empty");
        }
        else if(updateCustomerDTO.getUsername() == null || updateCustomerDTO.getUsername().isEmpty() ) {
            throw new InvalidCustomerRequestException("Username cannot be empty");
        }
        else if(updateCustomerDTO.getIsChangePassword() && (updateCustomerDTO.getNewPassword() == null ||
                    updateCustomerDTO.getNewPassword().isEmpty() ||
                    updateCustomerDTO.getOldPassword() == null ||
                    updateCustomerDTO.getOldPassword().isEmpty() ||
                    updateCustomerDTO.getConfPassword() == null ||
                    updateCustomerDTO.getConfPassword().isEmpty()
        )) {
            throw new InvalidCustomerRequestException("Passwords cannot be empty if you chose to change password. Click 'Go Back' to not change the password");

        }
    }

    private void notNullCheckForSignUp(CustomerDTO customerDTO) {
        if (customerDTO.getEmail() == null ||
                customerDTO.getEmail().isEmpty() ||
                customerDTO.getUsername() == null ||
                customerDTO.getUsername().isEmpty() ||
                customerDTO.getPassword() == null ||
                customerDTO.getPassword().isEmpty()
        ) {
            throw new InvalidCustomerRequestException("All fields are mandatory");
        }
    }

    private void validateEmail(String email) {
        if (!EmailUtil.validate(email)) {
            throw new InvalidCustomerRequestException("Email is not valid");
        }
    }

    private void validateEmailExistence(String email) throws SQLException {
        if (isCustomerExist(email)) {
            throw new InvalidCustomerRequestException("Email has been already used.");
        }
    }

    private void validateEmailExistenceForUpdate(String email , String id) throws  SQLException {
        if(customerServiceDBConnect.checkEmailAlreadyExistsForUpdate(email, id)){
            throw new InvalidCustomerRequestException("Email has been already used.");
        }
    }

    private void validateId(Long id) {
        if (id != null) {
            throw new InvalidCustomerRequestException("User ID must be null while registering");
        }
    }

    @Override
    public List<CustomerDTO> getAll() throws SQLException {
        return customerServiceDBConnect.getAll();
    }

    public boolean isCustomerExist(String email) throws SQLException {
        return customerServiceDBConnect.checkEmailAlreadyExists(email);
    }
}
