
package com.tienda.service;


import com.tienda.model.User;
import com.tienda.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
/**
 *
 * @author bfabian
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repositorio;

    public Optional<User> getUser(int id) {
        return repositorio.getUser(id);
    }

    public User create(User user) {
        if (user.getId() == null) {
            return user;
        } else {
            Optional<User> e = repositorio.getUser(user.getId());
            if (e.isEmpty()) {
                if (emailExist(user.getEmail()) == false) {
                    return repositorio.create(user);
                } else {
                    return user;
                }
            } else {
                return user;
            }
        }
    }
    
    public User update(User user) {

        if (user.getId() != null) {
            Optional<User> userDb = repositorio.getUser(user.getId());
            if (!userDb.isEmpty()) {
                if (user.getIdentification() != null) {
                    userDb.get().setIdentification(user.getIdentification());
                }
                if (user.getName() != null) {
                    userDb.get().setName(user.getName());
                }
                if (user.getAddress() != null) {
                    userDb.get().setAddress(user.getAddress());
                }
                if (user.getCellPhone() != null) {
                    userDb.get().setCellPhone(user.getCellPhone());
                }
                if (user.getEmail() != null) {
                    userDb.get().setEmail(user.getEmail());
                }
                if (user.getPassword() != null) {
                    userDb.get().setPassword(user.getPassword());
                }
                if (user.getZone() != null) {
                    userDb.get().setZone(user.getZone());
                }

                repositorio.update(userDb.get());
                return userDb.get();
            } else {
                return user;
            }
        } else {
            return user;
        }
    }
    
    public boolean delete(int userId) {
        Boolean aBoolean = getUser(userId).map(user -> {
            repositorio.delete(user);
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
    public List<User> listAll() {
        return repositorio.listAll();
    }

    public boolean emailExist(String email) {
        return repositorio.emailExist(email);
    }

    public User autenticateUser(String email, String password) {
        Optional<User> usuario = repositorio.autenticateUser(email, password);

        if (usuario.isEmpty()) {
            return new User();
        } else {
            return usuario.get();
        }
    }
    
}
