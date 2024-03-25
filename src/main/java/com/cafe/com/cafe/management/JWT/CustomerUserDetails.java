package com.cafe.com.cafe.management.JWT;

import com.cafe.com.cafe.management.Entity.UserEntity;
import com.cafe.com.cafe.management.dto.Useremail;
import com.cafe.com.cafe.management.repository.userrepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerUserDetails implements UserDetailsService {


    @Autowired
    private userrepository userrepo;
    @Autowired
    public Optional<UserEntity> user;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        user= userrepo.findByEmail(email);

        if(!Objects.isNull(user)){
            return new User(user.get().getEmail(),user.get().getPassword(),new ArrayList<>());
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }

    }


}
