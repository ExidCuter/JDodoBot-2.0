package xyz.the_dodo.database.interfaces.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.the_dodo.database.types.DeletedMessage;

@Repository
public interface IDeletedMessageRepo extends JpaRepository<DeletedMessage, Long> {
}
