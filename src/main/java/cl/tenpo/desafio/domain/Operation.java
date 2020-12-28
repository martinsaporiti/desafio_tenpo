package cl.tenpo.desafio.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Entity
@Table(name = "operation")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer num1;
    private Integer num2;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

}
