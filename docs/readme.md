# Detalhes por sprint

## Sprint 1



## Sprint 2

### 👤User story 1: *Professor importa uma tabela contendo informações dos alunos para poupar tempo e padronizar o envio, cadastrando vários alunos de uma única vez*

- **DoD**:
- **DoR**:
    - Duas telas no JavaFX:
        - Tela de login para que o usuário selecione se ele é aluno ou professor;
        - Tela do professor para cadastrar equipe contendo um botão para adicionar um arquivo .csv. Deve haver um botão para confirmar o envio. Após o envio, a tela apresenta a equipe cadastrada.

    - Backend que:
        -  Recebe as informações, e depois realiza o INSERT no banco de dados;
        -  Após realizar o cadastro, o programa irá realizar um SELECT no banco de dados, trazendo as informações para a aplicação.
    - Banco de dados contendo:
        -  Tabela de equipe;
        -  Tabela de usuário.

### 👤User story 2: *Aluno avalia outros integrantes da equipe a cada sprint para facilitar a visualização, facilitar o envio da avaliação e automatizar parte do processo*

- **DoD**: 
- **DoR**:
  - Tela no JavaFX:
      - Após selecionar aluno na primeira tela, o usuário terá que colocar o seu email e, caso esteja cadastrado, terá uma tela contendo os outros alunos de seu grupo, que serão sujeitos à sua avaliação.
  - Backend que:
      -  Recebe as informações, e depois realiza o INSERT no banco de dados;
  - Banco de dados contendo:
      -  Tabela de notas.

### ❓✅ Dúvidas tiradas com o cliente:

- 
