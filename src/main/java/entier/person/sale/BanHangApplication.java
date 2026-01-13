package entier.person.sale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
		scanBasePackages = {"entier.person.sale"}
)
public class BanHangApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanHangApplication.class, args);
	}

}
