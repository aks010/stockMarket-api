package com.stockmarket.company.auth;

import com.stockmarket.company.entity.MyUser;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private MyUserRepository myUserRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)

    public ResponseEntity<MyUser> saveUser(@RequestBody MyUser myUser, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Please submit with valid entries!");
        }
        MyUser newMyUser = userDetailsService.save(myUser);
        return new ResponseEntity<MyUser>(newMyUser, null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,headers = "Accept=application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        System.out.println("AUTH COTROLLER AUTHENTICATE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        Optional<MyUser> queryMyUser = myUserRepository.findByUsername(userDetails.getUsername());
        if(!queryMyUser.isPresent()) {
            throw new BadRequestException("User does not exist!!");
        }
        MyUser myUser = queryMyUser.get();
        JwtResponse response = new JwtResponse(token, myUser.getRole());
//        System.out.println()
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
