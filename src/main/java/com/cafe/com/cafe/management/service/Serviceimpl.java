package com.cafe.com.cafe.management.service;


import com.cafe.com.cafe.management.Entity.Role;
import com.cafe.com.cafe.management.Entity.UserEntity;
import com.cafe.com.cafe.management.ExceptionHandler.AccessDeniedException;
import com.cafe.com.cafe.management.ExceptionHandler.EmailalreadyexistsException;
import com.cafe.com.cafe.management.JWT.CustomerUserDetails;
import com.cafe.com.cafe.management.JWT.JWTFilter;
import com.cafe.com.cafe.management.JWT.jwtservice;
import com.cafe.com.cafe.management.Passwordgenerator.PasswordGenerator;
import com.cafe.com.cafe.management.Utils.MailUtil;
import com.cafe.com.cafe.management.dto.LoginDetail;
import com.cafe.com.cafe.management.dto.Userdto;
import com.cafe.com.cafe.management.dto.Useremail;
import com.cafe.com.cafe.management.repository.userrepository;
import com.cafe.com.cafe.management.service.impl.serviceinterface;
//import com.google.gson.internal.Streams;
//import com.google.gson.internal.bind.JsonTreeReader;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cafe.com.cafe.management.Entity.Role.ADMIN;

@Service
public class Serviceimpl implements serviceinterface {

    @Autowired
    private userrepository userrepo;
    @Autowired
    private ModelMapper modelmapper;
    @Autowired
    private CustomerUserDetails customerUserDetails;
    @Autowired
    private jwtservice Jwtservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private MailUtil mailUtil;



    @Override
    public void postuser(Userdto user) {
        Optional<UserEntity> u1 = userrepo.findByEmail(user.getEmail());
        if ((!u1.isPresent())) {
            UserEntity u = modelmapper.map(user, UserEntity.class);
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            u.setPassword(encodedPassword);
            u.setRole(Role.USER);
            u.setStatus("true");
            userrepo.save(u);
        } else {
            throw new EmailalreadyexistsException("Email already exists");
        }
    }

    @Override
    public String loginservice(LoginDetail logindetails) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logindetails.getEmail(), logindetails.getPassword())
        );

        if (auth.isAuthenticated()) {
            if (customerUserDetails.user.get().getStatus().equalsIgnoreCase("true")) {
                return Jwtservice.createteToken(customerUserDetails.user.get().getEmail(), customerUserDetails.user.get().getRole());

            } else {
                return "Wait for admin approval";
            }
        }
        return "authentication is not true";
    }

    @Override
    public Userdto getuserbyemail(String email) {
        Userdto u = modelmapper.map(userrepo.findByEmail(email), Userdto.class);
        Optional<UserEntity> user = userrepo.findByEmail(email);
        return u;
    }

    @Override
    public List<Userdto> getalluser() {
        if (jwtFilter.isAdmin()) {
            List<UserEntity> userentities = userrepo.findAll();
            List<Userdto> userdtos = userentities.stream().map(u -> modelmapper.map(u, Userdto.class)).toList();
            return userdtos;
        } else {
            throw new AccessDeniedException("This Role cannot access this endpoint");
        }
    }

    @Override
    public String updateuserstatusbyadmin(Map<String, String> map) throws Exception {
        Integer id = Integer.parseInt(map.get("id"));
        Optional<UserEntity> user = userrepo.findById(id);
        if (!user.isEmpty()) {
            if (jwtFilter.isAdmin()) {
                if (user.get().getStatus().equalsIgnoreCase(map.get("status"))) {
                    return "The user already has the status as " + map.get("status") + " no need to change";
                } else {
                    UserEntity userEntity = user.get();
                    userEntity.setStatus(map.get("status"));
                    userrepo.save(userEntity);
                    List<UserEntity> listofusres = userrepo.findByRole(ADMIN);
                    List<String> emails = listofusres.stream().map(l -> l.getEmail()).collect(Collectors.toList());
                    senmailtoallAdmin(map.get("status"), user.get().getEmail(), emails);
                    return "Status is updated by ADMIN";
                }
            } else {
                throw new AccessDeniedException("Only Admin can change the status");
            }
        } else {
            throw new Exception("There is no user by this id");
        }
    }

    @Override
    public ResponseEntity<String> Checktoken() {
        Optional<UserEntity> user = userrepo.findByEmail(jwtFilter.getCurrentUser());
        if (jwtFilter.getCurrentUser().equals(user.get().getEmail())) {
            return ResponseEntity.ok("Valid Token");
        } else {
            return ResponseEntity.ok("Invalid Token");
        }
    }

    @Override
    public ResponseEntity<String> changepassword(Map<String, String> map) {
        Optional<UserEntity> user = userrepo.findByEmail(jwtFilter.getCurrentUser());

        if (passwordEncoder.matches(map.get("Oldpassword"), user.get().getPassword())) {
            String newencodedpassword = passwordEncoder.encode(map.get("Newpassword"));
            user.get().setPassword(newencodedpassword);
            userrepo.save(user.get());
            return ResponseEntity.ok("Password is changed");
        } else {
            return new ResponseEntity<>("Oldpassword is wrong", HttpStatus.BAD_REQUEST);
        }
    }



    @Override
    public ResponseEntity<String> forgotPassword(Useremail email) throws MessagingException {
       String temppassword= PasswordGenerator.generatePassword();
       String encodedpass=passwordEncoder.encode(temppassword);
       Optional<UserEntity> user=userrepo.findByEmail(email.getEmail());
       if(!Objects.isNull(user)){
           user.get().setPassword(encodedpass);
           userrepo.save(user.get());
           String subject="Temperory Password forCafe managment app";
           mailUtil.ForgotPassword(email.getEmail(),subject,temppassword);
           return ResponseEntity.ok("Temperory password is sent to registered email");
       }
       else{
           return new ResponseEntity<>("The email provided is not registered please sign up",HttpStatus.BAD_REQUEST);
       }


    }

    private void senmailtoallAdmin(String status, String user, List<String> emailByRole) {
            emailByRole.remove(jwtFilter.getCurrentUser());
            if(status.equalsIgnoreCase("true") && status!=null){
                mailUtil.sendSimplemail(jwtFilter.getCurrentUser(),"Account Approved","USER:- "+user+" is approved by ADMIN: "+jwtFilter.getCurrentUser(),emailByRole);
            }
            else {
                mailUtil.sendSimplemail(jwtFilter.getCurrentUser(),"Account Disabled","USER:- "+user+"  is disabled by  ADMIN: "+jwtFilter.getCurrentUser(),emailByRole);
            }
    }
}

