package com.example.examen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Subscription request data")
public class SubscriptionRequestDTO {
    @Schema(
        description = "User ID",
        example = "1",
        required = true
    )
    @NotNull(message = "userId is mandatory")
    @Positive(message = "userId must be a positive number")
    private Long userId;

    @Schema(
        description = "Subscription plan type",
        example = "PREMIUM",
        allowableValues = {"FREE", "PREMIUM", "FAMILY"},
        required = true
    )
    @NotNull(message = "plan is mandatory")
    @Pattern(regexp = "^(FREE|PREMIUM|FAMILY)$", 
            message = "plan must be one of: FREE, PREMIUM, FAMILY")
    private String plan;

    @Schema(
        description = "Billing cycle",
        example = "MONTHLY",
        allowableValues = {"MONTHLY", "YEARLY"},
        required = true
    )
    @NotNull(message = "billingCycle is mandatory")
    @Pattern(regexp = "^(MONTHLY|YEARLY)$", 
            message = "billingCycle must be either MONTHLY or YEARLY")
    private String billingCycle;

    @Schema(
        description = "Subscription price",
        example = "9.99",
        required = true
    )
    @NotNull(message = "price is mandatory")
    @Positive(message = "price must be a positive number")
    private Double price;

    @Schema(
        description = "Subscription start date",
        example = "2024-03-20",
        required = true
    )
    @NotNull(message = "startDate is mandatory")
    private LocalDate startDate;

    @Schema(
        description = "Subscription end date",
        example = "2025-03-20"
    )
    private LocalDate endDate;

    @Schema(
        description = "Subscription status",
        example = "PENDING",
        allowableValues = {"PENDING", "ACTIVE", "EXPIRED", "CANCELLED"}
    )
    @Pattern(regexp = "^(PENDING|ACTIVE|EXPIRED|CANCELLED)?$", 
            message = "status must be one of: PENDING, ACTIVE, EXPIRED, CANCELLED")
    private String status;

    // Getters and Setters
    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
    
    public String getBillingCycle() { return billingCycle; }
    public void setBillingCycle(String billingCycle) { this.billingCycle = billingCycle; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}