package com.pfizer.restapi.security;

import org.restlet.Application;
import org.restlet.data.ChallengeScheme;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MemoryRealm;
import org.restlet.security.User;
import org.restlet.security.Verifier;


public class Shield {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_OWNER = "owner";
    public static final String ROLE_USER = "user";


    private Application application;

    public Shield(Application application) {
        this.application = application;
    }


    public ChallengeAuthenticator createApiGuard() {

        ChallengeAuthenticator apiGuard = new ChallengeAuthenticator(
                application.getContext(), ChallengeScheme.HTTP_BASIC, "realm");


        // - Verifier : checks authentication
        // - Enroler : to check authorization (roles)
        Verifier verifier = new CustomVerifier();
        apiGuard.setVerifier(verifier);

        return apiGuard;
    }





    /**
     * not used
     * it is given as reference for hard-coded definition of users
     * @return
     */
    public ChallengeAuthenticator createApiGuard1() {

        ChallengeAuthenticator apiGuard = new ChallengeAuthenticator(
                application.getContext(), ChallengeScheme.HTTP_BASIC, "realm");

        // Create in-memory users and roles.
        MemoryRealm realm = new MemoryRealm();

        User owner = new User("owner", "owner");
        realm.getUsers().add(owner);
        realm.map(owner, application.getRole(ROLE_OWNER));
        realm.map(owner, application.getRole(ROLE_USER));

        User admin = new User("admin", "admin");
        realm.getUsers().add(admin);
        realm.map(admin, application.getRole(ROLE_ADMIN));
        realm.map(admin, application.getRole(ROLE_OWNER));
        realm.map(admin, application.getRole(ROLE_USER));

        User user = new User("user", "user");
        realm.getUsers().add(user);
        realm.map(user, application.getRole(ROLE_USER));

        // - Verifier : checks authentication
        // - Enroler : to check authorization (roles)
        apiGuard.setVerifier(realm.getVerifier());
        apiGuard.setEnroler(realm.getEnroler());

        // Provide your own authentication checks by extending SecretVerifier or
        // LocalVerifier classes
        // Extend the Enroler class in order to assign roles for an
        // authenticated user

        return apiGuard;
    }



}
