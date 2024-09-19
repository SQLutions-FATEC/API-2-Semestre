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

📌 Status do Projeto: Sprint 1

### 🏁 Entregas de Sprints
Cada entrega foi realizada a partir da criação de uma tag em cada repositório (web e todos os microsserviços), além da criação de uma branch neste repositório com um relatório completo de tudo o que foi desenvolvido naquela sprint. Observe a relação a seguir:

| Sprint | Previsão |	Status |	Histórico |
|--------|----------|--------|------------|
| 01 | 29/09/2024 |	Em andamento | ver relatório |
| 02 | 20/10/2024	| Etapa futura | ver relatório |
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

| ID | Descrição | Prioridade | Fator | Sprint |
|:--:|-----------|:----------:|:-----:|:------:|
| 1 | Alunos preencherem avaliações de outros membros | :red_circle: Alta | 1 | 3 |
| 2 | Receber dados das equipes e seus integrantes (arquivo .csv) | :red_circle: Alta | 2 | 2 |
| 3 | Apresentar as médias ao professor | :red_circle: Alta | 3 | 4 |
| 4 | Gerar relatórios dos valores calculados | :yellow_circle: Média | 4 | 4 |
| 5 | Período de início e fim de avaliação dos alunos | :red_circle: Alta | 5 | 2 |
| 6 | Identificar o usuário (Login) | :yellow_circle: Média | 6 | 3 |

#### ✔️ Requisitos Não Funcionais

| ID | Descrição |
|:--:|-----------|
| 1 | Linguagem de programação: Java |
| 2 | Banco de Dados com modelo relacional |
| 3 | Acesso ao Banco de Dados com JDBC |

### Backlog das Sprints

#### Sprint 1

| ID | Descrição |
|:--:|-----------|
| x | x |

#### Sprint 2

| ID | Descrição |
|:--:|-----------|
| 1 | ✅ Receber dados das equipes e seus integrantes (arquivo .csv) |
| 2 | ✅ Período de início e fim de avaliação dos alunos |
| 3 | ✔️ Linguagem de programação: Java |
| 4 | ✔️ Banco de Dados com modelo relacional |
| 5 | ✔️ Acesso ao Banco de Dados com JDBC |

#### Sprint 3

| ID | Descrição |
|:--:|-----------|
| 1 | ✅ Alunos preencherem avaliações de outros membros | Alta | 1 | 3 |
| 2 | ✅ Identificar o usuário (Login) | Média | 6 | 3 |
| 3 | ✔️ Linguagem de programação: Java |
| 4 | ✔️ Banco de Dados com modelo relacional |
| 5 | ✔️ Acesso ao Banco de Dados com JDBC |

#### Sprint 4

| ID | Descrição |
|:--:|-----------|
| 1 | ✅ Apresentar as médias ao professor | Alta | 3 | 4 |
| 2 | ✅ Gerar relatórios dos valores calculados | Média | 4 | 4 |
| 3 | ✔️ Linguagem de programação: Java |
| 4 | ✔️ Banco de Dados com modelo relacional |
| 5 | ✔️ Acesso ao Banco de Dados com JDBC |

### User Stories

| Épico | ID | Ator | Ação | Motivo |
|:-----:|:--:|------|------|--------|
| x | 1 | Professor | Faz Login | Para acessar seu perfil e funcionalidades |
| x | 2 | Aluno | Faz login | Para acessar seu perfil e funcionalidades |
| x | 3 | Professor | Gerencia alunos e equipes antes do início do projeto | Para fornecer os acessos aos alunos |
| x | 4 | Professor | Importa uma tabela contendo informações dos alunos | Para cadastrar um grupo inteiro de uma única vez |
| x | 5 | Professor | ❓Define as datas de cada sprint❓ | Para definir o período de acesso dos alunos na plataforma |
| x | 6 | Aluno | Avalia os outros integrantes da equipe a cada sprint | Para atender os requisitos da API |
| x | 7 | Professor | Visualiza as médias das notas da equipe por sprint | Para ter um acompanhamento do desempenho |
| x | 8 | Aluno | Visualiza o histórico de avaliações | Para obter um feedback real no andamento do projeto |
| x | 9 | Professor | Edita os alunos e equipes ao longo do projeto | Para remanejar os dados

[→ Voltar ao topo](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md)

## 🖥️ Documentação

TODO

### Como utilizar o projeto

1. Entre na [página principal do projeto](https://github.com/SQLutions-FATEC/API-2-Semestre)
2. Em seu computador, crie uma pasta para se copiar o projeto
3. Através do seu terminal, acesse esta pasta e utilize o comando `git clone` com chave HTTPS ou SSH para copiar o projeto e seus arquivos para seu computador

![image](https://github.com/user-attachments/assets/7c8f3a2f-a50b-4b34-9dee-6a7fe1ad18ce)

4. TODO

[→ Voltar ao topo](https://github.com/SQLutions-FATEC/API-2-Semestre/blob/main/README.md)

## 🛠️ Tecnologias

As seguintes ferramentas, linguagens, bibliotecas e tecnologias foram usadas na construção do projeto:

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/) [![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/) [![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/seu-usuario) [![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/) [![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)](https://slack.com/) [![Google Docs](https://img.shields.io/badge/Google_Docs-4285F4?style=for-the-badge&logo=google-docs&logoColor=white)](https://docs.google.com/) [![Figma](https://img.shields.io/badge/Figma-0ACF83?style=for-the-badge&logo=figma&logoColor=white)](https://www.figma.com/) INTELLIJ?

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
