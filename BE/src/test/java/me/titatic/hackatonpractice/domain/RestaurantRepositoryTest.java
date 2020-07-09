package me.titatic.hackatonpractice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 레스토랑_하나를_저장한다() {
        Restaurant restaurant = Restaurant.builder()
            .name("restaurantName")
            .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        assertThat(savedRestaurant.getName()).isEqualTo("restaurantName");
    }
}
