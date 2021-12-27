package org.example.util;

import java.net.MalformedURLException;
import java.net.URL;

public class ParseRepoUrl {
    final static String GITHUB_API_REPO_URL = "https://api.github.com/repos";
    final static String COMMIT_SUFFIX = "/commits";
    final static String BRANCH_SUFFIX = "/branches";

    public static String getHost(String url) throws MalformedURLException {
        URL link = new URL(url);
        switch (link.getHost()){
            case "github.com":
                return "github";
        }
        return "unknown";
    }

    public static String getGithubRepo(String url) throws MalformedURLException {
        URL link = new URL(url);
        String path = link.getPath();
        if (path.contains(".git")){
            path = path.substring(0, path.indexOf(".git"));
        }
        return path.substring(0);
    }

    public static String getCommitApiUrl(String url) throws MalformedURLException {
        String githubApiUrl = "";
        switch (getHost(url)){
            case "github":
                githubApiUrl = GITHUB_API_REPO_URL + getGithubRepo(url) + COMMIT_SUFFIX;
        }
        return githubApiUrl;
    }
    public static String getBranchesApiUrl(String url) throws MalformedURLException {
        String githubApiUrl = "";
        switch (getHost(url)){
            case "github":
                githubApiUrl = GITHUB_API_REPO_URL + getGithubRepo(url) + BRANCH_SUFFIX;
        }
        return githubApiUrl;
    }

}
