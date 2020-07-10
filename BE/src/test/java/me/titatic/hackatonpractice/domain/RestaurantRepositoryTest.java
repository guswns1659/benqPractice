package me.titatic.hackatonpractice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @CsvSource({"restaurantName, description"})
    @ParameterizedTest
    void 레스토랑_하나를_저장한다(String restaurantName, String description) throws ParseException {

        Double latitude = 32.123;
        Double longitude = 127.123;
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        Point point = (Point) new WKTReader().read(pointWKT);

        Restaurant restaurant = Restaurant.builder()
            .name(restaurantName)
            .description(description)
            .point(point)
            .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        assertThat(savedRestaurant.getName()).isEqualTo("restaurantName");
    }
}
