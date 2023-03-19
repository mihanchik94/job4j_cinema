package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Repository
public class Sql2oFileRepository implements FileRepository {
    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<File> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("select * from files where id = :id")
                    .addParameter("id", id);
            File file = query.executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }

    @Override
    public Collection<File> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("select * from files");
            Collection<File> files = query.executeAndFetch(File.class);
            return files;
        }
    }
}