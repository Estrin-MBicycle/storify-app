package internship.mbicycle.storify;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestMariaDbClass implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        MariaDbConfigContainer.container.close();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        MariaDbConfigContainer.container.start();
    }
}
