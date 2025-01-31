package com.example.examen.controller;

import com.example.examen.dto.request.SubscriptionRequestDTO;
import com.example.examen.dto.response.SubscriptionResponseDTO;
import com.example.examen.dto.error.ErrorResponse;
import com.example.examen.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscription Management", description = "Endpoints for managing music streaming subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(
        summary = "Create a new subscription",
        description = "Creates a new subscription with the provided details. Returns the created subscription."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Subscription created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = SubscriptionResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Subscription already exists",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> addSubscription(
            @Parameter(
                description = "Subscription details",
                required = true,
                schema = @Schema(implementation = SubscriptionRequestDTO.class)
            )
            @Valid @RequestBody SubscriptionRequestDTO requestDTO) {
        return ResponseEntity.ok(subscriptionService.addSubscription(requestDTO));
    }

    @Operation(
        summary = "Delete a subscription",
        description = "Deletes a subscription by its ID. Only EXPIRED or CANCELLED subscriptions can be deleted."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Subscription deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Subscription not found",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Subscription cannot be deleted due to its status",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(
            @Parameter(description = "Subscription ID", required = true)
            @PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok().build();
    }
}