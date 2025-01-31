package com.example.examen.service;

import com.example.examen.dto.request.SubscriptionRequestDTO;
import com.example.examen.dto.response.SubscriptionResponseDTO;
import com.example.examen.exception.DuplicateSubscriptionException;
import com.example.examen.exception.InvalidSubscriptionStateException;
import com.example.examen.exception.SubscriptionNotFoundException;
import com.example.examen.mapper.SubscriptionMapper;
import com.example.examen.model.Subscription;
import com.example.examen.model.SubscriptionStatus;
import com.example.examen.repository.SubscriptionRepository;
import com.example.examen.validator.SubscriptionValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionValidator subscriptionValidator;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository, 
            SubscriptionMapper subscriptionMapper,
            SubscriptionValidator subscriptionValidator) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.subscriptionValidator = subscriptionValidator;
    }

    public SubscriptionResponseDTO addSubscription(SubscriptionRequestDTO requestDTO) {
        // Validate business rules
        subscriptionValidator.validateSubscriptionRequest(requestDTO);
        
        Subscription subscription = subscriptionMapper.toEntity(requestDTO);
        
        boolean exists = subscriptionRepository.findByUserIdAndPlanAndStartDate(
                subscription.getUserId(),
                subscription.getPlan(),
                subscription.getStartDate()
        ).isPresent();

        if (exists) {
            throw new DuplicateSubscriptionException(
                "There is already a subscription with the same user, plan and start date");
        }

        // Set default status if not provided
        if (subscription.getStatus() == null) {
            subscription.setStatus(SubscriptionStatus.PENDING);
        }

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(savedSubscription);
    }

    public void deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found with id: " + id));
        
        if (subscription.getStatus() != SubscriptionStatus.EXPIRED && 
            subscription.getStatus() != SubscriptionStatus.CANCELLED) {
            throw new InvalidSubscriptionStateException(
                "The subscription is used, it cannot be deleted.");
        }

        subscriptionRepository.delete(subscription);
    }

    // ... rest of the service code remains the same ...
}