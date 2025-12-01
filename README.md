# lanchonete-novo-final

src/main/java/com/lanchonete
│
├── config
│   → Classe responsável pela conexão com o banco (ConnectionFactory – Singleton)
│
├── controller
│   → Controladores que fazem a ponte entre a interface gráfica e a lógica de negócio.
│
├── database
│   → Controle de inicialização e acesso ao banco (Database.java).
│
├── model
│   → Entidades do sistema (Pedido, Produto, Vendedor, Pagamentos, etc.).
│
├── repository
│   → Interfaces e classes responsáveis por operações CRUD no banco de dados.
│
├── resources
│   → Arquivos auxiliares (script SQL, imagens, propriedades).
│
├── service
│   → Camada de lógica de negócio e regras do sistema.
│
├── util
│   → Utilitários gerais (validação, formatação, geração de relatórios TXT).
│
└── view
    → Telas Java Swing responsáveis pela interação visual com o usuário.

