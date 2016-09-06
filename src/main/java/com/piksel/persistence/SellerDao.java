package com.piksel.persistence;

import com.piksel.representations.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dino on 9/5/16.
 */
public interface SellerDao extends JpaRepository<Seller, Long> {
}
