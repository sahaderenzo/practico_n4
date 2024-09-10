package Entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

@Getter
@Builder
@Setter
@ToString
@Entity
@Table
@Audited
public class Cliente implements Serializable {
    @Serial /*Puse esta anotación por recomendaciones de java*/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int dni;

    private String nombre;
    private String apellido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_Domicilio")
    private Domicilio domicilio;
}
