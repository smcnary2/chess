# My Notes

### Jan 18th

git commit: verb(object)description

**Polymorphism**: many form  
its an abstraction technique that allows your programing to be flexible
- interfaces are a way things can be abstracted

**Interfaces**: basically create an outline for what you program
- implements: uses this part of the interface?

**Inherits**: copies everything from the parent/superclass to the subclass
- I think the work is extend to actually use it and then .super to reference the method 
- you can only have one parent in Java
- Everything in Java inherits from Object

**Abstract**:hybrid of an interface and inheritance
- So someone has to extend the class you abstract and they write the class

**Copying Objects**: 
- copy.variable then it will add all the attributes from var to another var
- its just overloading a constructor  
shallow copy: just causes the two var to share the same pointers. Chnage one you chnage them both  
deep copy: it copies everything from the original var or object


### Feb 6th
Phase 2 description
end points:

**clear**
- clears database

**register**
- new user& authenticates

**login**
- login and authenticates

**logout**
- logsout using authtoken

**listGame**
- verifies authtoken & gives list of games

**createGame**
- verifies authtoken and creates game

**joinGame**
- verifies authtoken and does a bunch of stuff so you can join game


**Objects**
UserData: username, password, email

AuthData: authtoken, username

GameData: gameID, whiteusername, blackusername, gamename, game

for phase 2: make a sequence diagram on website
submit using uri
look at phase 3

### Feb 8th

- make unit tests for phase 3 before you start coding

