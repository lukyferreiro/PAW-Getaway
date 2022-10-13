package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "userRoles")
public class UserRoleModel {

    @EmbeddedId
    private UserRoleId userRoleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private RoleModel role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserModel user;

    /* default */
    protected UserRoleModel() {
        // Just for Hibernate
    }

    public UserRoleModel(UserModel user, RoleModel role){
        this.user = user;
        this.role = role;
        this.userRoleId = new UserRoleId(role.getRoleId(), user.getUserId());
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
        this.userRoleId.setRoleId(role.getRoleId());
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
        this.userRoleId.setRoleId(user.getUserId());
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof UserRoleModel)){
            return false;
        }
        UserRoleModel other = (UserRoleModel) o;
//        return this.role.equals(other.role) && this.user.equals(other.user);
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleId);
    }


    @Embeddable
    private static class UserRoleId implements Serializable {
        @Column(nullable = false)
        private Long roleId;
        @Column(nullable = false, updatable = false)
        private Long userId;

        /* default */
        protected UserRoleId(){

        }

        public UserRoleId(Long roleId, Long userId){
            this.roleId = roleId;
            this.userId = userId;
        }

        public Long getRoleId() {
            return roleId;
        }
        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }
        public Long getUserId() {
            return userId;
        }
        public void setUserId(Long userId) {
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o){
            if(this == o){
                return true;
            }
            if (!(o instanceof UserRoleId)){
                return false;
            }
            UserRoleId other = (UserRoleId) o;
            return this.roleId.equals(other.roleId) && this.userId.equals(other.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, roleId);
        }
    }

}
