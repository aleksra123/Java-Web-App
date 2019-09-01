# Project

## 1. Context
This project is developed in the context of the subject *Software Development* at *Instituto Superior de Engenharia do Porto (ISEP)*.The project, called the College Management System (CMS), uses a Web Application developed with GWT. An initial version of this application is given by professors as a starting point. The CMS simulates the development of a proof of concept process and product for a
hypothetical startup company.

The project aims to:
	- Produce a proof of concept solution, state of the art in the field of *Management Information Systems (MIS)*.
	- Be innovating in this field by producing and implementing a software solution in short time frames, by applying a model-driven engineering approach on its software product line, which should be supported by a continuous deployment approach.

To implement this CMS the project makes use of Jenkins declarative pipelines, where attempting to meet the goals is done by breaking the system up into five major concerns. Below is a description of how each concern was designed and implemented, with a corresponding discussion concerning the decisions made during the implementation. Component 2.1 was realised through team effort, while the subsequent components where completed individually.

*(Note: As we were three students for this project, component 2.5 was not realised, as per project requirements.)*


## Component 2.2 - Author: Aleksander Asp 1180184

The design for this component was initially created with limited knowledge of Docker, images and containerisation. As more and more research on these concepts was done, the design was modified and updated. To achieve the task of generating a pdf file from a markdown file, an internet search revealed that *Pandoc*, a universal document converter, was an efficient tool and was chosen as the method of preference. When implementing the use of Pandoc, rather then requiring the hosts to have installed the software on their system, the project Jenkinsfile runs a Docker image of Pandoc, with a command to convert the *readme.md* file to a .pdf-file. This way, as long as the hosts have docker, the conversion can be done effortlessly.

The creation of containers, which would be the environment for testing, was designed to be done through Docker images, with the idea of having all the necessary software, i.e., java, cradle, mysql, etc, in one single image. The implementation of this requirement was done quite, as putting all these different softwares in one image became an impossible task. However, running the projects .war file in a container, allows hosts to access the project, but they will need to have the necessary software installed locally. 

In order to run the CMS web app on a different system than the database management system, docker-compose functionally was to be implemented. Linking between containers could then, in theory, be accomplished with relative ease. Implementation of this requirement was indeed done through a docker-compose file (.yml). The docker-compose file made use of an existing mysql image as well as a locally created Dockerfile. The Dockerfile pulls a base image from tomcat, copies over the correctly modified .war-file from the project, as well as some configuration files, and exposes a port. The docker-compose connects the service using the Dockerfile and the mysql service, using "--link:" functionality. By doing this and changing the url for the database connection in our code we can connect to a freshly initialised database. In order to populate this database we attach a .sql dump from our pre-existing database as a volume to the container. Ports for both services are mapped, making them accessible.

Generation of the .zip with the archived artefacts, Jenkinsfile and project report (.pdf) was set to be accomplished in the Jenkinsfile, making use of additional plug-ins if necessary. To implement this, an extra plugin called File Operations was installed to Jenkins, which allows for creating zip-files from other files and folders. The archived artefacts and other required files were simply copied into the submission folder using shell scripts.

In the end, the requirements that have been realised for this concern, have been so in an efficient way. Assuming that all hosts have Docker, there are very few software requirements, so this part can easily be run on different hosts and systems. As for the containers to be made for the integration and acceptance tests, there is definitely room for improvement, seeing as this step was not fully completed. Perhaps it isn't necessary to have all the previously mentioned software in one image, but instead some workaround, possibly using the .war-file.   



## Component 2.3- Author: Lone Bekkeheien 1180241
The Component three along with the other Component assignments forces the students to work with Jenkins and the construct of Pipelines, through a declarative jenkinsfile. The Component three especially takes a closer look on code quality and integration tests of the CMS application. The section 2.3 is divided in four parts as follows:

1.
The first part is doing checks on the code quality of the project by using a feature called Checkstyle. Checkstyle is a static code analysis tool used in software development for checking if Java source code complies with coding rules. The kinds of checks being done is defined in a configuration file called checkstyle.xml. For this project, five checks from the webpage  http://checkstyle.sourceforge.net/checks.html have been chosen. If any of 	these checks "fails" a warning will be given in the checkstyle analysis report which is being published on Jenkins. The first check is checking if there are any lines of code longer than 100 characters. The reason for this check is that long lines makes the code harder to read when you have to scroll to the right. Therefore this is something 	this project is trying to avoid.
The empty block module in the configuration file checks if there are any empty blocks like try, finally, catch and so on. This is unecessary code which this project don't want to have.
The right curly module checks if the right curly bracket is standing alone on the line.
The indentation-check is checking if the code has the desired indenation level. There are also certain settings 	set for this module. The settings are set after my own preferences of how I like my code to look.
The very last module if checking for empty catch blocks.
There are of course many other possible check alternatives that could've been used in this project. It is also 			possible to add more checks as you go.

