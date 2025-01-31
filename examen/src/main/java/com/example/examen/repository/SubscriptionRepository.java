package com.example.examen.repository;

import com.example.examen.model.Subscription;
import com.example.examen.model.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserIdAndPlanAndStartDate(Long userId, PlanType plan, LocalDate startDate);
}