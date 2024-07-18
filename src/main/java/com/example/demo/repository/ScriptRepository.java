package com.example.demo.repository;

import com.example.demo.model.Script;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByStatus(Script.ScriptStatus status, Pageable pageable);
}
