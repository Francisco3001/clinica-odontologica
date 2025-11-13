package com.clinicaODontologica.UP.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String username;
    @Column
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private UsuarioRole usuarioRole;

    public Usuario(String nombre, String username, String password, String email, UsuarioRole usuarioRole) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.email = email;
        this.usuarioRole = usuarioRole;
    }

    public Usuario() {
    }

    @Override
    public String getUsername() {
        return email;   // <-- Spring Security usa este email como “username”
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority(usuarioRole.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
