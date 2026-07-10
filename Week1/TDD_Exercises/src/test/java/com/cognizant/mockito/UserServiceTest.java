package com.cognizant.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // Exercise 1: Mocking
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1L, "Alice", "alice@example.com");
    }

    // Exercise 1: Mocking and Stubbing
    @Test
    void testGetUser() {
        // Arrange: Stub the behavior of the mocked repository
        when(userRepository.findUserById(1L)).thenReturn(sampleUser);

        // Act
        User result = userService.getUser(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
    }

    // Exercise 2: Verifying Interactions
    @Test
    void testRegisterUser() {
        // Act
        userService.registerUser(sampleUser);

        // Assert: Verify that saveUser was called exactly once with sampleUser
        verify(userRepository, times(1)).saveUser(sampleUser);
    }

    @Test
    void testRemoveUser() {
        // Act
        userService.removeUser(1L);

        // Assert: Verify that deleteUser was called with the correct ID
        verify(userRepository).deleteUser(1L);
        // Verify that no other methods were called on the repository
        verifyNoMoreInteractions(userRepository);
    }
}
