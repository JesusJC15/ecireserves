package edu.eci.cvds.ECIReserves.service;

import edu.eci.cvds.ECIReserves.model.User;
import edu.eci.cvds.ECIReserves.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /*
     * Crea un nuevo usuario en el sistema.
     *
     * @param user Usuario a crear.
     * @return true si el usuario se creó exitosamente, false si hubo algún problema (por ejemplo, datos inválidos o usuario ya existente).
     */
    public boolean createUsers(User user){
        if(user.getId() == null || user.getEmail() == null || user.getEmail().isEmpty() || user.getId().isEmpty()
        ||user.getPassword() == null || user.getPassword().isEmpty() ||user.getName() == null || user.getName().isEmpty()){
            return false;
        }
        if(userRepository.findById(user.getId()).isPresent() || userRepository.findByEmail(user.getEmail()) != null){
        return false;
        }
        userRepository.save(user);
        return true;
    }

    /*
     * Actualiza la información de un usuario existente.
     *
     * @param id       ID del usuario a actualizar.
     * @param name     Nuevo nombre del usuario (opcional).
     * @param email    Nuevo correo electrónico del usuario (opcional).
     * @param password Nueva contraseña del usuario (opcional).
     * @return true si el usuario se actualizó correctamente, false si no se encontró el usuario.
     */
    public boolean updateUsers(String id,String name,String email,String password){
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return false;
        }

        if (name != null && !name.trim().isEmpty()) user.setName(name);
        if (email != null && !email.trim().isEmpty()) user.setEmail(email);
        if (password != null && !password.trim().isEmpty()) user.setPassword(password);

        userRepository.save(user);
        return true;
    }

    /*
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario a eliminar.
     * @return true si el usuario se eliminó correctamente, false si el usuario no existe o el ID es inválido.
     */
    public boolean removeUsers(String id){
        if (id == null || id.isEmpty() || userRepository.findById(id).isEmpty()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    /*
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de usuarios.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    public User getUser(String id){
        return userRepository.findById(id).orElse(null);
    }

}