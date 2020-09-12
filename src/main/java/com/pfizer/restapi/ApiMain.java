package com.pfizer.restapi;

import com.pfizer.restapi.router.CustomRouter;
import com.pfizer.restapi.security.Shield;
import com.pfizer.restapi.security.cors.CorsFilter;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.Role;

import java.util.logging.Logger;

public class ApiMain extends Application {

    public static final Logger LOGGER = Engine.getLogger(ApiMain.class);

    public static void main(String[] args) throws Exception{
        LOGGER.info("Contacts application starting...");
//  HibernateStart.main();


        Component c = new Component();
        c.getServers().add(Protocol.HTTP, 9000);
        c.getDefaultHost().attach("/v1", new ApiMain());
        c.start();

        LOGGER.info("Sample Web API started");
        LOGGER.info("URL: http://localhost:9000/v1/product");

    }
    public ApiMain() {

        setName("WebAPITutorial");
        setDescription("Full Web API tutorial");

        getRoles().add(new Role(this, Shield.ROLE_ADMIN));
        getRoles().add(new Role(this, Shield.ROLE_OWNER));
        getRoles().add(new Role(this, Shield.ROLE_USER));

    }
    @Override
    public Restlet createInboundRoot() {

        CustomRouter customRouter = new CustomRouter(this);
        Shield shield = new Shield(this);

        Router publicRouter = customRouter.publicResources();
        ChallengeAuthenticator apiGuard = shield.createApiGuard();
        // Create the api router, protected by a guard

        Router apiRouter = customRouter.createApiRouter();
        apiGuard.setNext(apiRouter);

        publicRouter.attachDefault(apiGuard);

        // return publicRouter;

        CorsFilter corsFilter = new CorsFilter(this);
        return corsFilter.createCorsFilter(publicRouter);
    }

}
