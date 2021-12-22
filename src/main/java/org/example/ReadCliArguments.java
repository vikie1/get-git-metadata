package org.example;

import org.apache.commons.cli.*;

public class ReadCliArguments {
    public static void main(String[] args) {
        ReadCliArguments readCliArguments = new ReadCliArguments();
        readCliArguments.readArguments(args, cliOptions());
    }

    //Define all the command line options required
    static Options cliOptions(){
        Options options = new Options();
        options.addOption("h", "help", false, "Print this message");
        Option option = new Option("r", "repo", true, "Name of the repository use `,` to separate multiple repositories");
        option.setRequired(true);
        option.setArgName("REPO");
        options.addOption(option);
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
            }
            if (cmd.hasOption("r")) {
                String repo = cmd.getOptionValue("r");
                provideRepositoryName(repo);
            }
        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Provide a repository name", options);
            System.exit(0);
        }
        return cmd;
    }
    void provideRepositoryName(String input){
        String[] repoNames = input.split(",");
        for(String repoName: repoNames){
            System.out.println("Details for:" + repoName);
            GetRepoMetadata metadata = new GetRepoMetadata();
            try {
                metadata.getMetadata(repoName);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

}
