package internship.mbicycle.storify.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("spring.mail")
@Component
@Getter
@Setter
public class MailProperties {

    private String host;
    private String username;
    private String password;
    private int port;
    private String protocol;
}
