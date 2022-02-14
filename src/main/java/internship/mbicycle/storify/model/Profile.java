package internship.mbicycle.storify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(exclude = {"basket", "stores", "favorite", "orders"})
@ToString(exclude = {"basket", "stores", "favorite", "orders"})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String town;
    private String address;
    private String phone;


//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "basket_id")
//    @JsonIgnore
//    private Basket basket;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Store> stores;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name="favorite",
//            joinColumns={@JoinColumn(name="profile_id")},
//            inverseJoinColumns={@JoinColumn(name="product_id")})
//    @JsonIgnore
//    private List<Product> favorite;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Order> orders;

}
