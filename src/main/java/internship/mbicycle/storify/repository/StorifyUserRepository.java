package internship.mbicycle.storify.repository;

import java.util.Optional;

import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StorifyUserRepository extends JpaRepository<StorifyUser, Long> {

    Optional<StorifyUser> findByEmail(String email);

    Optional<StorifyUser> findById(long id);

    Optional<StorifyUser> findByActivationCode(String code);

    Optional<StorifyUser> findByProfile(Profile profile);

    @Query(value = "select * " +
            "from storify_user " +
            "inner join user_token on storify_user.id = user_token.user_id " +
            "where user_token.token = ?1 "+
            "and user_token.user_agent = ?2", nativeQuery = true)
    Optional<StorifyUser> findStorifyUserByJwtTokenAndUserAgent(String token, String userAgent);
}
