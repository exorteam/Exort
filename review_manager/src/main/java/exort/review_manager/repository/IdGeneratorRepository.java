package exort.review_manager.repository;

public interface IdGeneratorRepository {
    long nextId(String name);
    long resetId(String name, long maxId);
}
