package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.interfaces.persistence.UserDao;
import ar.edu.itba.getaway.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    // TODO
    // Ahora las queries estan escritas en JQL (JPA Query Language,
    // que esta basado en HQL, Hibernate Query Language).
    // En vez de nombres de tabla usamos nombres de entidades/clases, y en
    // lugar de columnas usamos nombres de propiedades.

//    @Override
//    public User create(final String username, final String password) {
//        final User user = new User(username, password);
//        em.persist(user);
//        return user;
//    }
//    @Override
//    public User getByUsername(final String username) {
//        final TypedQuery<User> query = em.createQuery("FROM User WHERE username = :username", User.class);
//        query.setParameter("username", username);
//        final List<User> list = query.getResultList();
//        return list.isEmpty() ? null : list.get(0);
//        // OTRA FORMA
//        return query.getResultList().stream().findFirst();
//    }

//    }
//    @Override
//    public User findById(long id) {
//        return em.find(User.class, id);
//    }

    //Si es optional
//    @Override
//    public Optional<User> findById(long id) {
//        return Optional.ofNullable(em.find(User.class, id));
//    }

//    @Override
//    public void changePassword(String email, String password){
//        Optional<User> maybeUser = findByEmail(email);
//        if(maybeUser.isEmpty){
//            return
//        }
//        User user = maybeUser.get();
//        user.setPassword(password);
//        em.persist(user);
//    }

    @Override
    public Optional<UserModel> getUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> getUserByExperienceId(Long experienceId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> getUserByReviewId(Long reviewId) {
        return Optional.empty();
    }

    @Override
    public UserModel createUser(String password, String name, String surname, String email, Collection<Roles> roles, Long imageId) throws DuplicateUserException {
        return null;
    }

    @Override
    public Collection<Roles> getUserRoles(Long userId) {
        return null;
    }

    @Override
    public Collection<RoleModel> getUserRolesModels(Long userId) {
        return null;
    }

    @Override
    public Optional<RoleModel> getRoleByName(Roles role) {
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> updateRoles(Long userId, Roles oldVal, Roles newVal) {
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> updatePassword(Long userId, String password) {
        return Optional.empty();
    }

    @Override
    public void updateUserInfo(Long userId, UserInfo userInfo) {

    }

    @Override
    public void addRole(Long userId, Roles newRole) {

    }
}
