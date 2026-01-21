package bootstrap.Interfaces;

public class Task {
    public int id;
    public String description;
    public String status;
    public String createdAt;
    public String updatedAt;

    public Task(
            int id,
            String description,
            String status,
            String createdAt,
            String updatedAt
    ) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        // return new Gson().toJson(this);

        return String.format(
                "{\"id\": %d, \"description\": \"%s\", \"status\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": \"%s\"}",
                this.id, this.description, this.status, this.createdAt, this.updatedAt
        );
    }
}
