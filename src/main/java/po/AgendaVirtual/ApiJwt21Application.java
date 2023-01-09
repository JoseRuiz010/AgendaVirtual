package po.AgendaVirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;
  
@SpringBootApplication
public class ApiJwt21Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiJwt21Application.class, args);
	}
	
	  @Bean
	    public SpringDataDialect springDataDialect() {
	        return new SpringDataDialect();
	    }
}
