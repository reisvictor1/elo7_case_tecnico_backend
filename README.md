# SPACE PROBE

## 🚀 Como rodar

### 📝 Requisitos
- Java 21
- Maven 3.9.8

### ⬆️ Iniciando a aplicação

1. Execute `./mvnw clean package` para construir a aplicação
1. Execute `./mvnw spring-boot:run` para iniciar a aplicação
2. Acesse http://localhost:8080

## 📄 Swagger
Existe um swagger rodando em http://localhost:8080/swagger-ui/index.html


### Observações da execução do Projeto
No desenvolvimeto da solução, eu problema ao compilar os testes unitários de forma que 
não consegui encontrar uma maneira das importações referentes a realização dos testes serem compilados (no caso, não reconhecia o WebApplicationContext para o funcionamento do MockMvc); porém coloquei um arquivo .txt para colocar o que eu tinha pensado para os testes. 


### Sobre a solução
Achei que todos as entidades que estavam criadas refletiam muito bem uma modelagem robusta do problema. A única coisa que adicionei, dado o entendimento da descrição, foi um campo de direction na entidade Probe. Isso porquê, pela descrição, foi entendido que a sequência de comandos se daria a partir dessa direção; então, ao meu ver, não faz sentido não existir probe sem direção, porque ele sempre vai ter que ter uma direção quando estiver no planeta.
Todos os métodos definidos no diretório 'infra/repository' foram úteis a minha solução.
No fim, acabei criando somente um serviço que atualizava a posição do Probe no planeta dado o que fosse definido no controller. 
A parte que mais desenvolvi foi o ProbeMovementController, criei um método PUT que atualizava a posição do probe no planeta devido a sequencia de comandos enviada pelo body da requisição. Como eu deveria verificar se ele estaria dentro dos limites do planeta e que ele não colidiria com outros probes, coloquei essas condições como as condições que teriam um "BAD REQUEST".