package com.piksel.resources;

import com.piksel.persistence.ProductDao;
import com.piksel.persistence.SellerDao;
import com.piksel.representations.Seller;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dino on 9/5/16.
 */

@Path("/sellers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Component
public class SellersResource {

    private final SellerDao sellerDao;
    private final ProductDao productDao;

    @Inject
    public SellersResource(SellerDao sellerDao, ProductDao productDao){
        this.sellerDao = sellerDao;
        this.productDao = productDao;
    }

    @GET
    public List<Seller> getAll(){
        return this.sellerDao.findAll();
    }

    @GET
    @Path("{id}/products")
    public Seller getAllProductsFromSellers(@PathParam("id") long id){
        Seller seller = sellerDao.findOne(id);
        if(seller == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        else{
            seller.getProducts().size();
            return seller;
        }
    }

    @GET
    @Path("{id}")
    public Seller getSeller(@PathParam("id") long id){
        Seller seller = sellerDao.findOne(id);
        if(seller == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return seller;
    }
}
