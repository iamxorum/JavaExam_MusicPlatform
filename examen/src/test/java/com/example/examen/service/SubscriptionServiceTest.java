package com.example.examen.service;

import com.example.examen.dto.request.SubscriptionRequestDTO;
import com.example.examen.dto.response.SubscriptionResponseDTO;
import com.example.examen.exception.SubscriptionNotFoundException;
import com.example.examen.exception.DuplicateSubscriptionException;
import com.example.examen.exception.InvalidSubscriptionStateException;
import com.example.examen.mapper.SubscriptionMapper;
import com.example.examen.model.Subscription;
import com.example.examen.model.SubscriptionStatus;
import com.example.examen.repository.SubscriptionRepository;
import com.example.examen.validator.SubscriptionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository repository;

    @Mock
    private SubscriptionMapper mapper;

    @Spy
    private SubscriptionValidator validator = new SubscriptionValidator();

    @InjectMocks
    private SubscriptionService service;

    @Test
    void addSubscription_Success() {
        // Arrange
        SubscriptionRequestDTO request = new SubscriptionRequestDTO();
        request.setUserId(1L);
        request.setPlan("PREMIUM");
        request.setBillingCycle("MONTHLY");
        request.setStartDate(LocalDate.now());

        Subscription subscription = new Subscription();
        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setId(1L);

        when(mapper.toEntity(any())).thenReturn(subscription);
        when(repository.save(any())).thenReturn(subscription);
        when(mapper.toDto(any())).thenReturn(response);

        // Act
        SubscriptionResponseDTO result = service.addSubscription(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(any());
    }

    @Test
    void deleteSubscription_Success() {
        // Arrange
        Long id = 1L;
        Subscription subscription = new Subscription();
        subscription.setStatus(SubscriptionStatus.EXPIRED);
        when(repository.findById(id)).thenReturn(Optional.of(subscription));

        // Act
        service.deleteSubscription(id);

        // Assert
        verify(repository).delete(subscription);
    }

    @Test
    void deleteSubscription_NotFound() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SubscriptionNotFoundException.class, () -> service.deleteSubscription(id));
        verify(repository, never()).delete(any());
    }

    @Test
    void addSubscription_DuplicateSubscription() {
        // Arrange
        SubscriptionRequestDTO request = new SubscriptionRequestDTO();
        request.setUserId(1L);
        request.setPlan("PREMIUM");
        request.setStartDate(LocalDate.now());
        
        Subscription subscription = new Subscription();
        when(mapper.toEntity(any())).thenReturn(subscription);
        when(repository.findByUserIdAndPlanAndStartDate(any(), any(), any()))
            .thenReturn(Optional.of(new Subscription()));

        // Act & Assert
        assertThrows(DuplicateSubscriptionException.class, () -> service.addSubscription(request));
        verify(repository, never()).save(any());
    }

    @Test
    void deleteSubscription_ExpiredStatus_Success() {
        // Arrange
        Long id = 1L;
        Subscription subscription = new Subscription();
        subscription.setStatus(SubscriptionStatus.EXPIRED);
        when(repository.findById(id)).thenReturn(Optional.of(subscription));

        // Act
        service.deleteSubscription(id);

        // Assert
        verify(repository).delete(subscription);
    }

    @Test
    void deleteSubscription_CancelledStatus_Success() {
        // Arrange
        Long id = 1L;
        Subscription subscription = new Subscription();
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        when(repository.findById(id)).thenReturn(Optional.of(subscription));

        // Act
        service.deleteSubscription(id);

        // Assert
        verify(repository).delete(subscription);
    }

    @Test
    void deleteSubscription_ActiveStatus_ThrowsException() {
        // Arrange
        Long id = 1L;
        Subscription subscription = new Subscription();
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        when(repository.findById(id)).thenReturn(Optional.of(subscription));

        // Act & Assert
        InvalidSubscriptionStateException exception = assertThrows(
            InvalidSubscriptionStateException.class, 
            () -> service.deleteSubscription(id)
        );
        assertEquals("The subscription is used, it cannot be deleted.", exception.getMessage());
        verify(repository, never()).delete(any());
    }

    @Test
    void addSubscription_WithNullStatus_SetsPendingStatus() {
        // Arrange
        SubscriptionRequestDTO request = new SubscriptionRequestDTO();
        request.setUserId(1L);
        request.setPlan("PREMIUM");
        request.setBillingCycle("MONTHLY");
        request.setStartDate(LocalDate.now());
        request.setStatus(null);

        Subscription subscription = new Subscription();
        when(mapper.toEntity(any())).thenReturn(subscription);
        when(repository.findByUserIdAndPlanAndStartDate(any(), any(), any()))
            .thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(subscription);

        // Act
        service.addSubscription(request);

        // Assert
        verify(repository).save(argThat(s -> s.getStatus() == SubscriptionStatus.PENDING));
    }

    @Test
    void deleteSubscription_PendingStatus_ThrowsException() {
        // Arrange
        Long id = 1L;
        Subscription subscription = new Subscription();
        subscription.setStatus(SubscriptionStatus.PENDING);
        when(repository.findById(id)).thenReturn(Optional.of(subscription));

        // Act & Assert
        assertThrows(InvalidSubscriptionStateException.class, 
            () -> service.deleteSubscription(id));
        verify(repository, never()).delete(any());
    }

    @Test
    void addSubscription_ValidatesRequest() {
        // Arrange
        SubscriptionRequestDTO request = new SubscriptionRequestDTO();
        // Don't set required fields

        // Act & Assert
        assertThrows(Exception.class, () -> service.addSubscription(request));
        verify(validator).validateSubscriptionRequest(request);
        verify(repository, never()).save(any());
    }

    @Test
    void addSubscription_WithCustomStatus_PreservesStatus() {
        // Arrange
        SubscriptionRequestDTO request = new SubscriptionRequestDTO();
        request.setUserId(1L);
        request.setPlan("PREMIUM");
        request.setBillingCycle("MONTHLY");
        request.setStartDate(LocalDate.now());
        request.setStatus("ACTIVE");

        Subscription subscription = new Subscription();
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        when(mapper.toEntity(any())).thenReturn(subscription);
        when(repository.save(any())).thenReturn(subscription);

        // Act
        service.addSubscription(request);

        // Assert
        verify(repository).save(argThat(s -> s.getStatus() == SubscriptionStatus.ACTIVE));
    }
} 