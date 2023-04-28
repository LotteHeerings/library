package nl.belastingdienst.library.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//AuthenticationService is for creating new users without being authenticated or authorized.
//This UserService is used for all uses outside of that.
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /** Creates a user.
     * @param newUser A UserDto containing all the new user's details.
     * @return A User in JSON format with all the details.
     */
    public User createUser(UserDto newUser) throws Exception{
        String email = newUser.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email address is already taken.");
        }
        var user =
                User.builder()
                        .email(email)
                        .name(newUser.getName())
                        .password(passwordEncoder.encode(newUser.getPassword()))
                        .user_role(Role.valueOf(newUser.getUser_role()))
                        .build();
        return userRepository.save(user);
    }

    /** Gets all the users.
     * @return A List(User) of all the users.
     */
    public List<User> getAllUserTypes() {
        return userRepository.findAll();
    }

    /** Deletes a user.
     * @param email A String containing the user's email.
     */
    public void deleteUser(String email) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new Exception("User with email " + email + " not found");
        }
        userRepository.deleteById(email);
    }

    /** Updates the user's details.
     * @param email A String containing the user's email.
     * @param userDetails A UserDto containing all the update details.
     * @return A User in JSON format with all the new/updated details.
     */
    public User updateUser(String email, UserDto userDetails) throws Exception {
        if (userDetails.getPassword().length() < 5) {
            throw new Exception("Password too short");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new Exception("User with email " + email + " not found");
        }

        User user = userOptional.get();
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setUser_role(Role.valueOf(userDetails.getUser_role()));

        return userRepository.save(user);
    }

}