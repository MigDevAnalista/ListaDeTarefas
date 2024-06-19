# Entendendo o Controller

Qual a diferença entre @Controller e o @RestController:

O controller é a camada que fica entre o usuário e a requisição e as demais camadas. Ela que vai receber as requisições do usuário.

* @Controller :  Utiliza quando queremos criar uma estrutura onde a gente tenha as páginas o spring  ele permite que a gente retorne páginas templates.
* @RestController: Quando agente está construindo uma api, e estamos ultilizando conceito de rest ou restfull.

## Métodos do http: 

Métodos de Acesso de Requisições HTTP:

* GET - Buscar uma informação
* POST - Adicionar um dado/informação
* PUT - Alterar um dado/informação
* DELETE -  Remover um dado
* PATH -  Alterar somente uma parte da informação/dado

Quando falamos  @RestController vamos criar uma rota, e o que seria uma rota o caminha que temos no browser: //http://localhost:8080/ o que vier aqui é a rota.

```java
@RestController
@RequestMapping("/primeiraRota")
public class TesteController{
    @GetMapping("/")//rota
    public String getMensagem(){
       return "Olá Mundo!";  
    }

}
```

Quando pegamos as informações dentro do body da requisição usamos a anotation @RequestBody, fazemos desta forma:

```java
public void criar(@RequestBody Usuario usuario){
    System.out.println(usuario.getNome());
 }
```

## Lombok

É uma lib que existe no java para conseguir otimizar em criar metodos settrs e gettrs e até mesmo outros métodos como construtor, equals e hashcode.

Anotation do Lombok:

```java
@Data ele coloca para todos os atributos da classe getters e setters toString e equalsHascode
```

## UUID

Ele é um identificador único, que ele gera uma chave de números e letras. ele é mais seguro que um id sequencial.

## Autowired

```java
@Autowired
UsuarioRepository usuarioRepository;
/**
 * é desta forma que eu instancio, pois o Spring com o Autowired ele gerencia o ciclo de vida faz a criação da instancia do objeto e etc.
*/

/*Se caso eu não queira fazer da forma de cima, eu não queira usar o @Autowired, você pode criar um construtor e passar o seu repository neste caso não vai precisar usar o @Autowired, exemplo abaixo.*/

// ai usa desta forma aqui embaixo. Neste caso funciona.
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    private UsuarioRepository usuarioRepository;
    
    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
} 


```

## Repository

O Repository ele permite que possamos fazer transações com o banco de dados, para isso precisamos criar uma Interface que herda de um JpaReposiotory passando um objeto que seria a classe e o seu identificador.

```java
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findBylogin(String login);/*quando queremos criar uma consulta costumizada colocamos o findBy e depois coloca o nome do atributo da classe que você quer consultar neste caso o login, ele faz diferença de maiusculo ou menusculo exemplo:
método findByLogin(String login); ou findBylogin(String login);  se seu atributo estiver maiusculo coloque maiusculo se tiver menusculo coloque menusculo.*/
  
}

```



## Podemos também usar o ResponseEntity

 Para poder ter retornos diferentes na nossa aplicação dentro da mesma requisição, para isso vou precisar utilizar o tipo de retorno ResponseEntity , desta forma conseguimos retorna casos de sucesso como de erro. Ou seja o ResponseEntity ele vai passar tanto algo que deu sucesso como algo que deu erro.

```java
    @PostMapping("/")
    public ResponseEntity criar(@RequestBody Usuario usuario){//@RequestBody significa que os dados vem do corpo da requisição
        var login = usuarioRepository.findByLogin(usuario.getLogin());
            if(login != null){
                // o que podemos retornar
                // Mensagem de erro
                // Status Code
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
            }else{
                var usuarioCriado =  usuarioRepository.save(usuario);
                return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);    
            }
    }
```

## Criptografar  Senha:

```java
/*Baixa a dependencia, é uma lib de encriptografar:
BCrypt.withDefaults().hashToString(numero da força da senha, aqui coloca a senha); este metodo retorna um array de char por isso tem que converter para toCharArray();
*/
var senha =  BCrypt.withDefaults().hashToString(12, usuario.getSenha().toCharArray());
```

## Lombok

Ele serve para colocar nossos codigo getters e setters, equals e hashCode e construtores. para não ficar com muita repetição de códgo!

* @Data  -> ele coloca os getters e setters para todos os atributos quando a anotation está encima da classe.
* @Getter -> ele vai colocar apenas os getteres para todos os atributos quando a anotation está encima da classe.
* @Setter -> ele vai colocar apenas os setters para todos os atributos quando a anotation está encima da classe.

Os metodos getters e setters no lombok:

```java
@Data
@Entity(name="tbl_usuario") //-> posso colocar o nome da tabela
public class Usuario{
    @Id  //-> a importação do id é do jarkarta persiste
    @GeneratedValue(generator="UUID") //-> gera o id automatico
    private UUID id; //-> este UUID é um identificador unico
    
    @Column(name="login") //-> nome da minha coluna no meu banco de dados
    private String login;
    
    private String nome;
    private String senha;
    
    @CreationTimestamp
    private LocalDateTime createdAt; //-> para salvar a data e hora da criação do usuário
    
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*se quiser colocar o get ou set em algum atributo especifico*/
public class Usuario{
    @Getter
    @Setter
    private String login; //-> aqui tem o getter e setter
    
    @Getter
    private String nome; //-> aqui tem somente o getter
    private String senha;
    
}

```

## Controller

O controller vai fazer a comunicação da pagina web com a api.

```java
@RestController // => aqui eu digo que é um restapi 
@RequestMapping("/usuario") // aqui eu tenho um mapeamento do endpoint 
public class UsuarioController {
    
    //@Autowired  => aqui eu digo para o spring instanciar a minha classe, com esta anotation eu não preciso fazer isso 
    //UsuarioRepository usuarioRepository = new UsuarioRepository();
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public ResponseEntity criar(@RequestBody Usuario usuario){
    }
}
```

Tipos de retorno que podemos usar:

```java
   /*O ResponseEntity serve para retornar o status code, e não só mensagem e objetos, ou seja o ResponseEntity com ele vamos conseguir passar tanto algo que deu sucesso quanto algo que deu erro*/
 
    @PostMapping("/")
    public ResponseEntity criar(@RequestBody Usuario usuario){
           var login = usuarioRepository.findByLogin(usuario.getLogin());
            if(login != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");// retorna uma mensagem de erro
            }else{
               //criptografo a senha e uso uma lib BCrypt
               var senha =  BCrypt.withDefaults().hashToString(12, usuario.getSenha().toCharArray());
               usuario.setSenha(senha);

                  var usuarioCriado =  usuarioRepository.save(usuario); // ele retorna a entidade criada

                  return ResponseEntity.status(HttpStatus.OK).body(usuarioCriado);//ele retorna status ok e o objeto    
            }
    }
```

vendo agora as tarefas que o usuário pode usar.parei na aula 03



Dependências:

```xml

<!-- Banco H2 --> 
     <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

<!-- Lombok --> 
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId><!--  --> 
        <scope>provided</scope>
    </dependency>

<!-- Criptografar senha -->
    <dependency>
        <groupId>at.favre.lib</groupId>
        <artifactId>bcrypt</artifactId>
        <version>0.10.2</version>
    </dependency>
```

