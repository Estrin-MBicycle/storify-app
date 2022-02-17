package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.service.GeneratorUniqueCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneratorUniqueCodeImpl implements GeneratorUniqueCode {

    @Override
    public String generationUniqueCode() {
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid).substring(0,8);
    }
}
