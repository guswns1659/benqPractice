package me.titatic.hackatonpractice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("사용자와 피드를 저장하는 테스트")
    @CsvSource({"accountName, 2"})
    @ParameterizedTest
    void 유저와_피드를_저장한다(String accountName, String likeCount) {
        Account account = Account.builder()
            .name(accountName)
            .build();

        Feed feed = Feed.builder()
            .likeCount(Integer.parseInt(likeCount))
            .build();

        account.addFeed(feed);
        accountRepository.save(account);

        assertThat(account.getFeeds().iterator().next().getLikeCount()).isEqualTo(Integer.parseInt(likeCount));
    }
}
