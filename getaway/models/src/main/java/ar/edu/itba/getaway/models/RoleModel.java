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
}
