package cl.tenpo.desafio.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean validToken;
}
