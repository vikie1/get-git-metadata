package org.example.dto;

public class GithubCommit {
    private final Commit commit;

    public GithubCommit(Commit commit) { this.commit = commit; }
    public Commit getCommit() { return commit; }


    static public class Commit {
        private final String message;
        private final String url;
        private final GithubCommitAuthor author;

        private Commit(String message, String url, GithubCommitAuthor author) {
            this.message = message;
            this.url = url;
            this.author = author;
        }
        public GithubCommitAuthor getAuthor() { return author; }
        public String getUrl() { return url; }
        public String getMessage() { return message; }
    }


    static public class GithubCommitAuthor {
        private final String name;
        private final String email;
        private final String date;

        private GithubCommitAuthor(String name, String email, String date) {
            this.name = name;
            this.email = email;
            this.date = date;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getDate() { return date; }
    }
}
