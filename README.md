# Vacation Management

## Decisões de Arquitetura

## Repositório

# Para facilitar a avaliação por parte da Equipa LBC, optou-se por concentrar todo o projeto num único repositório. Desta forma, é possível executar toda a aplicação com um único comando (`docker compose up`), simplificando o processo de instalação e avaliação.

## Estrutura de Pacotes

# Foi adotada a abordagem \*\*Package by Feature\*\* para a organização dos pacotes.

# Esta abordagem foi escolhida porque o sistema possui domínios de negócio bem definidos, como \*\*Employee\*\*, \*\*Vacation\*\* e \*\*User\*\*. Ao agrupar as classes por funcionalidade, obtém-se uma maior coesão, melhor organização do código e uma navegação mais simples durante o desenvolvimento e manutenção da aplicação.

# Além disso, esta estrutura facilita a evolução do projeto, reduz o acoplamento entre funcionalidades e torna mais simples localizar todas as classes relacionadas com uma determinada feature.
