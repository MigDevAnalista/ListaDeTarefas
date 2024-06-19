package br.com.miguelDev.todoList.model;
/** O que uma tarefa pode ter
 * Id
 * Usuario (ID_USUARIO)
 * Descrição
 * Titulo
 * data de inicio
 * data de termino
 * Prioridade
 */

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tbl_tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

   
    private UUID usuarioId;//referencia ao usuário
    
    @Column(length = 50) // tamanho de 50 caracteres
    private String titulo;
    
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String prioridade;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
