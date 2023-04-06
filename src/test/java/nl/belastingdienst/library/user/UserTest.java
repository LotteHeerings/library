package nl.belastingdienst.library.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testUser() {
        String email = "test@email.com";
        String name = "testname";
        String password = "testpassword";

        User user = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .user_role(Role.USER)
                .build();

        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(Role.USER, user.getUser_role());
    }
}
