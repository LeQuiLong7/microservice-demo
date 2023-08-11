package com.lql.microservice;

import com.lql.microservice.repository.ProductRepository;
import com.lql.microservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
class MicroserviceApplicationTests {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private  ProductService productService;
//
//	@Container
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
//
//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry propertyRegistry) {
//		propertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//	}
//
//	@Test
//	void createProduct() throws Exception {
//		String productJson = """
//				    {
//				        "name": "Iphonee",
//				        "description": "Iphone 13",
//				        "price": 2000.5
//				    }
//				""";
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(productJson))
//				.andDo(print())
//				.andExpect(status().isCreated());
//
//		Assertions.assertNotNull(productService.getAll());
//	}
//
//	@Test
//	void getProduct() throws Exception {
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")).andDo(print())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//		Assertions.assertNotNull(productService.getAll());
//	}


	@Test
	void test() {
		System.out.println(SpringVersion.getVersion());
	}

}
