package nl.belastingdienst.library.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//AuthenticationService is for creating new users without being authenticated or authorized.
//This UserService is used for all uses outside of that.
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) throws Exception{
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email address is already taken.");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUserTypes() {
        return userRepository.findAll();
    }

    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }

    public User updateUser(String email, User userDetails) {
        User user = userRepository.findByEmail(email).get();
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        userRepository.deleteById(email);
        return userRepository.save(user);
    }

}