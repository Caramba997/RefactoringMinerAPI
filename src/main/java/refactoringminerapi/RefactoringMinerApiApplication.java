package refactoringminerapi;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RefactoringMinerApiApplication {
	
	private APIController apiController = new APIController();;

	public static void main(String[] args) {
		SpringApplication.run(RefactoringMinerApiApplication.class, args);
	}

}
