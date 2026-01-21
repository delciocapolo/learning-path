package bootstrap;

import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

public class Initialization {
    private final Path directory;

    public Initialization() {
        this.directory = Path.of("storage/tasks.json");

        // Bootstrap Methods
        this.load();
    }

    public void load() {
        try {
            Path parentDir = this.directory.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            if (!Files.exists(this.directory)) {
                Files.createFile(this.directory);
            }
        } catch (IOException e) {
            IO.println("Erro ao criar arquivo: " + e.getMessage());
        }
    }
}
