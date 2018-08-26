package com.intellias.advisory.greeting.jpa.repository;

import com.intellias.advisory.greeting.jpa.model.GreetingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingMessageRepository extends JpaRepository<GreetingMessage, Long> {
}
