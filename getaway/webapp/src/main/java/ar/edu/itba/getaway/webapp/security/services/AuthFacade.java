package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.models.UserModel;

public interface AuthFacade {
    UserModel getCurrentUser();
    Long getCurrentUserId();
    boolean isVerifiedUser();
    boolean isProviderUser();
}
