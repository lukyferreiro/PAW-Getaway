package ar.edu.itba.getaway.models;

public class RoleModel {

    private Long roleId;
    private Roles roleName;

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
}
