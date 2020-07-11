package me.titatic.hackatonpractice.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import me.titatic.hackatonpractice.domain.account.Account;
import me.titatic.hackatonpractice.domain.account.AccountRepository;
import me.titatic.hackatonpractice.domain.account.Feed;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("사용자와 위치정보를 가진 피드를 저장하는 테스트")
    @CsvSource({"accountName, 2"})
    @ParameterizedTest
    void 유저와_피드를_저장한다(String accountName, Integer likeCount) throws ParseException {
        Account account = Account.builder()
            .name(accountName)
            .build();

        Double latitude = 32.123;
        Double longitude = 127.123;
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        Point point = (Point) new WKTReader().read(pointWKT);

        Feed feed = Feed.builder()
            .likeCount(likeCount)
            .point(point)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        account.addFeed(feed);
        accountRepository.save(account);

        assertThat(account.getFeeds().iterator().next().getLikeCount()).isEqualTo(likeCount);
        assertThat(account.getFeeds().iterator().next().getPoint().getX()).isEqualTo(longitude);
    }
}
