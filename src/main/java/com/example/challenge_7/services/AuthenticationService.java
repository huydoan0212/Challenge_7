package com.example.challenge_7.services;


import com.example.challenge_7.dto.request.AuthenticationRequest;
import com.example.challenge_7.dto.response.AuthenticationResponse;
import com.example.challenge_7.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);


    SignedJWT verifyToken(String token) throws JOSEException, ParseException;

    String generateToken(User user);
}
