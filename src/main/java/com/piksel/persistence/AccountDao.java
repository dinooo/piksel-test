package com.piksel.persistence;

import com.piksel.representations.Account;
import com.piksel.representations.OauthRequest;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountDao extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.username = :#{#request.username} AND a.password = :#{#request.password}")
    Account findAccount(@Param("request") OauthRequest request);

}
