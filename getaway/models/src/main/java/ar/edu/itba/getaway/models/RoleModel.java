package ar.edu.itba.getaway.models;

public class RoleModel {

    private final Long roleId;
    private final Roles roleName;

    public RoleModel(Long roleId, Roles roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }
    public Roles getRoleName() {
        return roleName;
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
}
