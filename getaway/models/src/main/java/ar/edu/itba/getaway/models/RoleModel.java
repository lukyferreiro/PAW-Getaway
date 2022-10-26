package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_roleId_seq")
    @SequenceGenerator(sequenceName = "roles_roleId_seq", name = "roles_roleId_seq", allocationSize = 1)
    @Column(name = "roleId")
    private Long roleId;

    @Column(name = "roleName", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles roleName;

    /* default */
    protected RoleModel() {
        // Just for Hibernate
    }

    public RoleModel(Long roleId, Roles roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    public Roles getRoleName() {
        return roleName;
    }
    public void setRoleName(Roles roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof RoleModel)){
            return false;
        }
        RoleModel other = (RoleModel) o;
        return this.roleId.equals(other.roleId) && this.roleName.equals(other.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}
