package internship.mbicycle.storify.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate;

    private Integer price;

    private String uniqueCode;

    private Long profileId;

    private boolean delivered;

    @ElementCollection
    @CollectionTable(name = "product_purchase",
        joinColumns = @JoinColumn(name = "purchase", referencedColumnName = "id"))
    @MapKeyColumn(name = "product")
    @Column(name = "count")
    private Map<Long, Integer> products;
}
