#Food Hygiene Rating

##Architecture:
###Application has divided into 3 layers
1. Application Services (implemented in java (package - com.infinityworks.foodHygieneRaings.services))
2. Rest Layer (implemented using jersey, intentions to keep it isomorphic and include HAL links so users can follow links to browse)
3. Client UI (implemented using angular, and agular follows HAL links to show re report)

##Assumptions:
1. For Scotland minimum set of ratings expected "Pass" & ""
2. For other regions minimum set of ratings needs to be displayed "5-star", "4-star", "3-star", "2-star", "1-star", "Exempt"
3. Any other ratings returned by Food Hygiene Api is included in calculation and will be displayed


##Notes:
Food Hygiene API has data issues (Ratings API doesnt return complete set of Rating Value which is being returned by Establishment Api


##This application is built on 

###Language reference
1. Java 8
2. Javascript (ES6 - babel)

###Tech Stack:
1. Spring boot (Bootable container)
2. Angular JS (Client Code)
3. Jersey (Rest Endpoint)

###Package management
1. Gradle (for java code and also calls npm internally to build client code)
2. npm and webapck (to build client code)

###Testing framework:
1. Spock


##Useful commands

1. Start the app: 'gradle bootRun'
2. Clean build 'gradle clean build'
3. build client code first time to download dependencies 'npm install', then 'webpack' to build

###Note:
1. if gradle is not installed on your machine, use ./gradlew (in linux or mac), gradlew.bat on windows


##Pending Tasks:
1. Acceptance test (plan to use Geb and WireMock)
2. Client code test (jasmine and karma)
