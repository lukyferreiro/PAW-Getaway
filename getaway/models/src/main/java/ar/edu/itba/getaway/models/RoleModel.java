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
    private long roleId;

    @Column(name = "roleName", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles roleName;

    /* default */
    protected RoleModel() {
        // Just for Hibernate
    }

    public RoleModel(long roleId, Roles roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public long getRoleId() {
        return roleId;
    }
    public void setRoleId(long roleId) {
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
        return this.getRoleId() == other.getRoleId() && this.getRoleName().equals(other.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}
