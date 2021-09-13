package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.PersonasController;
import com.example.demo.model.PersonasModel;
import com.example.demo.repository.PersonasRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JorgitoApplicationTests {
	@MockBean
	private PersonasRepository personasRepository;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private PersonasController personasController;
	

	@Test
	void contextLoads() {
		assertThat(personasController).isNotNull();
	}

	@Test
	public void canRetrieveAll() {
		ResponseEntity<PersonasModel> personasResponse = restTemplate.getForEntity("/api/persona/1",
				PersonasModel.class);
			assertThat(personasResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

    @Test
    public void headerIsPresent() throws Exception {
        // when
        ResponseEntity<PersonasModel> personasResponse = restTemplate.getForEntity("/api/personas_apellido", PersonasModel.class);

        // then
        assertThat(personasResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

	
}
