package org.example;

import Entidades.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin(); /*Iniciamos transacción*/

            /*Creamos una factura para luego ir llenando los datos*/
            Factura factura1 = Factura.builder()
                    .fecha("5/9/2024")
                    .detalles(new HashSet<>())
                    .numero(1)
                    .build();

            /*Creamos un domicilio para el cliente*/
            Domicilio dom = Domicilio.builder()
                    .nombreCalle("Calle Falsa")
                    .numero(123) /*De los simpsons*/
                    .build();

            /*Creamos un cliente*/
            Cliente yo = Cliente.builder()
                    .nombre("Renzo")
                    .apellido("Sahade")
                    .dni(12345678)
                    .domicilio(dom)
                    .build();

            /*Asignamos el cliente a la factura*/
            factura1.setCliente(yo);

            /*Creamos distintas categorias*/
            Categoria perecederos = Categoria.builder()
                    .denominacion("Perecederos")
                    .build();

            Categoria lacteos = Categoria.builder()
                    .denominacion("Lacteos")
                    .build();

            Categoria limpieza = Categoria.builder()
                    .denominacion("Limpieza")
                    .build();

            /*Creamos distintos articulos*/
            Articulo articulo1 = Articulo.builder()
                    .denominacion("Yogurt Ser Frutilla")
                    .cantidad(100)
                    .precio(200)
                    .categorias(new HashSet<>(Set.of(perecederos, lacteos)))
                    .build();

            Articulo articulo2 = Articulo.builder()
                    .denominacion("Detergente Magistral")
                    .cantidad(180)
                    .precio(1000)
                    .categorias(new HashSet<>(Set.of(limpieza)))
                    .build();

            /*Agregamos los articulos a las categorias*/
            lacteos.getArticulos().add(articulo1);
            perecederos.getArticulos().add(articulo1);
            limpieza.getArticulos().add(articulo2);

            /*Creamos detalles de facturas*/
            DetalleFactura detalle1 = DetalleFactura.builder()
                    .articulo(articulo1)
                    .cantidad(2)
                    .subtotal(400)
                    .build();

            DetalleFactura detalle2 = DetalleFactura.builder()
                    .articulo(articulo2)
                    .cantidad(1)
                    .subtotal(1000)
                    .build();

            /*Agregando el detalle a la factura*/
            factura1.getDetalles().add(detalle1);
            factura1.getDetalles().add(detalle2);

            /*Total de la factura*/
            factura1.setTotal(1400);

            /*Persistimos la factura para que se ejecute la cascada*/
            em.persist(factura1);


            em.flush();
            em.getTransaction().commit();

            /*Ahora modifico para tener variaciones*/
            em.getTransaction().begin();
            factura1.setTotal(2000);
            em.merge(factura1);
            em.flush();
            em.getTransaction().commit();

            /*Ahora elimino la factura 1 para tener una acción de cada tipo*/
            em.getTransaction().begin();
            em.remove(factura1);
            em.flush();
            em.getTransaction().commit();
        }

        catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase Factura");
        }
        em.close(); /*Cerramos el EntityMananger y el Factory*/
        emf.close();
    }
}
