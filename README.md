# Projeto Avaliador de Soft Skill

![sqlutions logo](https://github.com/user-attachments/assets/4884e8b3-b59a-45ba-ad13-13faa8d4d9b3)

### Bem-vindo ao repositório do nosso projeto _**Avaliador de Soft Skill**_, desenvolvido pela equipe _**SQLutions**_ do curso Banco de Dados 2º Semestre da Fatec de São José dos Campos.

---

<div align="center">

[Sobre](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md#-sobre-o-projeto) | [Backlogs e User Stories](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md#-backlogs--user-stories) | [Documentação](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md#%EF%B8%8F-documenta%C3%A7%C3%A3o) | [Tecnologias](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md#%EF%B8%8F-tecnologias) | [Equipe](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md#equipe)

</div>

## 📑 Sobre o projeto

O cliente do projeto é um cliente interno da FATEC (professores P2 e M2). A ideia para este projeto é o desenvolvimento de um Avaliador de Soft Skill. Todos anos os professores responsáveis pela API recebem dos alunos uma avaliação PACER (Proatividade, Autonomia, Colaboração e Entrega de Resultados) que os próprios alunos do grupo preenchem, porém, acabam sendo feitos em formatos diferentes e, por vezes, sem cálculo da média de cada aluno, o que toma muito tempo dos professores.<br>
Estre projeto deverá ser capaz de permitir que alunos de um grupo avaliem seus membros, calcule a média das notas e que os professores recebam esta informação em um formato padronizado. O projeto também deverá dar algumas permissões adicionais para que o professores possam ter um certo nível de controle sobre o sistema (admin). A interface deve ser intuitiva e os fluxos mapeados para exibir as mensagens corretas para que o usuário não se sinta perdido.

> Projeto baseado na metodologia ágil SCRUM, procurando desenvolver a Proatividade, Autonomia, Colaboração e Entrega de Resultados dos estudantes envolvidos

📌 Status do Projeto: Sprint 3

### 🏁 Entregas de Sprints
Cada entrega foi realizada a partir da criação de uma tag em cada repositório (web e todos os microsserviços), além da criação de uma branch neste repositório com um relatório completo de tudo o que foi desenvolvido naquela sprint. Observe a relação a seguir:

| Sprint | Previsão |	Status |	Histórico |
|--------|----------|--------|------------|
| 01 | 29/09/2024 |	✔️ Concluída | [ver relatório](https://github.com/SQLutions-FATEC/API-2-Semestre/tree/develop-1) |
| 02 | 20/10/2024	| ✔️ Concluída | [ver relatório](https://github.com/SQLutions-FATEC/API-2-Semestre/tree/develop-2) |
| 03 | 10/11/2024	| Etapa futura | ver relatório |
| 04 | 01/12/2024	| Etapa futura | ver relatório |

### 🎬 Apresentação Final
Confira a seguir uma demonstração das funcionalidades para cada tipo de usuário do sistema:

<details>
  <summary>Aluno</summary>
  TODO
</details>

<details>
  <summary>Professor</summary>
  TODO
</details>

[→ Voltar ao topo](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md)

## 🎯 Backlogs & User Stories

### Backlog do Produto

#### ✅ Requisitos Funcionais

| ID | Descrição                                                   | Prioridade  | Fator | Sprint |
|:--:|-------------------------------------------------------------|:-----------:|:-----:|:-----:|
| 1  | Alunos preencherem avaliações de outros membros             |   🔴 Alta   |   1   |   2   |
| 2  | Apresentar as médias ao professor                           |   🔴 Alta   |   2   |   3   |
| 3  | Receber dados das equipes e seus integrantes (arquivo .csv) |   🔴 Alta   |   3   |   2   |
| 4  | Gerar relatórios dos valores calculados                     |   🔴 Alta   |   4   |   3   |
| 5  | Período de início e fim de avaliação dos alunos             |  🟡 Média   |   5   |   4   |
| 6  | Identificar o usuário (Login)                               |  🟡 Média   |   6   |   4   |
| 7  | Aluno visualiza avaliações anteriores                       |  🟢 Baixa   |   7   |   2   |


#### ✔️ Requisitos Não Funcionais

| ID | Descrição |
|:--:|-----------|
| 7 | Linguagem de programação: Java |
| 8 | Banco de Dados com modelo relacional |
| 9 | Acesso ao Banco de Dados com JDBC |

### Backlog das Sprints

#### Sprint 1

| ID | Descrição |
|:--:|-----------|
| x | x |

#### Sprint 2

| ID | Descrição |
|:--:|-----------|
| 1 | ✅ Alunos preencherem avaliações de outros membros |
| 3 | ✅ Receber dados das equipes e seus integrantes (arquivo .csv) |
| 7 | ✔️ Linguagem de programação: Java |
| 8 | ✔️ Banco de Dados com modelo relacional |
| 9 | ✔️ Acesso ao Banco de Dados com JDBC |

#### Sprint 3

| ID | Descrição |
|:--:|-----------|
| 2 | ✅ Apresentar as médias ao professor |
| 4 | ✅ Gerar relatórios dos valores calculados |
| 7 | ✔️ Linguagem de programação: Java |
| 8 | ✔️ Banco de Dados com modelo relacional |
| 9 | ✔️ Acesso ao Banco de Dados com JDBC |

#### Sprint 4

| ID | Descrição |
|:--:|-----------|
| 5 | ✅ Período de início e fim de avaliação dos alunos |
| 6 | ✅ Identificar o usuário (Login) |
| 7 | ✔️ Linguagem de programação: Java |
| 8 | ✔️ Banco de Dados com modelo relacional |
| 9 | ✔️ Acesso ao Banco de Dados com JDBC |

### User Stories

| Épico | ID | Sprint | Ator      | Ação                                                                                                             | Motivo                                                                               |
|:-----:|:--:|:------:|-----------|------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
|   3   | 1  |   2    | Professor | Importa uma tabela contendo informações dos alunos                                                               | Para cadastrar um grupo inteiro de uma única vez                                     |
|   1   | 2  |   2    | Aluno     | Avalia os outros integrantes da equipe com linhas referentes aos integrantes, e colunas referentes aos critérios | Para facilitar a visualização e o envio da avaliação e automatizar parte do processo |
|  2/4  | 3  |   3    | Professor | Visualiza as médias das notas da equipe por sprint                                                               | Para obter um acompanhamento detalhado e periódico do desempenho das equipes         |
|   7   | 4  |   3    | Aluno     | Visualiza o histórico de avaliações                                                                              | Para obter um feedback real no andamento do projeto                                  |
|   3   | 5  |   3    | Professor | Gerencia alunos e equipes antes do início do projeto                                                             | Para fornecer os acessos aos alunos                                                  |
|   3   | 6  |   3    | Professor | Edita os alunos e equipes ao longo do projeto                                                                    | Para remanejar os alunos entre as equipes                                            |
|   5   | 7  |   4    | Professor | Define as datas de cada sprint                                                                                   | Para definir controles sobre o período de acesso dos alunos na plataforma            |
|   5   | 8  |   4    | Professor | Edita as datas de cada sprint                                                                                    | Para contornar imprevistos que venham a surgir no calendário acadêmico               |
|   6   | 9  |   4    | Usuário   | Faz login com a sua crendencial                                                                                  | Para acessar funcionalidades que correspondam à função do usuário                    |
|   1   | 10 |   4    | Professor | Altera critérios que serão avaliados entre os integrantes em cada projeto                                        | Para ter flexibilidade e trazer o que é pertinente ao mercado ao longo dos anos      |
|       | 11 |   x    | Professor | Define quantidade de sprints no projeto                                                                          | Para ter flexibilidade em caso de aumento ou redução do tempo do projeto             |


[→ Voltar ao topo](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md)

## 🖥️ Documentação

Como planejamento foi feito wireframe para validação do fluxo com o cliente, assim como fluxograma e a modelagem de banco de dados que estão acessíveis [na documentação](https://github.com/SQLutions-FATEC/API-2-Semestre/wiki/3.-Documenta%C3%A7%C3%A3o-do-Projeto)

> 🔗 **Links gerais**<br>
>
> - Manual do usuário: TODO<br>
> - Guia de instalação: [clique aqui para acessar](https://drive.google.com/file/d/1V-B9wRj7Xq34Bef8cKEu-fvkvedjF5vK/view?usp=sharing)<br>
> - Modelagem do banco de dados: [clique aqui para acessar](https://lucid.app/lucidchart/d3db8d95-2b6a-473f-8f60-90e07a328f93/edit?invitationId=inv_a0329dad-38a0-4aa1-93a9-6037de6f0d62&page=0_0#)

[→ Voltar ao topo](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md)

## 🛠️ Tecnologias

As seguintes ferramentas, linguagens, bibliotecas e tecnologias foram usadas na construção do projeto:

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/) [![JavaFX](https://img.shields.io/badge/JavaFX-18A303?style=for-the-badge&logo=java&logoColor=white)](https://openjfx.io/) [![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/) [![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/seu-usuario) [![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/) [![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)](https://slack.com/) [![Google Docs](https://img.shields.io/badge/Google_Docs-4285F4?style=for-the-badge&logo=google-docs&logoColor=white)](https://docs.google.com/) [![Figma](https://img.shields.io/badge/Figma-0ACF83?style=for-the-badge&logo=figma&logoColor=white)](https://www.figma.com/) [![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)](https://www.jetbrains.com/idea/)

[→ Voltar ao topo](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md)

## 👥 Equipe

|  | Função | Nome | Redes |
|--|--------|------|-------|
| ![davi](https://github.com/user-attachments/assets/08f611cd-614b-41b8-a7a7-cab3065e9aeb) | Product Owner | Davi Soares Fernandes dos Santos | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/dsf21/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/DaviSFS21) |
| ![augusto](https://github.com/user-attachments/assets/642849b4-3329-4e61-8d77-9a4b84f258de) | Scrum Master | Augusto de Moraes Piatto | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/augusto-piatto/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/augustopiatto) |
| ![bryan](https://github.com/user-attachments/assets/ee31c3b5-3ba6-4019-8c95-ba03cfa94d4c) | Desenvolvedor | Bryan Matheus | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/bryan-matheus-5aa0a3302/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/BryanARMatheus) |
| ![caina](https://github.com/user-attachments/assets/a6f52b8c-11c7-4f20-9647-004cd04c60bc) | Desenvolvedor | Cainã Nascimento Melo | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/cain%C3%A3-melo/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/CainaNascimentoMelo) |
| ![enzo](https://github.com/user-attachments/assets/3211d516-d4a0-48fc-af1d-e1cbef5d1a1e) | Desenvolvedor | Enzo Lemos Franco | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/enzo-lemos-franco-002651293/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/EnzoLFranco) |
| ![gloria](https://github.com/user-attachments/assets/2de16de0-fd28-4700-b5b5-a00702dfce10) | Desenvolvedora | Glória Brito | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/gloriafbrito/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/GloBrito) |
| ![joao](https://github.com/user-attachments/assets/984a1b7c-e4fa-4b78-84f1-0391f0f08de5) | Desenvolvedor | João Tuschtler Paulista | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/joaopaulista/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/joaopaulista) |
| ![lucas](https://github.com/user-attachments/assets/2a1afdae-7de8-4449-9fea-28fac568e960) | Desenvolvedor | Lucas Souza | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/lucas-souza-a67a52165/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/JayWeinberg123) |
| ![tiago](https://github.com/user-attachments/assets/2715b681-6533-41bd-a176-4ec9130a1a90) | Desenvolvedor | Tiago Torres dos Reis | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/tiago-torres-dos-reis/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/TiagoTReis) |

[→ Voltar ao topo](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md)
