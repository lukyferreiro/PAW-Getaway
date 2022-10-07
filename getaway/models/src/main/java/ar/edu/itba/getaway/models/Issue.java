package ar.edu.itba.getaway.models;

import javax.persistence.*;


//Issue que representa un issue que debe ser corregido, y lo mapeamos
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_id_seq")
    @SequenceGenerator(sequenceName = "issue_id_seq", name = "issue_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 1024, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserHibernate createdBy;

    //Definimos que la relación con User es opcional para la asignación, ya
    //que no es una relación fuerte (pueden existir issues que no fueron
    //asignados a nadie); pero es requerida para la creación (alguien tuvo
    //que haberlos creado).
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private UserHibernate assignedTo;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    /* default */
    public Issue() {
        // Just for Hibernate

    }
    public Issue(final String description, final UserHibernate createdBy, final Priority priority) {
        super();
        this.description = description;
        this.createdBy = createdBy;
        this.priority = priority;
    }

    public UserHibernate getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(final UserHibernate assignedTo) {
        this.assignedTo = assignedTo;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(final Priority priority) {
        this.priority = priority;
    }
    public void setCreatedBy(UserHibernate createdBy) {
        this.createdBy = createdBy;
    }
    public UserHibernate getCreatedBy() {
        return createdBy;
    }
    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "IssueTPE [id=" + id + ", description=" + description + ", createdBy=" +
                createdBy.getUsername() + ", assignedTo=" + (assignedTo == null ?
                "null" : assignedTo.getUsername()) + ", priority=" + priority + "]";
    }
}