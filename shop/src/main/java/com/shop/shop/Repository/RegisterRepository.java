package com.shop.shop.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.Entity.Register;

public interface RegisterRepository extends JpaRepository<Register ,Integer>{
        Optional<Register> findByMobileNumber(String mobileNumber);


}
