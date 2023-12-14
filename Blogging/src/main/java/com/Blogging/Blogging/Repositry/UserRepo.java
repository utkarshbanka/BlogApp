package com.Blogging.Blogging.Repositry;

import com.Blogging.Blogging.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

     Optional<User> findByEmail(String email);

}
