FROM tomcat:latest

RUN mkdir -p /cms
WORKDIR /cms
VOLUME /cms


ADD /tomcat/container_war/CMS_asp-1.0.war /usr/local/tomcat/webapps/
COPY /tomcat/tomcat-users.xml /usr/local/tomcat/conf/
COPY /tomcat/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
EXPOSE 8080 8080
