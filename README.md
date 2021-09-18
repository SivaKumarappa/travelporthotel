Technologies used:
------------------
Maven as a build tool with mavensure/maven-config plugins
TestNG for Assertions/running the tests using testng.xml suite
Restassured using JAVA(16.0.2) for API automation testing
Eclipse IDE for development

Steps to execute:
-----------------
Pre-requis: Maven/Java
1. Clone the project using https://github.com/SivaKumarappa/travelporthotel.git
2. go to downloaded Project root folder, make sure once you cd into that folder you can see pom.xml file
3.Give commond: mvn test.
4. It will run/compile the code and executes the tests, and we wantedly failing couple of Tests and so we can BUILDs status as FAILED.
5. To see test results - Goto reports folder( {project-root}/target/surefire-reports/index.html) and open index.html. It will show all failed test cases, to expand PASS test cases - left side frame, Results -> Passed Methods -> click on Show link , it will expand
Because of the time constraint I haven't configured EXTENTREPORT

What I observed with the APIs are:
----------------------------------
1. When you send a request with past dates, in stead of an response it should send an error response code. So API is allowing any date if it is in NNNN-NN-NN format (N-Number)
2.If you compare the rates for a specific date using get and post, we can see a difference of 10 bugs, even I can see this in code as well
3.It is allowing to send a post request with minus number of days(-6), instead of throwing an error it is giving back a response object with invalid results in checkout and totalprice fields.
4.In Post Request, checkout date-month is coming incorectly when we are giving more number of days
5. For Post request- pick a date on which rooms availability is less number ( lets say 4), and if you send a post request with 6days of room booking, then it should send an error response code for that particular date but not now.
6. When you send multiple post requests for a same date, then availability number is going to negative numbers, have to handle a proper error code error.
7. Also need to handle Proper Status codes/Error codes

Note: As part of the code, I need to do a good amount of refactor.
