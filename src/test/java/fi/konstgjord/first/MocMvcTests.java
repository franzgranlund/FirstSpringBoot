package fi.konstgjord.first;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.konstgjord.first.dto.CatDTO;
import fi.konstgjord.first.service.CatService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
class MocMvcTests {
	static final Logger logger = LoggerFactory.getLogger(MocMvcTests.class);

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.3")
			.withDatabaseName("first")
			.withCopyFileToContainer(MountableFile.forHostPath("docs/db/structure.sql"), "/docker-entrypoint-initdb.d/schema.sql");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CatService catService;

	@Test
	void testCatControllerWithMockMvc() throws Exception {
		logger.info("testCatControllerWithMockMvc - start");
		CatDTO requestDTO = new CatDTO(null, "Misu", 2L);

		mockMvc.perform(post("/cats")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDTO)))
				.andExpect(status().isCreated());

		Optional<CatDTO> oCatDTO = catService.getCatById(1L);
		assertTrue(oCatDTO.isPresent());
		assertEquals("Misu", oCatDTO.map(CatDTO::name).orElse(null));

		logger.info("testCatControllerWithMockMvc - stop");
	}
}
