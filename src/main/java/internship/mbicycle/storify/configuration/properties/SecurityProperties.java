package internship.mbicycle.storify.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("jwt.token")
@Component
@Getter
@Setter
public class SecurityProperties {

    private String jwtSecret;
    private boolean checkAccess;
}
