package com.piksel.resources;

import com.piksel.annotations.PublicResource;
import com.piksel.persistence.AccountDao;
import com.piksel.representations.Account;
import com.piksel.representations.OauthRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dino on 9/7/16.
 */


@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Component
@PublicResource
public class AuthenticationsResource {
    private AccountDao accountDao;

    @Inject
    public AuthenticationsResource(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @POST
    public Account authenticate(@Valid OauthRequest oauthRequest, @Context HttpServletResponse response) {
        Account account = accountDao.findAccount(oauthRequest);
        if (account == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        response.addCookie(new Cookie("test", "test"));

        return account;
    }


}
