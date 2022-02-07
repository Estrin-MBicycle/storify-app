package internship.mbicycle.storify.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String surname;
    private String town;
    private String adress;
    private String phone;

    /*@OneToMany
    private List<Store> stores;

    @OneToOne
    private Basket basket;

    @OneToOne
    private Favorite favorite;

    @OneToMany
    private List<Order> orders;*/
}
