package com.prgm.orderApiServer.domain.entity;

import com.prgm.orderApiServer.domain.entity.base.JpaBaseTimeEntity;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
public class User extends JpaBaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id @Column(name = "user_id")
    private Long seq;

    @NotNull
    private String name;

    @Email
    @NotNull
    private com.prgm.orderApiServer.domain.entity.Email email;

    @Column(name = "passwd")
    @NotNull
    private String password;

    @Column(name = "login_count")
    private int loginCount;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Builder
    public User(String name, com.prgm.orderApiServer.domain.entity.Email email, String password) {
        this(name, email, password, 0);
    }

    @Builder
    public User(String name, com.prgm.orderApiServer.domain.entity.Email email, String password, int loginCount) {
        checkArgument(isNotEmpty(name), "name must be provided");
        checkArgument(
                name.length() >= 1 && name.length() <= 10,
                "name length must be between 1 and 10 characters"
        );
        checkNotNull(email, "email must be provided");
        checkNotNull(password, "password must be provided");

        this.name = name;
        this.email = email;
        this.password = password;
        this.loginCount = loginCount;
        this.lastLoginAt = lastLoginAt;
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password)) {
            throw new IllegalArgumentException("Bad credential");
        }
    }

    public void afterLoginSuccess() {
        loginCount++;
        lastLoginAt = now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(seq, user.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("name", name)
                .append("email", email)
                .append("password", "[PROTECTED]")
                .append("loginCount", loginCount)
                .append("lastLoginAt", lastLoginAt)
                .toString();
    }

}