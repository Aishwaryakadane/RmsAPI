package com.AK.Rms.Service;


import com.AK.Rms.Model.AuthenticationToken;
import com.AK.Rms.Repository.IAuthenticationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationTokenRepo authTokenRepo;

    public void createToken(AuthenticationToken token) {
        authTokenRepo.save(token);
    }

    public boolean authenticate(String email, String authTokenValue)
    {
        AuthenticationToken authToken = authTokenRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(email);
    }

}
