# API de Tarefas - Aplicação Full-Stack de Gerenciamento de Tarefas

Uma aplicação moderna de gerenciamento de tarefas full-stack construída com Spring Boot (backend) e React com Vite (frontend). Este projeto demonstra uma API CRUD completa integrada com uma interface web responsiva para gerenciar tarefas diárias.

## Funcionalidades

### Backend (Spring Boot)
- **API RESTful** para gerenciamento de tarefas
- **Operações CRUD**: Criar, Ler, Atualizar, Deletar tarefas
- **Integração com Banco de Dados MySQL** com JPA/Hibernate
- **Configuração CORS** para integração com frontend
- **Lombok** para código limpo
- **Tratamento de Erros** e validação

### Frontend (React + Vite)
- **UI Responsiva** construída com React 19
- **Estilização Moderna** com Tailwind CSS
- **Gerenciamento de Tarefas em Tempo Real**: Adicionar, editar, deletar e marcar tarefas como concluídas
- **Estados de Carregamento** e tratamento de erros
- **Lembretes Simulados** para tarefas
- **Contador de Tarefas Pendentes**

### Funcionalidades Principais
- ✅ Criar novas tarefas com título e descrição
- 📝 Editar tarefas existentes
- 🗑️ Deletar tarefas
- ✅ Marcar tarefas como concluídas/incompletas
- 📊 Visualizar contagem de tarefas pendentes
- 🔔 Criar lembretes (simulado)
- 🗂️ Anexar arquivos (simulado)
- 🔄 Atualizações em tempo real após operações

## Stack Tecnológico

### Backend
- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Data JPA**
- **Spring Web MVC**
- **Banco de Dados MySQL**
- **Lombok**
- **Maven**

### Frontend
- **React 19**
- **Vite** (ferramenta de build)
- **Axios** (cliente HTTP)
- **Tailwind CSS** (estilização)
- **ESLint** (linting de código)

## Pré-requisitos

Antes de executar esta aplicação, certifique-se de ter os seguintes itens instalados:

- **Java 21** ou superior
- **Node.js** (versão 18 ou superior)
- **Servidor MySQL**
- **Maven** (geralmente vem com Spring Boot)
- **Git**

## Configuração do Banco de Dados

Este projeto utiliza **MySQL** como banco de dados principal para persistência de dados, escolhida por sua confiabilidade, performance e ampla adoção em aplicações empresariais.

