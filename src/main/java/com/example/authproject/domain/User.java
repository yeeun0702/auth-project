package com.example.authproject.domain;

import static com.example.authproject.domain.UserTableConstants.*;

import com.example.authproject.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = TABLE_NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long userId;

    @Column(name = COLUMN_NAME, nullable = false, length = 255)
    private String username;

    @Column(name = COLUMN_NICKNAME, nullable = false, length = 255)
    private String nickname;

    @Column(name = COLUMN_EMAIL, nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = COLUMN_PASSWORD, nullable = false, length = 255)
    private String password;

    public User(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

}
