package com.phantom.japanese_dictionary_mvc.repositories;

import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultsRepository extends JpaRepository <QuizResult,Integer> {
    List<QuizResult> findAllByUser(Person user);
}