1. **Instale e inicie o Servidor MySQL**
   - Baixe e instale o MySQL Server da [página oficial](https://dev.mysql.com/downloads/mysql/)
   - Inicie o serviço MySQL

2. **Crie o banco de dados da aplicação**:
   ```sql
   CREATE DATABASE tarefas_db;
   ```

3. **Configure as credenciais de forma segura**:
   - Copie `application.properties.example` para `application.properties`
   - Edite as credenciais do seu banco MySQL
   - **Nunca commite o arquivo `application.properties` real no Git!**

**⚠️ Segurança**: As credenciais no `application.properties` são apenas para desenvolvimento local. Para produção, use variáveis de ambiente ou serviços de configuração seguros.

## Segurança e Boas Práticas

### Arquivos Sensíveis
- **Nunca commite** `application.properties` com credenciais reais
- Use `application.properties.example` como template
- Configure variáveis de ambiente para produção

### Arquivos Ignorados
- Arquivos de IDE (`.idea/`, `.vscode/`) são ignorados
- Logs e arquivos temporários são ignorados
- `node_modules/` e `target/` são ignorados
### Melhorias Implementadas
- ✅ **Validação de entrada** com Bean Validation
- ✅ **Tratamento de erros padronizado** com GlobalExceptionHandler
- ✅ **Testes unitários** para Service e Controller
- ✅ **Docker** para containerização
- ✅ **Documentação aprimorada** da API
### Desenvolvimento Seguro
- Use senhas fortes para o banco de dados
- Configure CORS apenas para domínios necessários
- Mantenha dependências atualizadas

4. **Estrutura da tabela** (criada automaticamente pelo JPA/Hibernate):
   ```sql
   CREATE TABLE tarefas (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     titulo VARCHAR(255) NOT NULL,
     descricao VARCHAR(1000),
     concluida BOOLEAN NOT NULL DEFAULT FALSE
   );
   ```

## 🧪 Testes

### Backend Tests
```bash
cd tarefas-backend
./mvnw test
```

**Testes implementados:**
- ✅ Testes unitários para `TarefaService`
- ✅ Testes de integração para `TarefaController`
- ✅ Cobertura de cenários positivos e negativos
- ✅ Validação de regras de negócio

### Frontend Tests
```bash
cd tarefas-web
npm test
```

## 🐳 Docker (Opcional)

### Desenvolvimento com Docker
```bash
cd tarefas-backend
docker-compose up --build
```

Isso iniciará:
- **MySQL** na porta 3306
- **Backend Spring Boot** na porta 8080

### Produção
```bash
cd tarefas-backend
docker build -t tarefas-api .
docker run -p 8080:8080 tarefas-api
```

## Instalação e Execução

### Configuração do Backend

1. Navegue para o diretório do backend:
   ```bash
   cd tarefas-backend
   ```

2. Instale dependências e execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

   O backend será iniciado em `http://localhost:8080`

### Configuração do Frontend

1. Navegue para o diretório do frontend:
   ```bash
   cd tarefas-web
   ```

2. Instale dependências:
   ```bash
   npm install
   ```

3. Inicie o servidor de desenvolvimento:
   ```bash
   npm run dev
   ```

   O frontend será iniciado em `http://localhost:5173`

## Documentação da API

O backend fornece uma API REST com os seguintes endpoints:

### URL Base: `http://localhost:8080/tarefas`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/tarefas` | Obter todas as tarefas |
| GET | `/tarefas/{id}` | Obter tarefa por ID |
| POST | `/tarefas` | Criar uma nova tarefa |
| PUT | `/tarefas/{id}` | Atualizar uma tarefa existente |
| DELETE | `/tarefas/{id}` | Deletar uma tarefa |

### Modelo de Tarefa
```json
{
  "id": 1,
  "titulo": "Título da Tarefa",
  "descricao": "Descrição da Tarefa",
  "concluida": false
}
```

### Exemplos de Chamadas da API

**Criar uma Tarefa:**
```bash
curl -X POST http://localhost:8080/tarefas \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Aprender React",
    "descricao": "Estudar fundamentos do React",
    "concluida": false
  }'
```

**Obter Todas as Tarefas:**
```bash
curl http://localhost:8080/tarefas
```

## Funcionalidades do Frontend

O frontend React fornece uma interface intuitiva para gerenciamento de tarefas:

- **Lista de Tarefas**: Exibe todas as tarefas com status de conclusão
- **Formulário de Adição**: Criar novas tarefas com título e descrição
- **Modo de Edição**: Clique em editar para modificar tarefas existentes
- **Confirmação de Exclusão**: Remover tarefas com confirmação
- **Alternar Conclusão**: Clique em checkboxes para marcar tarefas como feitas/não feitas
- **Contador Pendente**: Mostra número de tarefas incompletas
- **Botão de Lembrete**: Criação de lembretes simulada
- **Design Responsivo**: Funciona em desktop e dispositivos móveis

## Estrutura do Projeto

```
tarefas-api/
├── tarefas-backend/          # Backend Spring Boot
│   ├── src/main/java/com/arthurpfurtado/tarefas_api/
│   │   ├── controller/       # Controladores REST
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositórios de dados
│   │   └── service/         # Lógica de negócio
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── tarefas-web/              # Frontend React
│   ├── src/
│   │   ├── App.jsx          # Componente principal
│   │   ├── api.js           # Configuração da API
│   │   └── main.jsx         # Ponto de entrada da aplicação
│   ├── package.json
│   └── vite.config.js
└── README.md
```

## Desenvolvimento

### Desenvolvimento do Backend
- Usa Spring Boot com auto-configuração
- JPA/Hibernate para ORM
- Banco H2 para testes (pode ser configurado)
- Maven para gerenciamento de dependências

### Desenvolvimento do Frontend
- Recarregamento automático com Vite
- ESLint para qualidade do código
- Tailwind CSS para estilização
- Axios para comunicação com API

## Implantação

### Implantação do Backend
Construa o arquivo JAR:
```bash
cd tarefas-backend
./mvnw clean package
java -jar target/tarefas-api-0.0.1-SNAPSHOT.jar
```

### Implantação do Frontend
Construa para produção:
```bash
cd tarefas-web
npm run build
npm run preview
```

## Contribuição

1. Faça um fork do repositório
2. Crie uma branch de funcionalidade (`git checkout -b feature/recurso-incrivel`)
3. Faça commit das suas mudanças (`git commit -m 'Adiciona recurso incrível'`)
4. Faça push para a branch (`git push origin feature/recurso-incrivel`)
5. Abra um Pull Request

## Licença

Este projeto é open source e está disponível sob a [Licença MIT](LICENSE).

## 👨‍💻 Autor

**Arthur Pereira Furtado** - Desenvolvedor de Software

*Construído com ❤️ como demonstração de habilidades de desenvolvimento full-stack*

---

*Este README apresenta uma aplicação full-stack completa com tecnologias modernas, demonstrando habilidades em Java, Spring Boot, React, APIs REST, integração com banco de dados e design responsivo.*