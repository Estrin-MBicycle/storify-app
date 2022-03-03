package internship.mbicycle.storify.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private String image;

    private String description;

    private Integer price;

    private Integer count;

    @ManyToOne(targetEntity = Store.class)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToMany(mappedBy = "favorite", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH,
        CascadeType.MERGE})
    private List<Profile> profiles;


}
