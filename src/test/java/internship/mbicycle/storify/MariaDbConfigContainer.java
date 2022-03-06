package internship.mbicycle.storify;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import internship.mbicycle.storify.util.PropertiesUtil;
import org.testcontainers.containers.MariaDBContainer;

public class MariaDbConfigContainer {

    public static final MariaDBContainer<?> container = new MariaDBContainer<>("mariadb:latest")
            .withUsername(PropertiesUtil.getProperty("spring.datasource.username"))
            .withPassword(PropertiesUtil.getProperty("spring.datasource.password"))
            .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig()
                    .withPortBindings(new PortBinding(Ports.Binding.bindPort(3308), ExposedPort.tcp(3306))));
}
