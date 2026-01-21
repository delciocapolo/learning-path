package handler;

import java.util.List;
import java.util.Scanner;
import com.tasktracker.repository.TaskRepository;

public class CLI {
    Scanner scanner;
    TaskRepository repository;

    public CLI() {
        this.scanner = new Scanner(System.in);
        this.repository = new TaskRepository();
        this.init();
    }

    private String readArgs() {
        System.out.print("task-cli ");
        return this.scanner.nextLine();
    }

    public void init() {
        String args = this.readArgs();
        List<String> command = List.of(args.trim().split("\\s+"));

        if (command.getFirst().equals("list")) {
            if (command.size() > 1) {
                String filter = command.get(1);

                switch (filter) {
                    case "done" -> this.repository
                            .getTasksByStatus("done")
                            .forEach(System.out::println);
                    case "todo" -> this.repository
                            .getTasksByStatus("todo")
                            .forEach(System.out::println);
                    case "in-progress" -> this.repository
                            .getTasksByStatus("in-progress")
                            .forEach(System.out::println);
                    default -> System.out.println("Filtro inválido. Use: done, todo ou in-progress");
                }
            } else {
                this.repository
                    .getTasks()
                    .forEach(System.out::println);
            }
        } else if (command.getFirst().equals("add")) {
            if (command.size() > 1) {
                String description = String.join(" ", command.subList(1, command.size()));
                this.repository.create(description);
            } else {
                System.out.println("A descrição é obrigatória");
            }
        } else if (command.getFirst().equals("delete")) {
            if (command.size() > 1) {
                int taskId = Integer.parseInt(command.get(1));
                this.repository.delete(taskId);
            } else {
                System.out.println("O Id é obrigatório");
            }
        } else if (
            command.getFirst().equals("mark-in-progress") ||
            command.getFirst().equals("mark-done")
        ) {
            if (command.size() <= 1) {
                System.out.println("O Id é obrigatório");
            } else {
                int taskId = Integer.parseInt(command.get(1));

                if (command.getFirst().equals("mark-in-progress")) {
                    this.repository.updateStatus(taskId, "in-progress");
                } else {
                    this.repository.updateStatus(taskId, "done");
                }
            }
        } else if (command.getFirst().equals("update")) {
            if (command.size() > 2) {
                int taskId = Integer.parseInt(command.get(1));
                String description = String.join(" ", command.subList(2, command.size()));

                this.repository.updateDescription(taskId, description);
            } else {
                System.out.println("O Id e a descrição são obrigatórios");
            }
        } else {
            System.out.println("Commando não reconhecido");
        }
    }
}
