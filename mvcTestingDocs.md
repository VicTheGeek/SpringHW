https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.spring-mvc-tests

Auto-configured Spring MVC Tests

To test whether Spring MVC controllers are working as expected, use the @WebMvcTest annotation. @WebMvcTest auto-configures the Spring MVC infrastructure and limits scanned beans to @Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, Filter, HandlerInterceptor, WebMvcConfigurer, WebMvcRegistrations, and HandlerMethodArgumentResolver. Regular @Component and @ConfigurationProperties beans are not scanned when the @WebMvcTest annotation is used. @EnableConfigurationProperties can be used to include @ConfigurationProperties beans.
A list of the auto-configuration settings that are enabled by @WebMvcTest can be found in the appendix.
If you need to register extra components, such as the Jackson Module, you can import additional configuration classes by using @Import on your test.

Often, @WebMvcTest is limited to a single controller and is used in combination with @MockitoBean to provide mock implementations for required collaborators.

@WebMvcTest also auto-configures MockMvc. Mock MVC offers a powerful way to quickly test MVC controllers without needing to start a full HTTP server. If AssertJ is available, the AssertJ support provided by MockMvcTester is auto-configured as well.
You can also auto-configure MockMvc and MockMvcTester in a non-@WebMvcTest (such as @SpringBootTest) by annotating it with @AutoConfigureMockMvc. The following example uses MockMvcTester:

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserVehicleController.class)
class MyControllerTests {

	@Autowired
	private MockMvcTester mvc;

	@MockitoBean
	private UserVehicleService userVehicleService;

	@Test
	void testExample() {
		given(this.userVehicleService.getVehicleDetails("sboot"))
			.willReturn(new VehicleDetails("Honda", "Civic"));
		assertThat(this.mvc.get().uri("/sboot/vehicle").accept(MediaType.TEXT_PLAIN))
			.hasStatusOk()
			.hasBodyTextEqualTo("Honda Civic");
	}

}

	If you need to configure elements of the auto-configuration (for example, when servlet filters should be applied) you can use attributes in the @AutoConfigureMockMvc annotation.

If you use HtmlUnit and Selenium, auto-configuration also provides an HtmlUnit WebClient bean and/or a Selenium WebDriver bean. The following example uses HtmlUnit:

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserVehicleController.class)
class MyHtmlUnitTests {

	@Autowired
	private WebClient webClient;

	@MockitoBean
	private UserVehicleService userVehicleService;

	@Test
	void testExample() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
		HtmlPage page = this.webClient.getPage("/sboot/vehicle.html");
		assertThat(page.getBody().getTextContent()).isEqualTo("Honda Civic");
	}

}

	By default, Spring Boot puts WebDriver beans in a special “scope” to ensure that the driver exits after each test and that a new instance is injected. If you do not want this behavior, you can add @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) to your WebDriver @Bean definition.
	The webDriver scope created by Spring Boot will replace any user defined scope of the same name. If you define your own webDriver scope you may find it stops working when you use @WebMvcTest.

If you have Spring Security on the classpath, @WebMvcTest will also scan WebSecurityConfigurer beans. Instead of disabling security completely for such tests, you can use Spring Security’s test support. More details on how to use Spring Security’s MockMvc support can be found in this Testing With Spring Security “How-to Guides” section.
Sometimes writing Spring MVC tests is not enough; Spring Boot can help you run full end-to-end tests with an actual server. 