package com.example.examen.mapper;

import com.example.examen.dto.request.SubscriptionRequestDTO;
import com.example.examen.dto.response.SubscriptionResponseDTO;
import com.example.examen.model.BillingCycle;
import com.example.examen.model.PlanType;
import com.example.examen.model.Subscription;
import com.example.examen.model.SubscriptionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plan", source = "plan", qualifiedByName = "stringToPlanType")
    @Mapping(target = "billingCycle", source = "billingCycle", qualifiedByName = "stringToBillingCycle")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus", defaultValue = "PENDING")
    Subscription toEntity(SubscriptionRequestDTO dto);

    @Mapping(target = "plan", source = "plan", qualifiedByName = "planTypeToString")
    @Mapping(target = "billingCycle", source = "billingCycle", qualifiedByName = "billingCycleToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    SubscriptionResponseDTO toDto(Subscription entity);

    @Named("stringToPlanType")
    default PlanType stringToPlanType(String value) {
        return value != null ? PlanType.valueOf(value) : null;
    }

    @Named("stringToBillingCycle")
    default BillingCycle stringToBillingCycle(String value) {
        return value != null ? BillingCycle.valueOf(value) : null;
    }

    @Named("stringToStatus")
    default SubscriptionStatus stringToStatus(String value) {
        return value != null ? SubscriptionStatus.valueOf(value) : SubscriptionStatus.PENDING;
    }

    @Named("planTypeToString")
    default String planTypeToString(PlanType value) {
        return value != null ? value.name() : null;
    }

    @Named("billingCycleToString")
    default String billingCycleToString(BillingCycle value) {
        return value != null ? value.name() : null;
    }

    @Named("statusToString")
    default String statusToString(SubscriptionStatus value) {
        return value != null ? value.name() : null;
    }
}