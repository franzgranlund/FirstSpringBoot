package fi.konstgjord.first;

import fi.konstgjord.first.dto.CatDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class RestTemplateTest {
	static final Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.3")
			.withDatabaseName("first")
			.withCopyFileToContainer(MountableFile.forHostPath("docs/db/structure.sql"), "/docker-entrypoint-initdb.d/schema.sql");
			//.withLogConsumer(new Slf4jLogConsumer(logger));

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testCatService() {
		logger.info("testCatService -- start");
		CatDTO catDTO = new CatDTO(null, "Gunnar", 1L);

		ResponseEntity<CatDTO> createResponse = restTemplate.postForEntity("/cats", catDTO, CatDTO.class);
		assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

		ResponseEntity<CatDTO[]> getAllResponse = restTemplate.getForEntity("/cats", CatDTO[].class);
		assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
		CatDTO[] catsFromGetAll = getAllResponse.getBody();

		assertNotNull(catsFromGetAll);
		logger.info("XXX: {}", Arrays.toString(catsFromGetAll));

		logger.info("testCatService -- stop");
	}
}
