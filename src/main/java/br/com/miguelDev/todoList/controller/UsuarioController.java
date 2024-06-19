package br.com.miguelDev.todoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.miguelDev.todoList.model.Usuario;
import br.com.miguelDev.todoList.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository; 

    @PostMapping("/")
    public ResponseEntity criar(@RequestBody Usuario usuario){
           var login = usuarioRepository.findBylogin(usuario.getLogin());
            if(login != null){
                //Mensagem de erro .body
                //status Code .status(HttpStatus.BAD_REQUEST) -> passa o erro que você quer de acordo com o código
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
            }else{
               //criptografo a senha e uso uma lib BCrypt, força da senha 12, a senha para incriptografar
               var senha =  BCrypt.withDefaults().hashToString(12, usuario.getSenha().toCharArray());
               usuario.setSenha(senha);

                  var usuarioCriado =  usuarioRepository.save(usuario); // ele retorna a entidade criada

                  return ResponseEntity.status(HttpStatus.OK).body(usuarioCriado);    
            }
    }
}
