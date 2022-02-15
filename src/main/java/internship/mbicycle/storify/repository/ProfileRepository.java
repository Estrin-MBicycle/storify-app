package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
