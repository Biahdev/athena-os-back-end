package dev.abeatriz.athena_os;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "jwt.public.key=classpath:app.pub",
        "jwt.private.key=classpath:app.key",
        "jwt.secret.admin=123",
        "jwt.cookie.name=123",
        "jwt.cookie.expiration=200",
        "front.url=localhost:4000"
})
class AthenaOsApplicationTests {

    @Test
    void contextLoads() {

    }

}