Thresholds for the build health has been set in the Jenkinsfile. The build is healthy(sunny threshold) if there are less warnings than 20%. The build is unhealthy(stormy threshold) if there are more warnings than 50%. If the actual number of warnings is between the provided thresholds then the build health is interpolated.

2.
The second part of this component is doing a "check" on the code quality of the project by using Findbugs. For this part the default Findbugs-checks have been used because I thought they were suitable. For instance one of the checks that are being done is called DMI_CONSTANT_DB_PASSWORD. If this check returns true, there will be a warning because the code creates a database connect using a hardcoded, constant password. Anyone with access to either the source code or the compiled code can easily learn the password. Although since the assignment says "set settings for findbugs" this component also has one includeFilter.xml file in the config folder, and a excludeFilter.xml. Through these files it is possible to include and exclude checks for specific files after what is desired. These files are included in the build through the build.gradle file. If I would've had more time on this project, i would've spent more time finding the absolute perfect checks, but for now the default checks and some in the include filter is enough. The analysis report is also published on Jenkins which gives an overview of which files that fails which checks, and exactly where it is. This way the developer don't have to go through the whole code looking for violations.
in the jenkinsfile threshold for the build health is also set. The code is set to be healthy if there are less warnings than 20%, and the build is unhealthy if there are more warnings than 50%.



3.
The third part of this component is making integration tests to cover the specific features of the project. There has been made many tests checking if the database is set up as it should and that everything is working. In the GWT file there has been made two tests specific for the features of this project. The first test is called testMaxStudents(). This test creates a new thread and a runnable that adds students to one specific class until it is full(max of 20 students in one class). When the class is full the created thread is being interrupted by another function that is restricting that only 20 students can be enrolled in one class. when the thread had been interrupted it breaks out of the while that are adding students to the class and goes to a catch block. When the thread are in the catch block we know that there are only twenty students in the class.
The second thest is called testStudentEnrolledInOnlyOneClass(). This test adds a two students with the exact same contact information and checks if they are being assigned different id's. In this project different id's means that even though the students have the exact same firstname, surname and so on they are not the same student because of the student id.
The testing and coverage reports are being published in Jenkins.
There are also different settings being set. For instance, to make the build fail if coverage degrades
more than the delta thresholds that should be congured, a boolean variable buildOverBuild is set to true. Other than that default settings are being used, except for the delta thersholds, which are set to 20%.
The build health of the coverage report is also being set in the jenkinsfile. For chekcstyle and findbugs the build health thresholds are the same. For the coverage report the thresholds a bit different because we want to accept less warnings in the coverage report. Less warnings in the coverage report is accepted because test coverage is a measure used to describe the degree to which the source code of a program is executed when a particular test suite runs. A program with high test coverage, measured as a percentage, has had more of its source code executed during testing, which suggests it has a lower chance of containing undetected software bugs compared to a program with low test coverage. Therefore the build is healthy if there are less than 10% warnings, and the build is unhealthy if there are more than 30% warnings.

4.
The very last part of this component concerns making the CMS application run for at least two different Tomcat versions. Because of the lack of time and the complexity of this part, this step haven't been done.



##Component 2.4 - Author: Konstantin Shuxtelinsky 1180233
The design for this component was initially created with limited knowledge to Cucumber and Selenium. Nevertheless a brief pipeline design was created and added to the project.

The importance of the Acceptance and Smoke test is mentioned in this part of the project. Quality Assuarance (QA) should be present in each and every company so failure can be tracked before the
passing of the Application to the customer. People working in QA spend a lot of time doing manual testing mostly without any structure. Therefore both Cucumber and in Webdevelopmen especially Selenium
are useful tools to prevent QA from manual testing and to ensure that the focus moves to developing test cases. This fact and the fact that Cucumber is a human readable language make it very strong in terms
of Testing and Application development. The Language itself could even be used before developing the app to display the functionality and the logic behind the Application. Unfortunately the publishing did not go quite
well even though the .json document was put into the right direction, the Pipeline could not find the file and did create some errors. Therefore and due to the lac of time this could not be done properly. The code for  
the publishing was inserted into the pipeline and uncommented.

Because the docker Container was missing and we could not make it work due to some big errors like "com.mysql.cj.exceptions.CJCommunicationsException: Communications link failure
" we decided to not finish this bullet point. Nevertheless the code for a working docker container was put into the pipeline and uncommented. Besides that the Dockerfile and the docker-compose file where created and put
into the project structure too.

While the containisation was not working the docker image could not be published to the docker hub.

The same problem was experienced with the smoke test, which should be tested against a docker container. Nevertheless the code was provided and uncommented in the pipeline.

To give a brief overview about this specific 2.4 exercise of the project:
The important file to implement where cucumber/src and the imported webdrivers as well as the created gradle wrapper, further
the Dockerfile to pull the tomcat image and the docker-compose to make it work together with the mysql image and finaly the Jenkins_Declarative file which combines all the requirements and is the pipeline to use.
