package internship.mbicycle.storify.models;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "storify_user")
@Data
@ApiModel(value = "user")
public class StorifyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String email;

}
