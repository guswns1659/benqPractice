package me.titatic.hackatonpractice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class ProjectRepositoryTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProjectRepository projectRepository;

    @LocalServerPort
    private int port;

    @DisplayName("기업과 프로젝트를 저장하는 테스트")
    @CsvSource({"corporation, project1, project2"})
    @ParameterizedTest
    void 프로젝트저장소로_프로젝트와_기업을_저장한다(String corporationName, String projectName1, String projectName2) {
        Corporation corporation = Corporation.builder()
            .name(corporationName)
            .build();

        Project project1 = Project.builder()
            .name(projectName1)
            .build();

        Project project2 = Project.builder()
            .name(projectName2)
            .build();

        corporation.addProject(project1);
        corporation.addProject(project2);
        Project savedProject1 = projectRepository.save(project1);
        Project savedProject2 = projectRepository.save(project2);

        assertThat(savedProject1.getCorporation().getName()).isEqualTo(corporationName);
        assertThat(savedProject2.getCorporation().getName()).isEqualTo(corporationName);
    }

    @DisplayName("유저와 프로젝트를 저장하는 테스트")
    @CsvSource({"accountName1, projectName1"})
    @ParameterizedTest
    void 유저와_프로젝트를_중간테이블로_저장한다(String accountName, String projectName1) {
        Account account = Account.builder()
            .name(accountName)
            .build();

        Project project1 = Project.builder()
            .name(projectName1)
            .build();

        project1.addAccount(account);
        Project savedProject = projectRepository.save(project1);

        assertThat(savedProject.getProjectAccounts().size()).isEqualTo(1);
        assertThat(savedProject.getProjectAccounts().iterator().next().getAccount().getName()).isEqualTo(accountName);
    }
}