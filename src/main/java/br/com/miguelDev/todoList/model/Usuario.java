package br.com.miguelDev.todoList.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data  //lombok
@Entity(name ="tbl_usuario")
public class Usuario {

    @Id
    @GeneratedValue(generator = "UUID") // gera o id de forma automatica
    private UUID uuid;
    
    @Column(unique = true) // define que vai ser um valor unico
    private String login;
    
    private String nome;
    private String senha;
    
    //salva a data e a hora quando for inserido algum dado no banco automaticamente.
    @CreationTimestamp
    private LocalDateTime createdAt;
  
}
