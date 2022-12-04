package com.library.library.entity;

import com.library.library.enums.Roles;
import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
