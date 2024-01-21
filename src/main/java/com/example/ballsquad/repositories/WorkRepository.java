package com.example.ballsquad.repositories;

import com.example.ballsquad.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work,Long> {
}
