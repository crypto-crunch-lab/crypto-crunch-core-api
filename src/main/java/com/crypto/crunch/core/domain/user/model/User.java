package com.crypto.crunch.core.domain.user.model;

import com.crypto.crunch.core.domain.user.conf.UserConf;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = true, length = 20)
    @Enumerated(EnumType.STRING)
    private UserConf.UserLoginType loginType;
}
