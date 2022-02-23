package internship.mbicycle.storify.model;

import internship.mbicycle.storify.dto.PurchasedAndNotPaidProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(
        name = "find_most_purchased_products",
        query = "select p.product_name name, sum(p.price*pp.count) profit "+
        "from purchase pu "+
        "inner join product_purchase pp on pu.id = pp.purchase "+
        "inner join product p on pp.product = p.id "+
        "where p.store_id = :id "+
        "group by name "+
        "order by sum(p.price*pp.count) desc "+
        "limit :limit",
        resultSetMapping = "most_purchased_products"),
        @NamedNativeQuery(
                name = "find_least_purchased_products",
                query = "select p.product_name name, sum(p.price*pp.count) profit "+
                        "from purchase pu "+
                        "inner join product_purchase pp on pu.id = pp.purchase "+
                        "inner join product p on pp.product = p.id "+
                        "where p.store_id = :id "+
                        "group by name "+
                        "order by sum(p.price*pp.count) "+
                        "limit :limit",
                resultSetMapping = "least_purchased_products")
})
@SqlResultSetMappings({
        @SqlResultSetMapping(
        name="most_purchased_products",
        classes = @ConstructorResult(
                targetClass = PurchasedAndNotPaidProduct.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "profit", type = Long.class)
                }
        )),
        @SqlResultSetMapping(
                name="least_purchased_products",
                classes = @ConstructorResult(
                        targetClass = PurchasedAndNotPaidProduct.class,
                        columns = {
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "profit", type = Long.class)
                        }
                ))
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"profile", "products"})
@ToString(exclude = {"profile", "products"})
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    private String description;

    private String address;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(targetEntity=Product.class, cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "store")
    private List<Product> products;

}