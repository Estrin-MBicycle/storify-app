package internship.mbicycle.storify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Basket {
    @Id
    private long id;
    private long idUser;

//    private List<Product> productList;

}
