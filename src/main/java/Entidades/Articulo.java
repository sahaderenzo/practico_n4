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
@ToString(exclude = "categorias") /*Tuve que agregar esto porque se me generaba una recursividad infinita*/
@Entity
@Table
@Audited
public class Articulo implements Serializable {
    @Serial /*Puse esta anotación por recomendaciones de java*/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private String denominacion;
    private int precio;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "Articulo_Categoria",
            joinColumns = @JoinColumn(name = "FK_Articulo"),
            inverseJoinColumns = @JoinColumn(name = "FK_Categoria")
    )
    @Builder.Default
    private Set<Categoria> categorias = new HashSet<>();
}
