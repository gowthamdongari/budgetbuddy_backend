package com.example.budgetbuddy.modal;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users")
public class Users  implements UserDetails, OAuth2User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private  String name;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registeredAt;

    public Long id() {
        return id;
    }

    public Users setId(Long id) {
        this.id = id;
        return this;
    }

    public String email() {
        return email;
    }

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

    public String name() {
        return name;
    }

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public String passwordHash() {
        return passwordHash;
    }

    public Users setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public String provider() {
        return provider;
    }

    public Users setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String providerId() {
        return providerId;
    }

    public Users setProviderId(String providerId) {
        this.providerId = providerId;
        return this;
    }

    public LocalDateTime registeredAt() {
        return registeredAt;
    }

    public Users setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
        return this;
    }

    /**
     * @param name
     * @param <A>
     * @return
     */
    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return name;
    }
}
