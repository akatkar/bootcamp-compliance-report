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
sheet must have an email column and template must be using value object.   

## sheet configuration
 - sheet.source = 1uWiPtk60-hV-Ff7fF3m1vv-4UwWFU4D1CCoGLXElhzU
 - sheet.range = Index

## mail configuration
 - mail.message.from=${mail.username}
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
Thymeleaf has been used as template engine. Templates can be prepared as html file using thymeleaf syntax
Template files must be placed in <b>resources/templates/mail</b> folder

3 sample templates have already been prepared. One of them, which is simplemailTemplate, is using individually email reports.

### Using sample templates

> bootcampTemplate:  --spring.profiles.active=default

> studentsTemplate:  --spring.profiles.active=students

> simpleMailTemplate:  --spring.profiles.active=simplemail

### Using custom templates
To be able to use custom template, you should run the app like following

    java -jar target/compliance-report-0.0.1-SNAPSHOT.jar --spring.profiles.active=students  --template.folder=file:/Users/alikatkar/upload/template/ --mail.message.template=customTemplate

where this command
 - Modified version of students template are used as custom template 
 - Custom template full path is <b>/Users/alikatkar/upload/template/customTemplate.html</b>
 - Please noticed that folder must be ended by /
 - Do not use suffix .html in mail.message.template (It is already configured)        


## Usage of the Tool with Maven

> mvn spring-boot:run

> mvn spring-boot:run -Dspring.profiles.active=students

> mvn spring-boot:run -Dmail.message.to=alikatkar@hotmail.com

## Usage of the Tool with Jar File
You should kick off the jar from root for security tokens. 

> java -jar target/compliance-report-0.0.1-SNAPSHOT.jar

All config variables can be provided as command line arguments

> java -jar target/compliance-report-0.0.1-SNAPSHOT.jar --mail.message.to=alikatkar@hotmail.com
 

## Google API Authorization
The first time you run the sample, it will prompt you to authorize access:

    a. The sample will attempt to open a new window or tab in your default browser. If this fails, copy the URL from the console and manually open it in your browser.

       If you are not already logged into your Google account, you will be prompted to log in. If you are logged into multiple Google accounts, you will be asked to select one account to use for the authorization.

    b. Click the Accept button.
    c. The sample will proceed automatically, and you may close the window/tab.
Authorization information is stored on the file system, so subsequent executions will not prompt for authorization.

> for more information: https://developers.google.com/sheets/api/quickstart/java    