package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {

   Optional<Basket> findByProfileId(Long profileId);
}
