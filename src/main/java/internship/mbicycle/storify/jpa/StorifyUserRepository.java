package internship.mbicycle.storify.jpa;

import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorifyUserRepository extends JpaRepository<StorifyUser, Long> {

    StorifyUser findByEmail(String email);

    StorifyUser findById(long id);

    StorifyUser findByActivationCode(String code);
}
