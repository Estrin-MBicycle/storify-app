package internship.mbicycle.storify.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Store> stores;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name="favorite",
            joinColumns={@JoinColumn(name="profile_id")},
            inverseJoinColumns={@JoinColumn(name="product_id")})
    private List<Product> favorite;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileId")
    @JsonIgnore
    private List<Purchase> purchases;

}
