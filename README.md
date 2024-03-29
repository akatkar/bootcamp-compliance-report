## New Changes
### Mailhog
Added Mailhog to capture and test emails during development
- start mailhog as running `docker-compose -f docker/docker-compose.yaml up -d`
- open Mailhog on browser as http://localhost:8025/#
- to stop mailhog `docker-compose -f docker/docker-compose.yaml down`

### Project changes
- moved to gradle. Build project using `./gradlew clean build`
- upgrade to Java 17 
- added checkstyle
- added unit tests
- added reading excel files (.xlsx) (use simplemail and bootcamp profile to test)

# bootcamp-email-reporting-tool
This tool has been developed for bootcamp hackaton  

<b>Bootcamp email reporting tool</b> will be used to generate dynamic reports, from, to, cc, time to send, source sheet, and the email templates are configurable as Spring Boot external configurations.

Source is a google sheet and should be provided as sheet id

Source of sheet has been parsed as <b>headers</b> and <b>values</b>. 
Headers is a List of Strings object
Values is a List of Map object
(Value is a Map object for individual emails)


Mail template should be prepared by using these objects. 
If the report will be sent as individually according to contents of the sheet, 
sheet must have an email column and its header must contain <b>email</b> as a key value. 
Since individual mails sent for each row, template must be using value object instead of values.

Example sheets
 -  For Bootcamp report: https://docs.google.com/spreadsheets/d/1uWiPtk60-hV-Ff7fF3m1vv-4UwWFU4D1CCoGLXElhzU/edit#gid=0
 -  For Students report: https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit#gid=0
 -  For individual mails: https://docs.google.com/spreadsheets/d/1xjkquoEFKbrFz87CEhbYRiu7XWD8unbZVpCbjGMTNzU/edit#gid=0   

## sheet configuration
 - sheet.source = 1uWiPtk60-hV-Ff7fF3m1vv-4UwWFU4D1CCoGLXElhzU
 - sheet.range = Index

## mail configuration
 - mail.message.from=${spring.mail.username}
 - mail.message.to=xxx@test.com, yyy@test.com
 - mail.message.cc=xxx@test.com, yyy@test.com
 - mail.message.subject=Bootcamp Compliance Report
 - mail.message.template=bootcampTemplate
 - mail.message.attachments=file1,file2
 - mail.message.individually = false
 
 NOTE THAT: 
 - Attachment files must be provided as full of path
 - If an attached file does not exist, it is ignored silently
 - You can attach multiple files as comma separated
 - Workbook1.xlsx is put to repository only for attachment test

## Templates
Thymeleaf has been used as template engine. Templates should be prepared as html file using thymeleaf syntax.
Three sample templates have already been prepared. One of them, which is simplemailTemplate, is demonstration of individually email reports.
Template files can be found in <b>resources/templates/mail</b> folder.

Custom prepared templates also can be used as defining with <b>template.folder</b> and <b>mail.message.template</b> parameters without building project.


### Using sample templates

> bootcampTemplate:  --spring.profiles.active=default

> studentsTemplate:  --spring.profiles.active=students

> simpleMailTemplate:  --spring.profiles.active=simplemail

### Using custom templates
To be able to use custom template, you should run the project like following

    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar --spring.profiles.active=students  --template.folder=file:/Users/alikatkar/upload/template/ --mail.message.template=customTemplate

where this command
 - Modified version of students template are used as custom template 
 - Custom template full path is <b>/Users/alikatkar/upload/template/customTemplate.html</b>
 - Please noticed that folder must be ended by /
 - Do not use suffix .html in mail.message.template (It is already configured)        


## Usage of the Tool with Maven

    mvn spring-boot:run

    mvn spring-boot:run -Dspring.profiles.active=students

    mvn spring-boot:run -Dmail.message.to=alikatkar@hotmail.com

## Usage of the Tool with Jar File
You should kick off the jar from root for security tokens. 

    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar

All config variables can be provided as command line arguments.

    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar --mail.message.to=alikatkar@hotmail.com

With all possible configurations. (To be able to run remove end of lines)

    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar 
            --sheet.source = 1uWiPtk60-hV-Ff7fF3m1vv-4UwWFU4D1CCoGLXElhzU
            --sheet.range = Index 
            --template.folder=file:/Users/alikatkar/upload/template/ 
            --mail.message.template=bootcampTemplate
            --mail.message.to=alikatkar@gmail.com, alikatkar@hotmail.com
            --mail.message.cc=ali.katkar@aurea.com
            --mail.message.subject=Bootcamp Compliance Report
            --mail.message.attachments=/Users/alikatkar/file1.txt,/Users/alikatkar/file2.txt
            --mail.message.individually = false
            --schedule.cron='0 0 23 ? * MON,FRI' 
where assumes that following files exist in the file system
 - /Users/alikatkar/upload/template/bootcampTemplate.html 
 - /Users/alikatkar/file1.txt
 - /Users/alikatkar/file2.txt
          
## Setting time to send
Scheduling has been done quartz cron scheduler. So if you provide a valid cron expression 
email reports will be sent periodically according to cron expression. If cron expression is provided but invalid, application will stop without sending report.
If cron expression is not provided then report will be sent immediately and application will stop.

### example usage of time to send
    // cron expression is not provided, send once
    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar --schedule.cron

    // send on every 2 minutes
    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar --schedule.cron='0 0/2 * * * ?'

    // Send At 23:00 on every day-of-week from Monday through Friday
    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar --schedule.cron='0 0 23 ? * MON,FRI'

> for more information about cron: http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-06.html

## Google API Authorization
The first time you run the sample, it will prompt you to authorize access:

    a. The sample will attempt to open a new window or tab in your default browser. If this fails, copy the URL from the console and manually open it in your browser.

       If you are not already logged into your Google account, you will be prompted to log in. If you are logged into multiple Google accounts, you will be asked to select one account to use for the authorization.

    b. Click the Accept button.
    c. The sample will proceed automatically, and you may close the window/tab.
Authorization information is stored on the file system, so subsequent executions will not prompt for authorization.

> for more information: https://developers.google.com/sheets/api/quickstart/java  

## Video Demonstration
https://drive.google.com/file/d/10XShjEYxAxzwZAFKzFiXa7-I5-7Mfd8V/view
  