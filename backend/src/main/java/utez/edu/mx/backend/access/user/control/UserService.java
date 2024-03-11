package utez.edu.mx.backend.access.user.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.access.user.model.UserRepository;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public boolean existsUsername (String username){
        Optional<User> optionalUser = repository.findFirstByCode(username);
        return optionalUser.isPresent();
    }

    public Optional<User> findFirstByCode(String code) {
        return repository.findFirstByCode(code);
    }
}
