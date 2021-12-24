package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class GetRepoMetadata {

    File localPath = null;
    CliOutput print = new CliOutput();
    public Git getRepository(String name) throws IOException, URISyntaxException, GitAPIException {
       localPath = File.createTempFile("GitRepository", "");
       if (localPath.exists()) { FileUtils.delete(localPath); }

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

    void getCommits(Git repo) throws GitAPIException {
        List<Ref> branches = repo.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        try {
            for(Ref branch : branches) {

                System.out.println("\n\n\n");
                System.out.println("---------Ref: " + branch.getName() + "---------");


                Iterable<RevCommit> commits = repo.log().all().call();
                for(RevCommit commit : commits) {
                    print.getCommitDetails(commit);
                }
            }
        } catch(IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    void getBasicMetadata(String repo) throws IOException, GitAPIException, URISyntaxException {
        Git git = getRepository(repo);
        print.getBasicDetails(git);
        getBranchAndCommitBasics(git);

        // clean up
        if (localPath.exists()) { FileUtils.delete(localPath, FileUtils.RECURSIVE); }
    }

    //For the more verbose option
    void getFullMetadata(String repo) throws IOException, GitAPIException, URISyntaxException {
        Git git = getRepository(repo);
        getCommits(git);
        print.getBasicDetails(git);
        getBranchAndCommitBasics(git);

        //cleanup once done
        if (localPath.exists()) { FileUtils.delete(localPath, FileUtils.RECURSIVE); }
    }

    void getBranchAndCommitBasics(Git git) throws GitAPIException, IOException {
        print.getBranchDetails(git);
        print.getNumberOfCommits(git);
    }

}
