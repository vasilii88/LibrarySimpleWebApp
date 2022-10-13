package main.Dao;
import main.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskDao extends JpaRepository<Task, Integer> {

}

