package Entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@Setter
@ToString
@Entity
@Table
@Audited
public class Factura implements Serializable {
    @Serial /*Puse esta anotación por recomendaciones de java*/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fecha;
    private int numero;
    private int total;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_Cliente")
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FK_Factura")
    @Builder.Default
    private Set<DetalleFactura> detalles = new HashSet<>();
}
