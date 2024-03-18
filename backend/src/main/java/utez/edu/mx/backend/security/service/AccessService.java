package utez.edu.mx.backend.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.user.control.UserService;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.security.jwt.JwtDto;
import utez.edu.mx.backend.security.jwt.JwtProvider;
import utez.edu.mx.backend.security.jwt.LoginDto;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class AccessService {

    private final static Logger logger = LoggerFactory.getLogger(AccessService.class);

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(LoginDto loginDto) throws SQLException {
        Optional<User> optionalUser = userService.findFirstByCode(loginDto.getUsername());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();
        if (!user.isAvailable()) {
            return new ResponseEntity<>(new Message("Usuario inactivo", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Map<String, Object> claims = new HashMap<>();
            claims.put("username",userDetails.getUsername());
            claims.put("name", user.getPerson().getName() + " " + user.getPerson().getSurname() + " " + user.getPerson().getLastname());
            claims.put("user_id", user.getId());
            claims.put("role", user.getRole());

            String tkn = jwtProvider.generateToken(authentication, claims);
            JwtDto jwtDto = new JwtDto(tkn, userDetails.getUsername());
            return new ResponseEntity<>(jwtDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error de autenticación");
            return new ResponseEntity<>(new Message("Error de credenciales", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
    }
}
