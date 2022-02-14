package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorifyUserRepository extends JpaRepository<StorifyUser, Long> {

    Optional<StorifyUser> findByEmail(String email);

    Optional<StorifyUser> findById(long id);

    Optional<StorifyUser> findByActivationCode(String code);
}
