package com.phantom.japanese_dictionary_mvc.repositories;

import com.phantom.japanese_dictionary_mvc.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository <Person, Integer> {
    Optional <Person> findByUsername(String username);

}
