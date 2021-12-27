package org.example.util;

import com.google.gson.Gson;
import org.example.dto.GithubBranch;
import org.example.dto.GithubCommit;
import org.example.output.GithubCliOutput;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpRequest {
    HttpsURLConnection connection;
    public static void getGithubCommits(String url) throws IOException {
        HttpRequest httpRequest = new HttpRequest();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (httpRequest.getConnection(url).getInputStream())));

        Gson gson = new Gson();
        GithubCommit[] commits = gson.fromJson(br, GithubCommit[].class);
        GithubCliOutput.commitOutput(commits);
        httpRequest.connection.disconnect();
    }
    public HttpsURLConnection getConnection(String url) throws IOException {
        URL link = new URL(url);
        connection = (HttpsURLConnection) link.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + connection.getResponseCode());
        }
        return connection;
    }
    public static void getGithubBranches(String url) throws IOException {
        HttpRequest httpRequest = new HttpRequest();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (httpRequest.getConnection(url).getInputStream())));
        Gson gson = new Gson();
        GithubBranch[] branches = gson.fromJson(br, GithubBranch[].class);
        GithubCliOutput.branchOutput(branches);
        httpRequest.connection.disconnect();
    }
}
