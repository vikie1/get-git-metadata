package org.example;

import org.apache.commons.cli.*;
import org.example.util.HttpRequest;
import org.example.util.ParseRepoUrl;

public class ReadCliArguments {
    public static void main(String[] args) {
        ReadCliArguments readCliArguments = new ReadCliArguments();
        readCliArguments.readArguments(args, cliOptions());
    }

    //Define all the command line options required
    static Options cliOptions(){
        Options options = new Options();
        options.addOption("h", "help", false, "Print this message");
        Option option = new Option("r", "repo", true, "URL of the repository use `,` to separate multiple repositories");
        option.setRequired(true);
        option.setArgName("REPO");
        options.addOption(option);
        options.addOption("v", "verbose", false, "Print the all the details of the repository including the commit dates and messages");
        return options;
    }
    //parse the command line arguments
    CommandLine readArguments(String[] args, Options options){
        CommandLine cmd = null;
        HelpFormatter formatter = new HelpFormatter();
        try {
            cmd = new DefaultParser().parse(cliOptions(), args);
            if (cmd.hasOption("h")) {
                formatter.printHelp("Usage: ", options);
            }if (cmd.hasOption("r")) {
                provideRepositoryName(cmd.getOptionValue("r"), cmd.hasOption("v"));
            }
        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Provide a repository URL", options);
        }
        return cmd;
    }
    void provideRepositoryName(String input, boolean verbose){
        String[] repoNames = input.split(",");
        for(String repoName: repoNames){
            System.out.println("\n\nDetails for: " + repoName);
            GetRepoMetadata metadata = new GetRepoMetadata();
            try {
                if (verbose) {
                    metadata.getFullMetadata(repoName);;
                }
                else {
                    switch (ParseRepoUrl.getHost(repoName)) {
                        case "github":
                            HttpRequest.getGithubCommits(ParseRepoUrl.getCommitApiUrl(repoName));
                            HttpRequest.getGithubBranches(ParseRepoUrl.getBranchesApiUrl(repoName));
                            break;
                            case "unknown":
                                metadata.getBasicMetadata(repoName);
                                break;
                    }

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

}
