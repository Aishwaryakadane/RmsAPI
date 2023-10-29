package com.AK.Rms.Repository;

import com.AK.Rms.Model.AuthenticationToken;
import com.AK.Rms.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationTokenRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByUser(User user);

    AuthenticationToken findFirstByTokenValue(String authTokenValue);



}