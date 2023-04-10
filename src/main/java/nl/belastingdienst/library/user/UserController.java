package nl.belastingdienst.library.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/admin/users")
    public User createUser(@RequestBody User user) throws Exception {
        return userService.createUser(user);
    }


    @GetMapping("/admin/users")
    public List<User> readUsers(){
        return userService.getAllUserTypes();
    }

    @DeleteMapping("/admin/users/{email}")
    public void deleteUser(@PathVariable(value = "email") String email) {
        userService.deleteUser(email);
    }

    @PutMapping("/admin/users/{email}")
    public User updateUser(@PathVariable(value = "email") String email, @RequestBody User userDetails) {
        return userService.updateUser(email, userDetails);
    }

}