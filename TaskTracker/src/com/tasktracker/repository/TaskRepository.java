package com.tasktracker.repository;

import bootstrap.Interfaces.Task;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskRepository {
    private final Gson gson;
    private final Path directory;
    private List<Task> cachedTasks = null;

    public TaskRepository() {
        this.directory = Path.of("storage/tasks.json");
        this.gson = new Gson();
    }

    public void saveToStorage(List<Task> tasks) {
        try {
            Files.writeString(
                this.directory,
                this.gson.toJson(tasks)
            );

            this.cachedTasks = new ArrayList<>(tasks);
        } catch (IOException e) {
            IO.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public  List<Task> getTasks() {
        if (this.cachedTasks == null) {
            this.cachedTasks = this.loadTasksFromDatasource();
        }

        return new ArrayList<>(this.cachedTasks);
    }

    private List<Task> loadTasksFromDatasource() {
        try {
            String contentRaw = Files.readString(this.directory);
            String content = contentRaw.isEmpty() ? this.gson.toJson(new ArrayList<Task>()) : contentRaw;
            JsonArray list = JsonParser.parseString(content).getAsJsonArray();

            return list.asList()
                    .stream()
                    .map(task -> this.gson.fromJson(task, Task.class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            IO.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public  List<Task> getTasksByStatus(String status) {
        return this.getTasks()
            .stream()
            .filter(task -> task.status.equals(status))
            .toList();
    }

    public  void create(String description) {
        List<Task> tasks = this.getTasks();
        int lastId = tasks.isEmpty() ? 0 : tasks.getLast().id;
        String currentTimestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Task task = new Task(
            lastId + 1,
            description,
            "todo",
            currentTimestamp,
            currentTimestamp
        );

        tasks.addLast(task);
        this.saveToStorage(tasks);

        System.out.printf(
            "Tarefa adicionada com sucesso (ID: %d)\n",
            task.id
        );
    }

    public  void delete(int taskId) {
        List<Task> tasks = this.getTasks();

        tasks.stream()
                .filter(task -> task.id != taskId)
                .findFirst()
                .ifPresentOrElse(
                        task -> {
                            this.saveToStorage(tasks);
                            System.out.printf(
                                "Tarefa eliminada com sucesso (ID: %d)\n",
                                taskId
                            );
                        },
                        () -> System.out.printf("Erro: Tarefa %d não encontrada\n", taskId)
                );
    }

    public  void updateStatus(int taskId, String status) {
        List<Task> tasks = this.getTasks();

        tasks.stream()
                .filter(T -> T.id == taskId)
                .findFirst()
                .ifPresentOrElse(
                        task -> {
                            if (Set.of("todo", "in-progress", "done").contains(status)) {
                                task.status = status;
                                this.saveToStorage(tasks);
                                System.out.printf("Estado da tarefa %d atualizado com sucesso\n", taskId);
                            } else {
                                System.out.printf("Estado %s não reconhecido\n", status);
                            }
                        },
                        () -> System.out.printf("Erro: A tarefa %d não existe\n", taskId)
                );
    }

    public void updateDescription(int taskId, String description) {
        List<Task> tasks = this.getTasks();

        tasks.stream()
                .filter(task -> task.id == taskId)
                .findFirst()
                .ifPresentOrElse(
                        task -> {
                            task.description = description;
                            this.saveToStorage(tasks);
                            System.out.printf("Descrição da tarefa %d atualizado com sucesso\n", taskId);
                        },
                        () -> System.out.printf("Erro: A tarefa %d não existe\n", taskId)
                );
    }
}
