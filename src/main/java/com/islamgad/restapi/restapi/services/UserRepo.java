package com.islamgad.restapi.restapi.services;

import com.islamgad.restapi.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
