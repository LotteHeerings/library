package nl.belastingdienst.library.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public User createUser(@RequestBody UserDto user) throws Exception {
        return userService.createUser(user);
    }

    @GetMapping("/users")
    public List<User> readUsers(){
        return userService.getAllUserTypes();
    }

    @DeleteMapping("/users/{email}")
    public void deleteUser(@PathVariable(value = "email") String email) throws Exception {
        userService.deleteUser(email);
    }

    @PutMapping("/users/{email}")
    public User updateUser(@PathVariable(value = "email") String email, @RequestBody UserDto userDetails) throws Exception{
        return userService.updateUser(email, userDetails);
    }

}