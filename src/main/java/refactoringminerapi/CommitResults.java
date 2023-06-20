package refactoringminerapi;

import lombok.Data;
import refdiff.core.diff.Relationship;

@Data
public class CommitResults {
	
	public CommitResults(String sha) {
		commitSha = sha;
		refactorings = new Relationship[0];
	}

	private String commitSha;
	
	private Object[] refactorings;
	
}
