### Security API

O **Security API** irá oferecer suporte a alguns mecanismos de controle e gerenciamento de acesso à sua API desenvolvida com Spring Boot.
Os mecanismos poderão ser usados para autenticação e autorização de recursos.
Forneceremos um contexto de segurança com autenticação de usuários e controle total de acesso a serviços.

![](https://img.shields.io/badge/Security_API-snapshot--v1.0.0.0-green.svg?&logo=java&style=for-the-badge)

![](https://img.shields.io/badge/Java_8-✓-blue.svg?&logo=java)
![](https://img.shields.io/badge/Spring_boot_v2.6.3-✓-blue.svg?&logo=spring)
![](https://img.shields.io/badge/Swagger_2-✓-blue.svg?&logo=swagger)
![](https://img.shields.io/badge/JWT-✓-blue.svg?&logo=jsonwebtokens)
![](https://img.shields.io/badge/Flyway-✓-blue.svg?&logo=amazondynamodb)

# Passos para configuração

## Baixar e executar projeto localmente
```git
git clone https://github.com/raonicorreia/security.git
```
```maven
mvn clean install -U
```

# Em seu projeto
## Importar dependência

```xml
<dependency>
    <groupId>br.com.raospower</groupId>
    <artifactId>security</artifactId>
    <version>1.0.0.0</version>
</dependency>
```
## Configurar pool de conexão

```properties
spring.datasource.driver-class-name=org.apache.derby.jdbc.EmbeddedDriver
spring.datasource.url=jdbc:derby:C:/temp/dados/security;create=true
spring.datasource.username=security
spring.datasource.password=security
spring.jpa.hibernate.ddl-auto=none
```
## Definir parâmetros

```properties
# Propriedade para definir o tempo de expiracao do Token
raospower.security.token.expiration=1600000
# Chave a ser utilizada para assinar o token
raospower.security.key.secret=cff37da8-88ce-4ce3-a4c9-4e7f3aee5605
# Tipo de criptografia a ser utilizada para senha durante as autenticacoes
raospower.security.cryption.algorithm=AES
# Chave da criptografia
raospower.security.cryption.key=97C5526BABCE8F67221D10F305624438

# Adicionado devido a falha ao gerar swagger
spring.mvc.pathmatch.matching-strategy=ant-path-matcher
```
## Especificar pacote na configuração do Spring

Na classe principal do Spring adicionar o pacote **br.com.raospower** em scanBasePackages da anotação @SpringBootApplication.

# Executando aplicação pela primeira vez

Antes de executar a aplicação, orientamos realizar a limpeza e o empacotamento através do comando.

`$ mvn clean package`

# Configurando Controller

## Usando anotação "hasPermission(operation, verbo_http)"

A anotação **hasPermission(operation, verbo_http)** deverá ser adicionada acima de todas as operações que desejar controle de acsesso.
Deverá ser fornecido os valores **operation** e **verbo_http**, onde operation será o path definido para consumo da operação e verbo_http o verbo que irá indicar a ação que está sendo requisitada.

Exemplo: 

```java
@RestController
@RequestMapping("v1/usuario")
public class UsuarioController {

    @GetMapping("/")
    @PreAuthorize("hasPermission('v1/usuario', 'GET')")
    public List<Usuario> getUsuarios(@RequestBody Usuario usuario) {
        UsuarioSpecification usuarioSpecification = new UsuarioSpecification(usuario);
        return usuarioService.getUsuarios(usuarioSpecification);
    }
    
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasPermission({#id}, 'v1/usuario/{id}', 'GET')")
    public Usuario getUsuarioByID(@PathVariable("id") Long id) throws UsuarioInexistenteException {
        return usuarioService.getUsuarioByID(id);
    }
}
```
```curl
caso 1: curl --location --request GET 'localhost:8080/v1/usuario/'
caso 2: curl --location --request GET 'localhost:8080/v1/usuario/1'
```

### Para o caso 1 - getUsuarios
A configuração ficaria da seguinte forma:

- Operação: [RequestMapping + GetMapping] = v1/usuario/
- Verbo HTTP: GET

### Para o caso 2 - getUsuarioByID
A configuração ficaria da seguinte forma:

- Operação: [RequestMapping + GetMapping] = v1/usuario/{id}
- Verbo HTTP: GET

# Concedendo acesso ![](https://img.shields.io/badge/Construindo-✓-red.svg?)

## Usuário default
- user: admin
- senha: FUxqDqZdVVbt/SR+F0y8tQ==

## Perfis default
#### - ROLE_ADMIN
#### - ROLE_USER

## Por Script
Construindo...

## Através do Serviço
Construindo...

# Testando ![](https://img.shields.io/badge/Construindo-✓-red.svg?)
Construindo...

# Acessando swagger
http://localhost:8080/swagger-ui.html


# Fim

[![](https://img.shields.io/badge/Linkedin-raonicorreia-blue.svg?&logo=linkedin)](https://www.linkedin.com/in/raonicorreia/)

teste
