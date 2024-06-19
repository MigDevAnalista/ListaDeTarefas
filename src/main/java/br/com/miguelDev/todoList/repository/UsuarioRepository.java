package br.com.miguelDev.todoList.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.miguelDev.todoList.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
     Usuario findBylogin(String login); 
    
}
