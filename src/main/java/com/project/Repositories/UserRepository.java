package com.project.Repositories;

import com.project.Models.AdditionalCost;
import com.project.Models.User;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserLogin(String login);
    List<User> findAllByUserLogin(String login);
}
