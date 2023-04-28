package nl.belastingdienst.library.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder //uses design pattern builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @Column(name = "email", nullable=false, unique=true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role user_role;

    /** Gets the user's roles in a list.
     * @return A List representing the roles of the account.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user_role.name()));
    }

    /** Gets the user's email.
     * @return A string representing the user's email.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /** Gets the user's password. Isn't necessary but here for visual clarity
     * @return A string representing the user's password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /** Gets the user's expiration details.
     * @return A boolean representing if the account is not-expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** Gets the user's locked details.
     * @return A boolean representing if the account is not-locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** Gets the user's credentials details.
     * @return A boolean representing if the credentials is not-expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** Gets the user's enabled details.
     * @return A boolean representing if the account is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
