
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b from Box b join b.messages s where b.name=?1 and s.sender.id=?2")
	Box findBoxByNameAndActor(String name, int actorId);

	@Query("select b from Actor a join a.boxes b where a.id=?1")
	Collection<Box> findBoxByActor(int actorId);

	@Query("select b from Actor a join a.boxes b where a.id=?1")
	Collection<Box> findBoxesByActorRecipient(int id);

}
