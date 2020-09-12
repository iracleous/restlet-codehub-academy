package com.pfizer.restapi.security;

import com.pfizer.restapi.security.dao.ApplicationUser;
import com.pfizer.restapi.security.dao.ApplicationUserPersistence;
import org.restlet.Request;
import org.restlet.security.Role;
import org.restlet.security.SecretVerifier;

import java.sql.SQLException;

/**
 *
 create  table UserTable(
 username nvarchar(50),
 password nvarchar(50),
 role nvarchar(50)
 );

 insert into UserTable
 (username, password, role)
 values('dimitris', 'dimitris', 'user'),
 ('aristotelis', 'aristotelis', 'user')
 ;


 select * from usertable;




 *
 */

public class CustomVerifier extends SecretVerifier {

    public int verify(String identifier, char[] secret)
            throws IllegalArgumentException {
        ApplicationUserPersistence applicationUserPersistence = ApplicationUserPersistence.getApplicationUserPersistence();
        ApplicationUser user = null;
        try {
            user = applicationUserPersistence.findById(identifier);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //user contains both user hints and roles
        if (user!=null
                && compare(user.getPassword().toCharArray(), secret)) {
            Request request = Request.getCurrent();
            request.getClientInfo().getRoles().add(new Role(user.getRole().getRoleName()));
            return SecretVerifier.RESULT_VALID;
        } else {
            return SecretVerifier.RESULT_INVALID;
        }
    }
}