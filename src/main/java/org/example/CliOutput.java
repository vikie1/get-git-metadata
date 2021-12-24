package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

public class CliOutput {

    void getBasicDetails(Git repo) throws IOException, GitAPIException {
        //ObjectId head = repo.getRepository().resolve(Constants.HEAD);
        System.out.println("something" + repo.getRepository().resolve(Constants.HEAD));
//        RevCommit lastCommit = new RevWalk(repo.getRepository()).parseCommit(head);
        System.out.println("\n");
        System.out.println("Repository Size: " + repo.getRepository().getDirectory().length());
//        System.out.println("Repository Last Modified: " + new Date(lastCommit.getCommitTime()));
        System.out.println("Repository Head: " + repo.getRepository().getFullBranch());
    }

    void getBranchDetails(Git repo) throws IOException, GitAPIException {
        List<Ref> branches = repo.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        System.out.println("\n");
        System.out.println("---------Branches---------");
        System.out.println("Number of branches: " + branches.size());

        //loop through branches and list the names
        int count = 0;
        for(Ref branch : branches) {
            System.out.println("Branch" + count + ": " + branch.getName().substring(branch.getName().lastIndexOf("/")+1, branch.getName().length()));
            count ++;
        }
    }
    void getNumberOfCommits(Git git) throws IOException, GitAPIException {
        Iterable<RevCommit> commits = git.log().all().call();
        long numberOfCommits = StreamSupport.stream(commits.spliterator(), false).count();
        System.out.println("\n");
        System.out.println("---------Commits---------");
        System.out.println("Number of commits: " + numberOfCommits);
        System.out.println("Last commit time: " + new Date(git.log().all().call().iterator().next().getCommitTime() * 1000L));
    }

    void getCommitDetails(RevCommit commit) {
        System.out.println("Commits: " + commit.getName());
        System.out.println("Commit Message: " + commit.getShortMessage());
        System.out.println("Author: " + commit.getAuthorIdent().getName());
        System.out.println("Commit Date: " + new Date(commit.getCommitTime() * 1000L));
        System.out.println("Full description: " + commit.getFullMessage());
        System.out.println("\n");
    }

}
