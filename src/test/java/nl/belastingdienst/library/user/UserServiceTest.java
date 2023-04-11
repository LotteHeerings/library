package nl.belastingdienst.library.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() throws Exception {
        var user =
                User.builder()
                        .email("email")
                        .name("name")
                        .password(passwordEncoder.encode("password"))
                        .user_role(Role.USER)
                        .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertEquals(user, savedUser);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_duplicateEmail() {

        var user =
                User.builder()
                        .email("email")
                        .name("name")
                        .password(passwordEncoder.encode("password"))
                        .user_role(Role.USER)
                        .build();

        //userRepository.save(user);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(Exception.class, () -> {
            userService.createUser(user);
        });

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, never()).save(user);
    }

    @Test
    void getAllUserTypes() {
        var user1 =
                User.builder()
                        .email("email1")
                        .name("name1")
                        .password(passwordEncoder.encode("password1"))
                        .user_role(Role.USER)
                        .build();
        var user2 =
                User.builder()
                        .email("email2")
                        .name("name2")
                        .password(passwordEncoder.encode("password2"))
                        .user_role(Role.USER)
                        .build();
        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUserTypes();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteUser() {
        String email = "email";

        userService.deleteUser(email);

        verify(userRepository, times(1)).deleteById(email);
    }

    @Test
    void updateUser() throws Exception {
        // Create the existing user
        var existingUser =
                User.builder()
                        .email("email1")
                        .name("name1")
                        .password(passwordEncoder.encode("password1"))
                        .user_role(Role.USER)
                        .build();

        // Create the updated user
        var updatedUser =
                User.builder()
                        .email("email2")
                        .name("name2")
                        .password(passwordEncoder.encode("password2"))
                        .user_role(Role.USER)
                        .build();

        // Mock the repository to return the existing user when searching by email
        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        // Mock the repository to return the existing user when saving
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the update method
        User savedUser = userService.updateUser(existingUser.getEmail(), updatedUser);

        // Verify that the save method was called with the updated user
        verify(userRepository, times(1)).save(updatedUser);

        // Assert that the returned user is the same as the updated user
        assertEquals(updatedUser.getEmail(), savedUser.getEmail());
        assertEquals(updatedUser.getName(), savedUser.getName());
    }

    @Test
    public void testUpdateWhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        User userDetails = new User();
        userDetails.setEmail("newemail@example.com");
        userDetails.setName("New Name");
        userDetails.setPassword("newpassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            userService.updateUser(email, userDetails);
        });
        String expectedMessage = "User with email " + email + " not found";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));
    }

}
