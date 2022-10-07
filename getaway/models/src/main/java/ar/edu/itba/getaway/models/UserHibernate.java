package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.List;

@Entity
// El uso de @Table no es obligatorio, salvo que querramos evitar algún default.
// En este caso, queremos que se siga usando la tabla users en lugar de la que generaría por default User
@Table(name = "users")
public class UserHibernate {

    @Id
    //Por defecto, Hibernate en PostgreSQL crea una única secuencia, llamada hibernate_sequence y la utiliza para todas las entidades.
    //Nosotros ya teníamos una secuencia users_userid_seq para los ids de esta tabla.
    //Deseamos seguir usandola, por tanto debemos agregar el atributo generator a @GeneratedValue y agregar
    // la annotation @SequenceGenerator indicandole cuál es el generator que queremos usar.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
    // @Column responde a que no queremos usar el nombre default, sino que tenga el @Id alcanzaría.
    @Column(name = "userid")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    //Mapeamos las relaciones de Issue en forma inversa
    //fetchType que nos permite jugar con cuando y como queremos obtener los objetos que participan de la
    //relación. JPA nos da un enorme poder para simplificar el código de la aplicación, pero no debemos caer
    //nunca en desentendernos de la base de datos, y tenemos que auditar cuidadosamente que queries estamos
    //haciendo al impactar cambios a nuestros modelos / mapeos. Incluso cuando localmente todo funciona
    //bien, al salir a producción, con miles / millones de registros, este tipo de problemas pueden
    //rápidamente deteriorar la performance de la aplicación y hasta hacer caer el servicio.
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "assignedTo")
    private List<Issue> assignedIssues;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "createdBy")
    private List<Issue> createdIssues;

    //Hibernate requiere un constructor default para poder crear instancias
    //de nuestros modelos y luego por reflection popular las properties.
    UserHibernate() {
        // Just for Hibernate
    }

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}