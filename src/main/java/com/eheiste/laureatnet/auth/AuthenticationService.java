package com.eheiste.laureatnet.auth;

import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.security.JwtService;
import com.eheiste.laureatnet.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        UserAccount userAccount =
                UserAccount
                        .builder()
                        .email(request.getEmail())
                        .password(
                                passwordEncoder.encode(request.getPassword())
                        )
                        .build();
//        userAccountService.createAccount(userAccount);

        var jwtToken = jwtService.generateToken(userAccount);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var account = userAccountService.loadByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(account);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
