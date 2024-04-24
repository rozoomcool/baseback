package com.itabrek.baseback.repository;

import com.itabrek.baseback.entity.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends CrudRepository<Story, Long> {
}
