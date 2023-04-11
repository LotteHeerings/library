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

    public User createUser(UserDto newUser) throws Exception{
        String email = newUser.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email address is already taken.");
        }
        var user =
                User.builder()
                        .email(newUser.getEmail())
                        .name(newUser.getName())
                        .password(passwordEncoder.encode(newUser.getPassword()))
                        .user_role(Role.valueOf(newUser.getUser_role()))
                        .build();
        return userRepository.save(user);
    }

    public List<User> getAllUserTypes() {
        return userRepository.findAll();
    }

    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }

    public User updateUser(String email, User userDetails) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new Exception("User with email " + email + " not found");
        }

        User user = userOptional.get();
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        userRepository.deleteById(email);
        return userRepository.save(user);
    }

}