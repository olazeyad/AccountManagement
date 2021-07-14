This is simple application create with support of Spring Boot, Maven and Java provides 3 Apis:
1. /statement/filterStatementByDate : get account statement within date range
2. /statement/filterStatementByAmount : get account statement within amount range
3. /statement/getAccountStatement : get account statement within the three past months
4. /authenticate

To run application follow this steps:
1. pull the code to Ide
2. Edit the datasourse url with the location that accountsdb.accdb exist in application.properties
3. Run the project (will run on port 8085)
4. First of all call authenticate api and provide username and password this will return with JWT which have expiration time = 5 min
5. Use the jwt in your next request
6. filterStatementByDate accept json object contain accountId(optional), startDate, endDate
7. filterStatementByAmount accept json object contain accountId(optional), startAmount, endAmount
8. getAccountStatement accept no param or json object contain accountId(optional)

