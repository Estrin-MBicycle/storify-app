package internship.mbicycle.storify.integration.repository;

import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorifyUserRepository extends JpaRepository<StorifyUser, Long> {

    Optional<StorifyUser> findByEmail(String email);

    Optional<StorifyUser> findByActivationCode(String code);
}
