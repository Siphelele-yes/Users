package com.repository;

import com.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByFirstName(String firstName);

    public List<User> findByLastName(String lastName);

    public List<User> findByAge(Integer age);

    public List<User> findByCountry(String country);

    User findUserByUsername(String username);

}
