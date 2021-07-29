<p align="center">

 <img width="100px" src="src/main/resources/static/images/logo-tab.png" align="center" alt="stox app logo" />
 <h2 align="center">Stoxapp</h2>
 <p align="center">
A platform to create, manage and analyze stocks, sectors, stock exhanges and ipos.</p>
</p>
## What is it?

This is the backend service for the [Stock Market Charting - React].

## Checkout Directly
[stoxapp.herokuapp.com]

## Installation
To build and deploy the project.
```
mvn spring-boot:run 
```
OR

To build the project
```
mvn clean package
``` 
```
cd target
```
```
java -jar company-0.0.1-SNAPSHOT.jar
```

## APIs
The APIs for the service is corresponding to the following:

- Company
- Stock Exchange
- Sectors
- Stock Price
- User
- IPO Details

### Company APIs
- company-get

!['company-get'](src/main/resources/static/images/postman/company-get.png)

- company-list

!['company-list'](src/main/resources/static/images/postman/company-list.png)
- company-mapExchange

!['company-mapExchange'](src/main/resources/static/images/postman/company-mapExchange.png)
- company-new

!['company-new'](src/main/resources/static/images/postman/company-new.png)

- company-patternfilter

!['company-patternfilter'](src/main/resources/static/images/postman/company-patternfilter.png)

- company-update

!['company-update'](src/main/resources/static/images/postman/company-update.png)

### IPO APIs
- ipo-get

!['ipo-get'](src/main/resources/static/images/postman/ipo-get.png)

- ipo-list

!['ipo-list'](src/main/resources/static/images/postman/ipo-list.png)

- ipo-listupcoming

!['ipo-listupcoming'](src/main/resources/static/images/postman/ipo-listupcoming.png)


- ipo-mapExchange

!['ipo-mapExchange'](src/main/resources/static/images/postman/ipo-mapexchange.png)
- ipo-new

!['ipo-new'](src/main/resources/static/images/postman/ipo-new.png)

- ipo-update

!['ipo-update'](src/main/resources/static/images/postman/company-update.png)

### Sector APIs
- sector-get

!['sector-get'](src/main/resources/static/images/postman/sector-get.png)

- sector-list

!['sector-list'](src/main/resources/static/images/postman/sector-list.png)

- sector-listcompanies

!['sector-listcompanies'](src/main/resources/static/images/postman/sector-listcompanies.png)

- sector-new

!['sector-new'](src/main/resources/static/images/postman/sector-new.png)

- sector-update

!['sector-update'](src/main/resources/static/images/postman/sector-update.png)

### Stock Exchange APIs
- stockexchange-get

!['stockexchange-get'](src/main/resources/static/images/postman/stockexchange-get.png)

- stockexchange-list

!['stockexchange-list'](src/main/resources/static/images/postman/stockexchange-list.png)

- stockexchange-listcompanies

!['stockexchange-listcompanies'](src/main/resources/static/images/postman/stockexchange-listcompanies.png)

- stockexchange-new

!['stockexchange-new'](src/main/resources/static/images/postman/stockexchange-new.png)

- stockexchange-update

!['stockexchange-update'](src/main/resources/static/images/postman/stockexchange-update.png)

### Stock Price APIs
- stockprice-compare

!['stockprice-compare'](src/main/resources/static/images/postman/stockprice-compare.png)

- stockprice-uploadexcel

!['stockprice-uploadexcel'](src/main/resources/static/images/postman/stockprice-uploadexcel.png)

### User APIs
- users-get

!['users-get'](src/main/resources/static/images/postman/users-get.png)

- users-list

!['users-list'](src/main/resources/static/images/postman/users-list.png)

- users-register

!['users-register'](src/main/resources/static/images/postman/users-register.png)

- users-authenticate

!['users-authenticate'](src/main/resources/static/images/postman/users-authenticate.png)

- users-confirm

!['users-confirm'](src/main/resources/static/images/postman/users-confirm.png)

- users-update

!['users-update'](src/main/resources/static/images/postman/users-update.png)





## Version
v0.0.1


[Stock Market Charting - React]: <https://github.com/aks010/stockMarket-react>
[stoxapp.herokuapp.com]:<http://stoxapp.herokuapp.com/>