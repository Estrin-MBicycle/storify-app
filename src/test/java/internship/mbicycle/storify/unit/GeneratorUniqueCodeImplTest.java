package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.service.impl.GeneratorUniqueCodeImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneratorUniqueCodeImplTest {

    private GeneratorUniqueCodeImpl generatorUniqueCode;

    @BeforeEach
    void setUp() {
        generatorUniqueCode = new GeneratorUniqueCodeImpl();
    }

    @Test
    void generationUniqueCode() {
        String firstCode = generatorUniqueCode.generationUniqueCode();
        String secondCode = generatorUniqueCode.generationUniqueCode();
        Assertions.assertNotEquals(firstCode, secondCode);
    }
}
