# Introduction
This tool is used to print out the metadata of a repository given the URL.

# Requirements
You should have the following installed:
* [Java](https://www.java.com/) version 8 or later is preferred.
* [Git](https://git-scm.com/)
* [Gradle](https://gradle.org/)

# Dependancies
Will be installed automatically if not already present by gradle:
* [JGit](https://git-scm.com/book/en/v2/Appendix-B%3A-Embedding-Git-in-your-Applications-JGit)
* [Appache-commons-cli](https://commons.apache.org/proper/commons-cli/)

# Installation
`$ git clone https://github.com/vikie1/get-git-metadata.git` <br/>

`$ cd get-git-metadata` <br/>

`$ gradle clean build` <br/>

# Usage
`$ gradle run --args="-h"` -> Should print the help message <br/>
