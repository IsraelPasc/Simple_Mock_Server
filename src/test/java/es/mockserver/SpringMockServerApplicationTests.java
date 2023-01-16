package es.mockserver;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringMockServerApplicationTests {

    private List<String> testList;
    
	@Test
    @DisplayName("Context loads")
	void contextLoads() {
        assertTrue(true);
	}

}
