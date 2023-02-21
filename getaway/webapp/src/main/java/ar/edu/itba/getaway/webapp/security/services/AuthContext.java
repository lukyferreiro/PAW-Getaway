package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.models.UserModel;

public interface AuthContext {
    UserModel getCurrentUser();
    Long getCurrentUserId();
    boolean isVerifiedUser();
    boolean isProviderUser();
}
