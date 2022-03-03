package internship.mbicycle.storify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "product_cart",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> productsMap;
}