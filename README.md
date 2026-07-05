# Vacation Management

## Como Executar o Projeto

### Pré-requisitos

Antes de iniciar a aplicação, certifique-se de que possui os seguintes requisitos instalados:

* Docker
* Docker Compose

### Inicialização

Na raiz do projeto, execute:

```bash
docker compose up
```

Na primeira execução, o Docker irá descarregar as imagens necessárias e criar todos os contentores definidos no ficheiro `docker-compose.yml`. Este processo poderá demorar alguns minutos.

Após a conclusão da inicialização, a aplicação estará pronta para utilização.

### Credenciais de Acesso

O projeto cria automaticamente um utilizador administrador para facilitar a avaliação e os testes da aplicação.

| Campo        | Valor           |
| ------------ | --------------- |
| **Username** | `maribel.admin` |
| **Password** | `password123`   |

## Decisões de Arquitetura

### Repositório

Para facilitar a avaliação por parte da Equipa, optou-se por concentrar todo o projeto num único repositório. Desta forma, é possível executar toda a aplicação com um único comando (`docker compose up`), simplificando o processo de instalação e avaliação.

### Estrutura de Pacotes

Foi adotada a abordagem **Package by Feature** para a organização dos pacotes.

Esta abordagem foi escolhida porque o sistema possui domínios de negócio bem definidos, como **Employee**, **Vacation** e **User**. Ao agrupar as classes por funcionalidade, obtém-se uma maior coesão, melhor organização do código e uma navegação mais simples durante o desenvolvimento e manutenção da aplicação.

Além disso, esta estrutura facilita a evolução do projeto, reduz o acoplamento entre funcionalidades e torna mais simples localizar todas as classes relacionadas com uma determinada feature.
