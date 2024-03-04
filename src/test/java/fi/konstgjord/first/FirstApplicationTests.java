package fi.konstgjord.first;

import fi.konstgjord.first.dto.CatDTO;
import fi.konstgjord.first.model.Color;
import fi.konstgjord.first.repository.ColorRepository;
import fi.konstgjord.first.service.CatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class FirstApplicationTests {
	static final Logger logger = LoggerFactory.getLogger(FirstApplicationTests.class);

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.3")
			.withDatabaseName("first")
			.withCopyFileToContainer(MountableFile.forHostPath("docs/db/structure.sql"), "/docker-entrypoint-initdb.d/schema.sql");
			//.withLogConsumer(new Slf4jLogConsumer(logger));

	@Autowired
	private CatService catService;

	@Autowired
	private ColorRepository colorRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testCatService() {
		logger.info("testCatService -- start");
		long nrOfCurrentCats = catService.getAllCats().size();
		CatDTO catDTO = new CatDTO(null, "Lenni", 1L);
		catService.createCat(catDTO);
		Assertions.assertEquals(nrOfCurrentCats + 1, catService.getAllCats().size(), "We should have one cat");
		logger.info("testCatService -- stop");
	}

	@Test
	void testCatService2() {
		logger.info("testCatService2 -- start");
		Assertions.assertEquals(0L, catService.getCatsByColorName("Orange").size(), "We should not have any orange cats");
		Color orange = colorRepository.findByColorName("Orange");
		CatDTO catDTO = new CatDTO(null, "Nugget", orange.getId());
		catService.createCat(catDTO);
		Assertions.assertEquals(1L, catService.getCatsByColorName("Orange").size(), "We should have one cat");
		logger.info("testCatService2 -- stop");
	}
}
