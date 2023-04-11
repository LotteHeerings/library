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
    public void testCreateUser() throws Exception {
        // Arrange
        UserDto newUser = new UserDto("Test@email.com", "Test", "password", "USER");
        User user = User.builder()
                .email(newUser.getEmail())
                .name(newUser.getName())
                .password("encoded_password")
                .user_role(Role.valueOf(newUser.getUser_role()))
                .build();
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.createUser(newUser);

        // Assert
        assertNotNull(result);
        assertEquals(newUser.getEmail(), result.getEmail());
        assertEquals(newUser.getName(), result.getName());
        assertEquals(Role.valueOf(newUser.getUser_role()), result.getUser_role());
        verify(userRepository, times(1)).findByEmail(newUser.getEmail());
        verify(passwordEncoder, times(1)).encode(newUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_emailAlreadyTaken() throws Exception {
        // Arrange
        UserDto newUser = new UserDto("test@email.com", "Test", "password", "USER");
        User existingUser = User.builder()
                .email(newUser.getEmail())
                .build();
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(existingUser));

        // Act and Assert
        assertThrows(Exception.class, () -> userService.createUser(newUser));
        verify(userRepository, times(1)).findByEmail(newUser.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateUser_invalidEmailFormat() {
        // Arrange
        UserDto newUser = new UserDto("invalidemailformat", "Test", "password", "USER");

        // Act and Assert
        verify(userRepository, never()).findByEmail(newUser.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateUser_invalidPasswordSize() {
        // Arrange
        UserDto newUser = new UserDto("Test@email.com", "Test", "pw", "USER");

        // Act and Assert
        verify(userRepository, never()).findByEmail(newUser.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
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
    public void testDeleteUser_existingUser() throws Exception {
        // Arrange
        String existingEmail = "john.doe@example.com";
        User existingUser = new User(existingEmail, "John Doe", "password", Role.USER);
        when(userRepository.findByEmail(existingEmail)).thenReturn(Optional.of(existingUser));

        // Act
        userService.deleteUser(existingEmail);

        // Assert
        verify(userRepository, times(1)).findByEmail(existingEmail);
        verify(userRepository, times(1)).deleteById(existingEmail);
    }

    @Test
    public void testDeleteUser_nonExistingUser() throws Exception {
        // Arrange
        String nonExistingEmail = "nonexistinguser@example.com";
        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> userService.deleteUser(nonExistingEmail));
        verify(userRepository, times(1)).findByEmail(nonExistingEmail);
        verify(userRepository, never()).deleteById(anyString());
    }

    @Test
    public void testUpdateUser_existingUser() throws Exception {
        // Arrange
        User existingUser = new User("john.doe@example.com", "John Doe", "password", Role.USER);
        UserDto updatedUser = new UserDto("jane.doe@example.com", "Jane Doe", "newpassword", "ADMIN");
        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        User result = userService.updateUser(existingUser.getEmail(), updatedUser);

        // Assert
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getUser_role(), result.getUser_role().toString());
        verify(passwordEncoder, times(1)).encode(updatedUser.getPassword());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUser_nonExistingUser() throws Exception {
        // Arrange
        String nonExistingEmail = "nonexistinguser@example.com";
        UserDto updatedUser = new UserDto("jane.doe@example.com", "Jane Doe", "newpassword", "ADMIN");
        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> userService.updateUser(nonExistingEmail, updatedUser));
        verify(userRepository, times(1)).findByEmail(nonExistingEmail);
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
    
    @Test
    public void testUpdateUser_invalidPasswordSize() throws Exception {

    }

}
