package internship.mbicycle.storify.jpa;

import internship.mbicycle.storify.models.StorifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorifyUserRepository extends JpaRepository<StorifyUser, Long> {
    StorifyUser findById(long id);
}
