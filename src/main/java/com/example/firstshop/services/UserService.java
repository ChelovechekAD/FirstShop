package com.example.firstshop.services;

import com.example.firstshop.database.Role;
import com.example.firstshop.database.User;
import com.example.firstshop.dto.request.*;
import com.example.firstshop.dto.response.*;
import com.example.firstshop.exception.ListExceptions;
import com.example.firstshop.repository.RoleRepository;
import com.example.firstshop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{
    @PersistenceContext
    private EntityManager em;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authManager;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;



    /*
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);

            if (user == null){
                throw new UsernameNotFoundException("User not found!");
            }
            return user;
        }
    */
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public RegistrationResponse userRegistration(UserRegistrationRequest userDetails){
        if(userRepository.existsUserByEmail(userDetails.getEmail())){
            throw new ListExceptions.LoginAlreadyUsed("Данная почта уже занята!");
        }
        if(!userDetails.getPassword().equals(userDetails.getPasswordConfirm())){
            throw new ListExceptions.InvalidInputException("Пароли не совпадают!");
        }
        var user = User.builder()
                .username(userDetails.getUsername())
                .password(bCryptPasswordEncoder.encode(userDetails.getPassword()))
                .email(userDetails.getEmail())
                .roles(Collections.singleton(Role.of(1L,"ROLE_USER")))
                .blocked(false)
                .build();
        userRepository.save(user);
        return RegistrationResponse.builder()
                .email(user.getEmail())
                .build();
    }

    public LoginResponse userLogin(UserLoginRequest userDetails){
        var user = userRepository.findUserByEmail(userDetails.getEmail())
                .orElseThrow(() -> new ListExceptions.UserNotFoundException("Пользователь не найден!"));
        if (user.getBlocked()){
            throw new ListExceptions.UserIsBlocked("Данный пользователь заблокирован!");
        }
        JwtResponse jwtResponse = authService
                .login(new JwtRequest(userDetails.getEmail(), userDetails.getPassword()), user);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getEmail(), userDetails.getPassword()));
        return LoginResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .accessToken(jwtResponse.getAccessToken())
                .refreshToken(jwtResponse.getRefreshToken())
                .build();
    }

    public UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest userDetails){
        var user = userRepository.findUserByEmail(userDetails.getEmail())
                .orElseThrow(() -> new ListExceptions.UserNotFoundException("Пользователь не найден"));
        user.setUsername(userDetails.getUsername());
        userRepository.save(user);
        return UpdateUserInfoResponse.builder()
                .username(userDetails.getUsername())
                .build();
    }


    public DeleteUserResponse deleteUserByEmail(DeleteUserRequest request){
        String email = request.getEmail();
        // var roles = userRepository.getUserRolesByEmail(email);
        if (request.isAdminRequest()){
            if(userRepository.existsUserByEmail(email)){
                User user = userRepository.findByEmail(email);
                try{
                    userRepository.deleteById(user.getId());

                    return DeleteUserResponse.builder()
                            .status("Success")
                            .message("User Deleted")
                            .build();
                }catch (Exception e) {
                    throw new ListExceptions.InvalidInputException(e.getMessage());
                }
            }else throw new ListExceptions.UserNotFoundException("Пользователь не найден!");
        }else{
            if (userRepository.existsUserByEmail(email)){
                User user = userRepository.findByEmail(email);
                if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
                    throw new ListExceptions.WrongPasswordException("Неправильный пароль!");
                }else {
                    try {
                        userRepository.deleteById(user.getId());
                        return DeleteUserResponse.builder()
                                .status("Success")
                                .message("User Deleted")
                                .build();
                    } catch (Exception e) {
                        throw new ListExceptions.InvalidInputException(e.getMessage());
                    }
                }
            }else throw new ListExceptions.UserNotFoundException("Пользователь не найден!");
        }
    }

    // ADMIN OPERATION WITH USER
    public boolean deleteUser(Long userId){
        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    public boolean changeBlockStateUser(Long userId, Boolean value){
        var user = userRepository.findById(userId)
                .orElseThrow(()->new ListExceptions.UserNotFoundException("Пользователь не найден!"));
        user.setBlocked(value);
        userRepository.save(user);
        return true;
    }


    public List<User> usergtList(Long idMin){
        return em.createQuery("select u from User u where u.id < :paramId", User.class).setParameter("paramId", idMin).getResultList();
    }

}
