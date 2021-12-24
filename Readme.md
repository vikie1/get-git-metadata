# Introduction
This tool is used to print out information about a repository given the URL.

# Requirements
You should have the following installed:
* [Java](https://www.java.com/) version 8 or later is preferred.
* [Git](https://git-scm.com/)

# Dependancies
Will be installed by gradle automatically if not already present :
* [JGit](https://git-scm.com/book/en/v2/Appendix-B%3A-Embedding-Git-in-your-Applications-JGit)
* [Appache-commons-cli](https://commons.apache.org/proper/commons-cli/)

# Installation
`$ git clone https://github.com/vikie1/get-git-metadata.git` <br/>

`$ cd get-git-metadata` <br/>

`$ ./gradlew clean build` for Mac/Unix/Linux<br/>
`$ .\gradlew clean build` for Windows<br/>

# Usage
`$ ./gradlew run --args="-h"` in Mac/Unix/Linux -> Should print the help message <br/>
`$ .\gradlew run --args="-h"` in Windows -> Should print the help message <br/> 
