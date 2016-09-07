package com.piksel.annotations;

import java.lang.annotation.*;

/***
 * Used for marking a class accessible to a non-authorized user for
 * @GET, @PUT, @POST, and @DELETE annotations
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface PublicResource {

}