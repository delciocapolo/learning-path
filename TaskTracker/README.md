# Habilidades a adquirir
1. Filesystem
2. Input/Output user interaction

# FLow  - [1]
1. A aplicação é inicializada - [x]
2. Verifica se o arquivo tasks.json existe no directorio store/ - [x]
3. Caso não exista, deve criar o directório e o arquivo tasks.json - [x]
4. Caso exista, deve carregar todas as tarefas e exibir no usuário todas as tarefas persistidas - [x]

# FLow  - [2]
1. Quando a aplicação inicializa, preciso conseguir interagir com o terminal. Desde: 
   2. listar tarefas e visualizar o seu estado; 
   3. editar tarefa(desde o estado, até outras propriedades); 
   4. eliminar tarefa;
   5. mudar o estado da tarefa;

# Estructura do arquivo tasks
```json
    [
        {
            "id": "number",
            "description": "string",
            "createdAt": "date",
            "status": ["new", "running", "completed"]
        }
    ]
```

# RNF
1 - o sistem deve ser capaz de ler argumentos de entrada
2 - o sistema deve ser capaz de reconhecer argumentos como: add, update, delete and list (list tasks done, in progress and not done)

# Observação
Para saber mais, dê uma olhada em: [Roadmap - task-tracker](https://roadmap.sh/projects/task-tracker)