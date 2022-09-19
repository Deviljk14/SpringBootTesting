package com.turkcell.paper.testing.repository;

import com.turkcell.paper.testing.entity.Customer;
import com.turkcell.paper.testing.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByNotificationEmailAndCustomerType(String notificationEmail, CustomerType customerType);
}
