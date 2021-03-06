package xyz.the_dodo.bot.tests.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.the_dodo.REST.service.PrefixServiceImpl;
import xyz.the_dodo.database.interfaces.repos.IPrefixRepo;
import xyz.the_dodo.database.interfaces.repos.IServerRepo;
import xyz.the_dodo.database.interfaces.services.IPrefixService;
import xyz.the_dodo.database.types.Prefix;
import xyz.the_dodo.database.types.Server;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource({"/h2-test.properties"})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql({"/testData/servers.sql"})
public class PrefixServiceImplTests {
    @Autowired
    private IPrefixRepo prefixRepo;

    @Autowired
    private IServerRepo serverRepo;

    private IPrefixService prefixService;


    @PostConstruct
    public void setup() {
        PrefixServiceImpl service;

        service = new PrefixServiceImpl();

        service.setPrefixRepo(prefixRepo);

        prefixService = service;
    }

    @Test
    public void test_findAll() {
        List<Prefix> prefixes;

        prefixes = prefixService.findAll();

        assertThat(prefixes).isNotNull()
                .extracting("id", "server.id", "prefix")
                .contains(
                        tuple(1L, 1L, "#"),
                        tuple(2L, 2L, "_")
                );
    }

    @Test
    public void test_findByServerId() {
        Prefix prefix;

        prefix = prefixService.getByServerDiscordId("00000000000000");

        assertThat(prefix).isNotNull()
                .extracting("id", "server.id", "prefix")
                .contains(1L, 1L, "#");
    }

    @Test
    public void test_save() {
        Server server;
        Prefix prefix;

        prefix = new Prefix();
        server = serverRepo.findById(2L).get();

        prefix.setPrefix("%");
        prefix.setServer(server);

        prefixService.save(prefix);

        prefix = prefixService.findById(3L);

        assertThat(prefix).isNotNull()
                .extracting("id", "server.id", "prefix")
                .contains(3L, 2L, "%");
    }
}
