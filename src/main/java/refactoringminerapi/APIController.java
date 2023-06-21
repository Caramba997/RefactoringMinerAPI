package refactoringminerapi;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import refdiff.core.RefDiff;
import refdiff.core.diff.CstDiff;
import refdiff.core.diff.Relationship;
import refdiff.parsers.js.JsPlugin;

@RestController
@RequestMapping("/api")
public class APIController {

	  @PostMapping("/refactorings")
	  public ResponseEntity<Object> refactorings(@RequestBody RefactoringsRequest data) {
		  try {
			  CommitResults[] refactorings = processCommits(data.getRepo(), data.getOwner(), data.getCommits());
			  return ResponseEntity.ok(refactorings);
		  }
		  catch (Exception e) {
			  return ResponseEntity.badRequest().body(e.getCause());
		  }
	  }
	  
	  private CommitResults[] processCommits(String repo, String owner, String[] commits) throws Exception {
	        File tempFolder = new File("temp");

	        JsPlugin jsPlugin = new JsPlugin();
	        RefDiff refDiffJs = new RefDiff(jsPlugin);

	        File clonedRepo = refDiffJs.cloneGitRepository(new File(tempFolder, repo), "https://github.com/" + owner + "/" + repo + ".git");

	        // You can compute the relationships between the code elements in a commit with
	        // its previous commit. The result of this operation is a CstDiff object, which
	        // contains all relationships between CstNodes. Relationships whose type is different
	        // from RelationshipType.SAME are refactorings.
	        CommitResults[] results = new CommitResults[commits.length];
	        for (int i = 0; i < commits.length; i++) {
	        	String sha = commits[i];
	        	CommitResults currentResult = new CommitResults(sha);
	        	results[i] = currentResult;
	        	try {
	    	        CstDiff diffForCommit = refDiffJs.computeDiffForCommit(clonedRepo, sha);
	    	        currentResult.setRefactorings(diffForCommit.getRefactoringRelationships().toArray());
	        	}
	        	catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        }
	        return results;
	  }
	
}
