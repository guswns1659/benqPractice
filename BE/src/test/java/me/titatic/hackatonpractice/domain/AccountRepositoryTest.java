package me.titatic.hackatonpractice.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
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
import me.titatic.hackatonpractice.domain.account.Challenge;
import me.titatic.hackatonpractice.domain.account.Option;
import me.titatic.hackatonpractice.domain.account.PointHistory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("사용자와 위치정보를 가진 피드를 저장하는 테스트")
    @CsvSource({"accountName, 3, 4, 2, projectName"})
    @ParameterizedTest
    void 유저와_피드를_저장한다(String accountName, Integer todayCount, Integer ecoPoint, Integer likeCount,
        String projectName) throws ParseException {
        Account account = Account.builder()
            .name(accountName)
            .todayCount(todayCount)
            .ecoPoint(ecoPoint)
            .build();

        Double latitude = 32.123;
        Double longitude = 127.123;
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        Point point = (Point) new WKTReader().read(pointWKT);

        Challenge challenge = Challenge.builder()
            .likeCount(likeCount)
            .point(point)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        PointHistory pointHistory = PointHistory.builder()
            .ecoPoint(ecoPoint)
            .projectName(projectName)
            .createdAt(new Date())
            .pointOption(Option.SAVE)
            .build();

        account.addPointHistory(pointHistory);
        account.addChallenge(challenge);
        accountRepository.save(account);

        assertThat(account.getChallenges().iterator().next().getLikeCount()).isEqualTo(likeCount);
        assertThat(account.getChallenges().iterator().next().getPoint().getX()).isEqualTo(longitude);
        assertThat(account.getPointHistories().iterator().next().getEcoPoint()).isEqualTo(ecoPoint);
        assertThat(account.getPointHistories().iterator().next().getPointOption().getOption()).isEqualTo(Option.SAVE.getOption());
    }
}
