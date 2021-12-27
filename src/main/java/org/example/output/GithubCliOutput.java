package org.example.output;

import org.example.dto.GithubBranch;
import org.example.dto.GithubCommit;

import java.util.Arrays;
import java.util.Date;

public class GithubCliOutput {
    public static void commitOutput(GithubCommit[] commits) {
        System.out.println("---------Commits---------");
        System.out.println("Number of commits: " + commits.length);
        GithubCommit latestCommit = commits[0];
        System.out.println("Latest commit: " + latestCommit.getCommit().getAuthor().getDate());
    }
    public static void branchOutput(GithubBranch[] branches) {
        System.out.println("---------Branches---------");
        System.out.println("Number of branches: " + branches.length);
        System.out.print("Name of the branches: ");
        for (GithubBranch branch : branches) {
            System.out.print(branch.getName() + ", ");
        }
        System.out.println();
    }
}
