package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

public class GetRepoMetadata {

    File localPath = null;
    public Git getRepository(String name) throws IOException, URISyntaxException, GitAPIException {
       localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // clone
        System.out.println("Cloning ...");
        try (Git result = Git.cloneRepository()
                .setURI(name)
                .setBranchesToClone(null)
                .setCloneAllBranches(false)
                .setDirectory(localPath)
                .setNoCheckout(true)
                .setBare(true)
                .call()) {
           System.out.println("Done.");
        }catch (GitAPIException e) {
            System.err.println("Git API Exception: " + e.getMessage());
        }

        //then use the repository
        return Git.open(localPath);
    }
    void basicDetails(Git repo) throws IOException, GitAPIException {
        System.out.println("\n");
        System.out.println("Repository Size: " + repo.getRepository().getDirectory().length());
		System.out.println("Repository Last Modified: " + new Date(repo.getRepository().getDirectory().lastModified() * 1000L));
        System.out.println("Repository Head: " + repo.getRepository().getFullBranch());
    }
    void getCommits(Git repo) throws GitAPIException {
        List<Ref> branches = repo.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        try {
//            RevWalk walk = new RevWalk(repo.getRepository());
            for(Ref branch : branches) {
                System.out.println("\n\n\n");
                System.out.println("---------Ref: " + branch.getName() + "---------");
                Iterable<RevCommit> commits = repo.log().all().call();
                for(RevCommit commit : commits) {
                    System.out.println("Commits: " + commit.getName());
                    System.out.println("Commit Message: " + commit.getShortMessage());
                    System.out.println("Author: " + commit.getAuthorIdent().getName());
                    System.out.println("Commit Date: " + new Date(commit.getCommitTime() * 1000L));
                    System.out.println("Full description: " + commit.getFullMessage());
					System.out.println("\n");
                }
            }
        } catch(IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    void getBasicMetadata(String repo) throws IOException, GitAPIException, URISyntaxException {
        GetRepoMetadata metadata = new GetRepoMetadata();
        Git git = metadata.getRepository(repo);
        metadata.basicDetails(git);
        metadata.getBasics(git);

    }
    void getFullMetadata(String repo) throws IOException, GitAPIException, URISyntaxException {
        Git git = getRepository(repo);
        getCommits(git);
        basicDetails(git);
        getBasics(git);
    }
    void getBasics(Git git) throws GitAPIException, IOException {
        List<Ref> branches = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();

        System.out.println("\n");
        System.out.println("---------Branches---------");
        System.out.println("Number of branches: " + branches.size());
        int count = 0;
        for(Ref branch : branches) {
            System.out.println("Branch" + count + ": " + branch.getName().substring(branch.getName().lastIndexOf("/")+1, branch.getName().length()));
        count ++;
        }
        Iterable<RevCommit> commits = git.log().all().call();
        long numberOfCommits = StreamSupport.stream(commits.spliterator(), false).count();
        System.out.println("\n");
        System.out.println("---------Commits---------");
        System.out.println("Number of commits: " + numberOfCommits);

        //delete the cloned repository
        FileUtils.delete(localPath, FileUtils.RECURSIVE);
    }

}
