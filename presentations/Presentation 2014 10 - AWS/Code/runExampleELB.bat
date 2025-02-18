@echo off

javac -classpath lib\aws-java-sdk-1.7.13.jar AwsExampleELB.java

java -classpath AwsCredentials.properties;lib\aws-java-sdk-1.7.13.jar;lib\aspectjrt.jar;lib\aspectjweaver.jar;lib\aws-java-sdk-1.7.13.jar;lib\commons-codec-1.3.jar;lib\commons-logging-1.1.1.jar;lib\freemarker-2.3.18.jar;lib\httpclient-4.2.3.jar;lib\httpcore-4.2.jar;lib\jackson-annotations-2.1.1.jar;lib\jackson-core-2.1.1.jar;lib\jackson-databind-2.1.1.jar;lib\joda-time-2.2.jar;lib\mail-1.4.3.jar;lib\spring-beans-3.0.7.jar;lib\spring-context-3.0.7.jar;lib\spring-core-3.0.7.jar;lib\stax-1.2.0.jar;lib\stax-api-1.0.1.jar;. AwsExampleELB
