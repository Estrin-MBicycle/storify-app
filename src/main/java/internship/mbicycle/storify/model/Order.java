package internship.mbicycle.storify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDate date;

    private Integer price;

    @Column(name = "unique_code")
    private String uniqueCode;

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH,
            CascadeType.PERSIST})
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList = new ArrayList<>();

}
