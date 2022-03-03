package internship.mbicycle.storify.repository;

import java.util.Optional;

import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorifyUserRepository extends JpaRepository<StorifyUser, Long> {

    Optional<StorifyUser> findByEmail(String email);

    Optional<StorifyUser> findById(long id);

    Optional<StorifyUser> findByActivationCode(String code);

    Optional<StorifyUser> findByProfile(Profile profile);
}
