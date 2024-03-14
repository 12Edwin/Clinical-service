package utez.edu.mx.backend.security.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utez.edu.mx.backend.access.role.control.RoleService;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.access.role.model.RoleRepository;
import utez.edu.mx.backend.access.user.control.UserService;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.security.entity.MyUserDetails;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    @Lazy
    public MyUserDetailsService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> userOptional = userService.findFirstByCode(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Optional<Role> role = Optional.empty();
                if(user.isAvailable()) {
                    role = roleService.findById(user.getRole().getId());
                }
                user.setRole(role.orElse(null));
                return MyUserDetails.build(user);
            }
            logger.error("NO SE ENCONTRÓ");
            throw new RuntimeException("NO SE ENCONTRÓ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
