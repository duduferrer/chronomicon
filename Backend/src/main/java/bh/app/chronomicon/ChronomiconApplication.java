package bh.app.chronomicon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ChronomiconApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChronomiconApplication.class, args);
	}

}
