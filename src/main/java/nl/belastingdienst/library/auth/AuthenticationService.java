package nl.belastingdienst.library.auth;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.config.JwtService;
import nl.belastingdienst.library.user.Role;
import nl.belastingdienst.library.user.User;
import nl.belastingdienst.library.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Allows a user to register, save it to the database and return the token
    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new Exception("Email address is already taken.");
        }
        var user =
                User.builder()
                        .email(request.getEmail())
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .user_role(Role.USER)
                        .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        ); // Exception gets thrown when wrong
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
