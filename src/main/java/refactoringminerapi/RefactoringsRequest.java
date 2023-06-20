package refactoringminerapi;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RefactoringsRequest {
	
	@NotNull
	private String repo;

	@NotNull
	private String owner;

	@NotNull
	private String[] commits;

}
