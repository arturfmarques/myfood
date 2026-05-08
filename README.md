# MyFood

Projeto desenvolvido em **Java** para a disciplina de **Programação 2**, com aplicação de conceitos de **Programação Orientada a Objetos**, contemplando a implementação do **Milestone 01** e do **Milestone 02** do projeto **MyFood**.

## Objetivo

Implementar as funcionalidades principais do sistema MyFood, respeitando os requisitos definidos para os dois primeiros marcos do projeto e garantindo compatibilidade com os testes de aceitação fornecidos.

## Conceitos aplicados

Durante o desenvolvimento do projeto, foram aplicados os principais conceitos estudados na disciplina, entre eles:

- Classes e objetos
- Construtores
- Encapsulamento
- Herança
- Classes abstratas
- Composição
- Listas
- Exceções
- Persistência em arquivo
- Separação de responsabilidades
- Padrão Facade

## Funcionalidades implementadas

### Milestone 01
- Cadastro de usuários
- Login
- Consulta de atributos de usuários
- Cadastro de empresas
- Consulta de empresas
- Cadastro e edição de produtos
- Listagem de produtos
- Criação e gerenciamento de pedidos
- Persistência em arquivo

### Milestone 02
- Cadastro de mercados
- Cadastro de farmácias
- Alteração de horário de funcionamento de mercados
- Consulta de novos atributos de empresas
- Cadastro de entregadores
- Vínculo entre entregadores e empresas
- Listagem de entregadores de uma empresa
- Listagem de empresas vinculadas a um entregador
- Liberação de pedidos para entrega
- Criação e gerenciamento de entregas
- Consulta de atributos de entrega
- Finalização de entregas

## Estrutura do projeto

- `src`: código-fonte do sistema
- `models`: entidades principais do domínio
- `services`: classes responsáveis pelas regras de negócio e persistência
- `exceptions`: exceção personalizada para regras de negócio
- `Facade`: classe principal utilizada pelos testes
- `Main`: classe de execução dos testes com EasyAccept
- `tests`: arquivos de teste de aceitação
- `lib`: biblioteca `easyaccept.jar`
- `relatorio`: relatório do projeto em PDF

## Testes implementados

O projeto está compatível com os testes de aceitação até:

- `us1_1`
- `us1_2`
- `us2_1`
- `us2_2`
- `us3_1`
- `us3_2`
- `us4_1`
- `us4_2`
- `us5_1`
- `us5_2`
- `us6_1`
- `us6_2`
- `us7_1`
- `us7_2`
- `us8_1`
- `us8_2`

## Como executar

1. Abra o projeto no IntelliJ IDEA
2. Certifique-se de que a pasta `src` está marcada como **Sources Root**
3. Verifique se o arquivo `easyaccept.jar` está corretamente adicionado ao projeto
4. Execute a classe `Main.java` para rodar os testes de aceitação

## Relatório

O relatório do projeto está disponível na pasta:

- `relatorio`

## Observação

O projeto foi implementado de forma compatível com os testes do EasyAccept disponibilizados para o **Milestone 01** e o **Milestone 02** do projeto **MyFood**.