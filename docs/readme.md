# Detalhes por sprint

## Sprint 1

### ❓✅ Dúvidas tiradas com o cliente:
- O professor define o limite de pontos por grupo a cada sprint.
- As avaliações começam após o término da sprint, com uma semana para conclusão, e o professor gostaria de poder fechá-las manualmente ou automaticamente após o prazo.
- O professor não precisa modificar valores após os cálculos serem feitos.
- A aplicação não tomará nenhuma ação se o aluno tentar acessá-la fora do período de avaliação.
- Não é prioritário o aluno ter acesso às avaliações anteriores feitas por ele.
- Não é essencial gerar gráficos de evolução nas sprints, sendo suficiente gerar arquivos .csv para análise no Excel.
- Não é necessário gerar relatórios gerais ao final do projeto, apenas relatórios individuais por sprint.
- O professor poderá mover alunos entre equipes, mas excluir alunos não é uma prioridade.
- O professor gostaria de definir as datas de cada sprint e poder editá-las, mas essa funcionalidade não é prioritária.
- Não é necessário que o professor visualize relatórios de alunos que evadiram.

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
-  **Dúvidas pertinentes**:
    -  O professor prefere cadastrar alunos em grupo, utilizando arquivos .csv.
    -  Quando o professor for cadastrar o aluno na aplicação, ele vai inserir o nome, e-mail e a turma. O .csv deve conter o nome e o link do Github da equipe.

### 👤User story 2: *Aluno avalia outros integrantes da equipe a cada sprint para facilitar a visualização, facilitar o envio da avaliação e automatizar parte do processo*

- **DoR**:
    - O cliente deseja que a aplicação importe um .csv contendo a equipe e os alunos do banco de dados
      - Tela no JavaFX:
          - Após selecionar aluno na primeira tela, o usuário terá que colocar o seu email e, caso esteja cadastrado, terá uma tela contendo os outros alunos de seu grupo, que serão sujeitos à sua avaliação.
      - Backend que:
          -  Recebe as informações, e depois realiza o INSERT no banco de dados;
      - Banco de dados contendo:
          -  Tabela de notas.
-  **Dúvidas pertinentes**:
    -  Não é uma prioridade permitir que o aluno edite suas respostas após enviar suas notas, mas seria interessante considerar isso no futuro.

### ❓✅ Dúvidas tiradas com o cliente:

- O professor gostaria de ter acesso a projetos anteriores, mas isso não é prioritário, contanto que os dados não sejam apagados.
- A aplicação deve fornecer médias por critério/aluno, com as notas individuais de cada membro da equipe.
- O professor não deve editar a quantidade de sprints depois que ele começar.
- *Quando iniciamos um semestre novo, o professor vai inserir o ano e o semestre atual; depois, ele irá descrever a quantidade de sprints, e as datas de início e fim de cada uma delas; O professor descreverá os critérios; então, ele vai definir equipes e alunos.*

### 🖋️ Entrega da sprint:
_**Obrigatório:** A aplicação deve enviar e buscar informações do banco._ 

- Documentação para configurar a aplicação em Windows e Linux;
- Cadastro de equipes, incluindo a importação em .csv;
- Tela de avaliação dos membros da equipe.

******

## Sprint 3

### 👤User story 3: Apresentar as médias ao professor
- **DoR**:
    - JavaFX:
        - Seletor de equipe
        - Seletor de semestre (travado até preencher a equipe)
        - Seletor de sprint (travado até preencher o semestre)
        - Seletor de aluno (travado até preencher a sprint)
        - Tabela com as notas para cada critério
    
    - Back:
        - Endpoint de GET para todas equipes
        - Endpoint de GET para todos semestres da equipe selecionada
        - Endpoint de GET para todas sprints do semestre selecionado da equipe selecionada
        - Endpoint de GET para todos alunos da sprint selecionada do semestre selecionado da equipe selecionada
        - Pega os parâmetros dos seletores, monta uma query de GET, busca todas notas registradas referentes ao aluno, calcula a média para cada parâmetro e retorna para o front

    - Banco:
        - Tabela de Equipe
        - Tabela de Aluno
        - Tabela de Semestre
        - Tabela intermediária de equipe e semestre
        - Tabela de Sprint
        - Tabela de Critério
        - Tabela intermediária de critério e semestre
        - Tabela de Nota
        - Tabela de TipoUsuario


### 👤User story 4: Gerar relatórios dos valores calculados
- **DoR**:
    - JavaFX:
        - Seletor de equipe
        - Seletor de semestre (travado até preencher a equipe)
        - Seletor de sprint (travado até preencher o semestre)
        - Botão para gerar o relatório

  - Back:
      - Pega os parâmetros dos seletores, monta uma query de POST, identifica todos alunos e suas notas, e retorna um arquivo CSV para o usuário, que irá começar download imediatamente

  - Banco:
      - Tabela de Equipe
      - Tabela de Aluno
      - Tabela de Semestre
      - Tabela intermediária de equipe e semestre
      - Tabela de Sprint
      - Tabela de Critério
      - Tabela intermediária de critério e semestre
      - Tabela de Nota
      - Tabela de TipoUsuario

