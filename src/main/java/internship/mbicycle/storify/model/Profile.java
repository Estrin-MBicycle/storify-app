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
@EqualsAndHashCode(exclude = {"cart", "stores", "favorite", "purchase"})
@ToString(exclude = {"cart", "stores", "favorite", "purchase"})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String town;
    private String address;
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Store> stores;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "favorite",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> favorite;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileId")
    @JsonIgnore
    private List<Purchase> purchase;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

}