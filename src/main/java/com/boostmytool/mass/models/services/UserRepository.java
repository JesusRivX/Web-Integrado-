/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.boostmytool.mass.models.services;

import com.boostmytool.mass.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jr860
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
}