**HTTP**
- client to server communication (server can't talk to client)
- You have to ask what port to use to get to the website but before that you need the domain
- http://localhost:8080/user this is the url we will be using

**Methods**

get: get an existing resource (no body)

Post: create new resource

Put: updates an existing resource

Delete: deleting a resource

Options: get information about a resource

**Status Codes**

200: good
204: no connection

301.302: redirected
304: not modified

400: bad request
404: not found
403: forbidden
429: toomany requests

500: server error
503: not available

**Java Spark**
- import java spark (in instructions) add to server not other modules
- makes it easy to route end point handlers

phase3 ulm diagram:https://sequencediagram.org/index.html#initialData=actor%20Client%0Aparticipant%20Server%0Aparticipant%20RegistrationService%0Aparticipant%20LoginService%0Aparticipant%20GameService%0Aparticipant%20DataAccess%0Adatabase%20db%0A%0Agroup%20%23navy%20Registration%20%23white%0AClient%20-%3E%20Server%3A%20%5BPOST%5D%20%2Fuser%5Cn%7Busername%2C%20password%2C%20email%7D%0AServer%20-%3E%20RegistrationService%3A%20register(username%2C%20password%2C%20email)%0ARegistrationService%20-%3E%20DataAccess%3A%20getUser(username)%0ADataAccess%20-%3E%20db%3A%20SELECT%20username%20from%20user%0ADataAccess%20--%3E%20RegistrationService%3A%20null%0ARegistrationService%20-%3E%20DataAccess%3A%20createUser(username%2C%20password)%0ADataAccess%20-%3E%20db%3A%20INSERT%20username%2C%20password%2C%20email%20INTO%20user%0ARegistrationService%20-%3E%20DataAccess%3A%20createAuth(username)%0ADataAccess%20-%3E%20db%3A%20INSERT%20username%2C%20authToken%20INTO%20auth%0ADataAccess%20--%3E%20RegistrationService%3A%20authToken%0ARegistrationService%20--%3E%20Server%3A%20authToken%0AServer%20--%3E%20Client%3A%20200%5Cn%7BauthToken%7D%0Aend%0A%0A%0Agroup%20%23orange%20Login%20%23white%0AClient%20-%3E%20Server%3A%20%5BPOST%5D%20%2Fsession%5Cn%7Busername%2C%20password%7D%0AServer%20-%3E%20LoginService%3Alogin(username%2C%20password)%0ALoginService%20-%3E%20DataAccess%3A%20findUser(user%2C%20password)%0ADataAccess%20-%3Edb%3A%20%0ADataAccess%20--%3ELoginService%3A%20true%0ALoginService%20-%3EDataAccess%3A%20updateAuth(user)%0ADataAccess%20-%3Edb%3A%0ADataAccess%20--%3E%20RegistrationService%3A%20authToken%0ARegistrationService%20--%3E%20Server%3A%20authToken%0AServer%20--%3E%20Client%3A%20200%5Cn%7BauthToken%7D%0Aend%0A%0Agroup%20%23green%20Logout%20%23white%0AClient%20-%3E%20Server%3A%20%5BDELETE%5D%20%2Fsession%5CnauthToken%0AServer%20-%3ELoginService%3A%20logout()%0ALoginService-%3EDataAccess%3A%20findAuth()%0ADataAccess-%3Edb%3A%0ADataAccess--%3ELoginService%3A%20authtoken%0ALoginService%20-%3EDataAccess%3A%20deleteAuth()%0ADataAccess-%3Edb%3A%0ALoginService%20--%3EServer%3A%0AServer%20--%3EClient%3A200%0Aend%0A%0Agroup%20%23red%20List%20Games%20%23white%0AClient%20-%3E%20Server%3A%20%5BGET%5D%20%2Fgame%5CnauthToken%0AServer%20-%3E%20GameService%3A%20listGames()%0AGameService-%3EDataAccess%3A%20findAuth()%0ADataAccess-%3Edb%3A%0ADataAccess%20--%3EGameService%3A%20authtoken%0AGameService%20-%3EDataAccess%3A%20findAllGames()%0ADataAccess-%3Edb%3A%0ADataAccess%20--%3EGameService%3A%20%7Bgame%7D%0AGameService--%3EServer%3A%20%7Bgame%7D%0AServer%20--%3EClient%3A%20200%20%7Bgame%7D%0Aend%0A%0Agroup%20%23purple%20Create%20Game%20%23white%0AClient%20-%3E%20Server%3A%20%5BPOST%5D%20%2Fgame%5CnauthToken%5Cn%7BgameName%7D%0AServer-%3EGameService%3A%20createGame(gameName)%0AGameService-%3EDataAccess%3A%20findAuth()%0ADataAccess-%3Edb%3A%0ADataAccess%20--%3EGameService%3A%20authtoken%0AGameService%20-%3EDataAccess%3A%20insertGame(gameName%2C%20gameID)%0ADataAccess-%3Edb%3A%0ADataAccess%20--%3EGameService%3A%20%7BgameID%7D%0AGameService--%3EServer%3A%20%7BgameID%7D%0AServer%20--%3EClient%3A%20200%20%7BgameID%7D%0Aend%0A%0Agroup%20%23yellow%20Join%20Game%20%23black%0AClient%20-%3E%20Server%3A%20%5BPUT%5D%20%2Fgame%5CnauthToken%5Cn%7BClientColor%2C%20gameID%7D%0AServer%20-%3EGameService%3A%20joinGame(playerColor%2C%20gameID)%0AGameService-%3EDataAccess%3A%20findAuth()%0ADataAccess-%3Edb%3A%0ADataAccess%20--%3EGameService%3A%20authtoken%0AGameService%20-%3EDataAccess%3A%20claimColor()%0ADataAccess-%3Edb%3A%0ADataAccess%20--%3EGameService%3A%0AGameService--%3EServer%3A%20%0AServer%20--%3EClient%3A%20200%20%0Aend%0A%0Agroup%20%23gray%20Clear%20application%20%23white%0AClient%20-%3E%20Server%3A%20%5BDELETE%5D%20%2Fdb%0AServer%20-%3EGameService%3A%20clear()%0AGameService%20-%3EDataAccess%3A%20clear()%0ADataAccess%20-%3Edb%3A%20%0A%0ADataAccess%20--%3EGameService%3A%0AGameService%20--%3EServer%3A%0AServer%20--%3EClient%3A%20%5B200%5D%0Aend%0A

### Feb 13th

mark resource as resource root

**static file handling**

lambda functions call a functional interface
this::echoBody
same as (req,resp) -> echobody(req,resp)

**Error Handling**
definitely read slide

**HTTP client**
- make a uri obj
- theb make a url and make an http connection
- then set request method 
- make request
- finally output the response body

Repl: read evaluate p loop

### Feb 22nd

**Test Driven Development(TDD)**
1. add a test
2. implement the functionality
3. run all the tests
4. reactor/cleanup
5. Run all the tests

Data Access: memory version and SQL version

fail()
Assertion.assertTrue(boolean)
Assertion.assertequals(expected return value, function)
Assertion.assertthrows(expected return value/Exception, ()-> {throw new Exception})

### Feb 29th

**SQL**:
declarative language to work with relational data

select

DDL(data definition lang): create alter drop
\
DML(data manipulation lang): insert update delete
\
DQL(data query lang): select

show databases: lists all databases
\
use: open database
\
show: lists all the tables
\
describe: lists field for a table
\
create database: creates a new database
\
create table: 
\
insert: inserts data into a table
\
select: query a table
\
drop table: delete a table
\
alter: edit table

to get the amount of rows select count from variable

joining tables in slides