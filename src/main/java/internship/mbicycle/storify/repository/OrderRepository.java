package internship.mbicycle.storify.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import internship.mbicycle.storify.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order>  findAllByPurchaseDate (LocalDate date);

    Optional<Order> findOrderByUniqueCode (String uniqueCode);

    List<Order> findAllByProfileId(Long profileId);

    List<Order> findAllByDelivered(boolean isDelivered);

    List<Order> findAllByProfileIdAndDelivered(Long profileId, boolean isDelivered);

}
