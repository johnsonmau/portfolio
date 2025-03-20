# Use official Tomcat base image
FROM tomcat:9.0.99

# Set working directory to Tomcat webapps
WORKDIR /usr/local/tomcat/webapps/

# Copy the built WAR file into the Tomcat webapps directory
COPY target/home.war ./ROOT.war

# Expose port 8084
EXPOSE 8084

# Start Tomcat
CMD ["catalina.sh", "run"]