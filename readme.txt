Project app for water pipeline system.
--------------------------------------------------------------------------------------------
This application is developed using frameworks MAVEN, SPRING and H2 database
--------------------------------------------------------------------------------------------
The application consists of 1 module and 6 packages entity, data, config, repository, service, util
All the dependencies spelled out in the pom.xml.
--------------------------------------------------------------------------------------------
The functional part of the application is divided into four parts
--------------------------------------------------------------------------------------------
1) Parsing input csv files, which are in CSV folder and write data from csv files into database
--------------------------------------------------------------------------------------------
2) Solve connection problems between two points and find minimal route
--------------------------------------------------------------------------------------------
3) Write solution (minimal route) into database
--------------------------------------------------------------------------------------------
4) Create new csv file "solutions.csv" adn write there minimal route between two points
--------------------------------------------------------------------------------------------