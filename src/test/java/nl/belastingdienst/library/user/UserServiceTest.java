package nl.belastingdienst.library.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    User user;

    @InjectMocks
    private UserService userService = new UserService(userRepository, passwordEncoder);

    @Test
    public void testCreateUser() throws Exception {
        // Create a new user to save
        user.setEmail("testing@email.com");
        user.setName("testing name");
        user.setPassword("testingpassword");

        // Call the createUser method with the user object
        userService.createUser(user);

        // Verify that the save method is called with the correct argument
        verify(userRepository).save(user);
    }

    @Test
    public void testGetAllUserTypes() {
        // Create some test data
        List<User> users = new ArrayList<>();
        users.add(new User("testing1@email.com", "test 1", "password", Role.USER));
        users.add(new User("testing2@email.com", "test 2", "password", Role.EMPLOYEE));

        // Configure the mock UserRepository to return the test data
        when(userRepository.findAll()).thenReturn(users);

        // Call the getAllUserTypes() method and verify the result
        List<User> result = userService.getAllUserTypes();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(users.size(), result.size());
        Assertions.assertEquals(users, result);

        // Verify that the findAll() method of the mock UserRepository was called once
        verify(userRepository, times(1)).findAll();
    }
}