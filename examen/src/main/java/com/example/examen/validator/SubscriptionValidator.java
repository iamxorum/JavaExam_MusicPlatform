package com.example.examen.validator;

import com.example.examen.dto.request.SubscriptionRequestDTO;
import com.example.examen.exception.InvalidSubscriptionStateException;
import com.example.examen.model.BillingCycle;
import com.example.examen.model.PlanType;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionValidator {
    
    public void validateSubscriptionRequest(SubscriptionRequestDTO dto) {
        try {
            if (dto.getPlan() != null) {
                PlanType.valueOf(dto.getPlan());
            }
            if (dto.getBillingCycle() != null) {
                BillingCycle.valueOf(dto.getBillingCycle());
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidSubscriptionStateException("Invalid plan or billing cycle");
        }
    }
}