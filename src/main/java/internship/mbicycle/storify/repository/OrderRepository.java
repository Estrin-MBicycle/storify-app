package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order>  findAllByDate (LocalDate date);

    Optional<Order> findOrderByUniqueCode (String uniqueCode);

    List<Order> findAllByProfileId(Long profileId);

}
