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
public class Categoria implements Serializable {
    @Serial /*Puse esta anotación por recomendaciones de java*/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denominacion;

    @ManyToMany(mappedBy = "categorias")
    @Builder.Default
    private Set<Articulo> articulos = new HashSet<>();
}
