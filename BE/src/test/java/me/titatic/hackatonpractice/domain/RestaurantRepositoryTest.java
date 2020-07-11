package me.titatic.hackatonpractice.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import me.titatic.hackatonpractice.domain.restaurant.Restaurant;
import me.titatic.hackatonpractice.domain.restaurant.RestaurantRepository;
import me.titatic.hackatonpractice.utils.CardinalDirection;
import me.titatic.hackatonpractice.utils.GeometryUtils;
import me.titatic.hackatonpractice.utils.Location;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class RestaurantRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @CsvSource({"restaurantName, description"})
    @ParameterizedTest
    void 레스토랑_하나를_저장한다(String restaurantName, String description) throws ParseException {
        Double latitude = 37.51435;
        Double longitude = 127.12215;
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

    @DisplayName("nKm 이내의 식당을 구하는 테스트")
    @Test
    void 범위_내_식당을_구한다() {
        Double baseLatitude = 37.51573;
        Double baseLongitude = 127.11835;
        int distance = 1;

        // 북동쪽 좌표 구하기
        Location northEast = GeometryUtils.calculateByDirection(baseLatitude, baseLongitude, distance, CardinalDirection.NORTHEAST
            .getBearing());
        Location southWest = GeometryUtils.calculateByDirection(baseLatitude, baseLongitude, distance, CardinalDirection.SOUTHWEST
            .getBearing());

        double x1 = northEast.getLongitude();
        double y1 = northEast.getLatitude();
        double x2 = southWest.getLongitude();
        double y2 = southWest.getLatitude();

        // native query 활용
        Query query = entityManager.createNativeQuery("" +
            "SELECT r.id, r.name, r.description, r.point \n" +
            "FROM restaurant AS r \n" +
            "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2) + ", r.point)"
        , Restaurant.class);

        // query.setParameter(1, baseLongitude);
        // query.setParameter(2, baseLatitude);
        // query.setParameter(3, distance * 1000);

        List<Restaurant> result = query.getResultList();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(BigInteger.valueOf(5));
    }
}
