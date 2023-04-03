# Junit_Mockito_Integration_test<br>

This repository contains two projects to practice Junit test, Mockito mock test and integration test for WebMVC, and WebFlux projects respectively. <br>

TDD stands for Test Driven Development which is a software development methodology. We need to test our application thoroughly before we push it to source code repo.
There are Controller layer, Service layer, and Repository layer in MVC architecture. And we have corresponding method to test each layer. <br>

**Repository Layer**<br>
We could use @DataJpaTest annotation to test repository layer, and it will not load any dependecies in application context container. Also it will automatically 
initialize an in-memory database H2 instead of using database on your local machine. The tests are followed by BDD style given_when_then.<br>

**Service Layer**<br>
Service layer depends on repository layer in MVC architecture. We use mock repository layer to eliminate distractions from such dependency. Annotation used in this
layer are @Mock, @InjectMock. The tests are also followed by BDD style given_when_then.<br>

**Controller Layer**<br>
Controller layer is dependent on Service Layer. And we use @WebMvcTest to test. We could also use Postman to test it manually after all controller layer just calls service layer normally.<br>

**Integration Test**<br>
Spring Boot provides @SpringBootTest annotation for Integration testing. This annotation creates an application context and load full application context. As the name suggests, integration tests focus on integrating different layers of the application. That also means no mocking is invoked. 
Basically, we write integration tests for testing a feature which may invoke interaction with multiple components. Like Employee Management Feature(Controller → Service → Repository)
