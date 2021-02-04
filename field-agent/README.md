# Module 7 Assessment: Field Agent

## Tasks & Goals:
### Friday, January 29
* [x] Review Code (3:00) (actual 0:50)
* [x] Implement Alias model (0:30) (actual 0:05)
### Saturday, January 30
* [x] Implement SecurityClearanceJdbcTemplate Repository (1:30) (actual 0:50)
* [x] Test SecurityClearanceJdbcTemplateRepository (1:00) (actual 1:00)
* [x] Implement AliasJdbcTemplateRepository (1:30) (actual 0:30)
* [x] Test AliasJdbcTemplateRepository (1:00) (actual 2:00)
* [x] Implement SecurityClearanceService (2:00) (actual 1:30)
* [x] Implement AliasService (2:00) (actual 1:00)
### Sunday, January 31
* [x] Test SecurityClearanceService (2:00) (actual 1:10)
* [x] Test AliasService (2:00) (actual 1:30)
* [x] Implement SecurityClearanceController (2:00) (actual 0:45)
* [x] Implement AliasController (2:00) (actual 0:20)
* [x] Implement Global Error Handling (1:00) (actual 1:00)
* [x] Send HTTP requests to ensure functionality (2:00) (actual 3:00)

Estimated: 23:30

Actual: 15:30

## To-Do:
* [x] Implement Security Clearance Feature
    * classes needed: SecurityClearanceService, SecurityClearanceController
    * utilize existing SecurityClearanceJdbcTemplateRespository (incomplete)
    * Missing Features
        * [x] Find all security clearances
        * [x] Find a security clearance by its identifier
        * [x] Add a security clearance
        * [x] Update an existing security clearance
        * [x] Delete a security clearance. (This requires a strategy. It's probably not appropriate to delete agency_agent records that depend on a security clearance. Only allow deletion if a security clearance key isn't referenced.)
    * Validations
        * [x] Security clearance name is required
        * [x] Name cannot by duplicated.
        * [x] Security clearance id cannot be referenced in agency/agent
* [x] Implement Aliases
    * classes needed: Alias(model) AliasJdbcTemplateRepository, AliasService, AliasController
    * Missing Features
        * [x] Fetch an individual agent with aliases attached.
        * [x] Add an alias.
        * [x] Update an alias.
        * [x] Delete an alias. (No strategy required. An alias is never referenced elsewhere.)
    * Validations
        * [x] Name is required.
        * [x] Persona is not required unless a name is duplicated. The persona differentiates between duplicate names.
* [x] Implement Global Error Handling
    * Use the @ControllerAdvice annotation to register an exception handler for all controllers. Catch and handle exceptions at two levels.
        1. Determine the most precise exception for data integrity failures and handle it with a specific data integrity message.
        2. For all other exceptions, create a general "sorry, not sorry" response that doesn't share exception details.
## Deliverables:
* [x] Assessment Plan
* [x] HTTP files to hold requests
* [x] Maven Project
* [x] Test Suite
### Resources:
* LMS Lessons, Pets project
* Memories project, refactored
* Sustainable Foraging, Don't Wreck My House
* Q&A Videos
* WakaTime plugin
* HttpStatus Enum Documentation: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html
* ResponseEntity<> Documentation: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html

## Class Catalog
### Models
* Alias
  * int aliasId
  * String name
  * String persona
  * Agent agent  

### Data Access
* AliasRepository: interface for Alias repositories
* SecurityClearanceJdbcTemplateRepository
  * currently implemented: findById()
  * findAll()
  * add()
  * update()
  * deleteById()
    
* AliasJdbcTemplateRepository
  * findAll()
  * findById() -> use to find aliases attached to agent
  * add()
  * update()
  * deleteById()
    
### Domain  
* SecurityClearanceService
  * methods to mirror repository methods
  * validateFields(): validates required fields are filled in
  * isRepeat(): checks whether there is a repeat
  * isReferenced(): checks whether security clearance is currently referenced in agency_agent  
* AliasService
  * methods to mirror repository methods
  * validateFields(): validates required fields are filled in
  * isRepeat(): checks whether name is repeat and therefore if persona is needed
  * validate(): method for orchestrating all validations  

### Controllers
* SecurityClearanceController
  * HTTP CRUD handlers - @GetMappping(find methods), @PostMapping(add), @PutMapping(update), @DeleteMapping(delete)
* AliasController
  * HTTP CRUD handlers - @GetMappping(find methods), @PostMapping(add), @PutMapping(update), @DeleteMapping(delete)
* GlobalExceptionHandler
  * start with basic last resort Exception ex handler
  * add additional handlers during HTTP request trials  

### Testing
* SecurityClearanceJdbcTemplateRepositoryTest
* AliasJdbcTemplateRepositoryTest
* SecurityClearanceServiceTest (use mocking)
* AliasServiceTest (use mocking)

## Notes:
### High Level Requirements
* Create full HTTP CRUD for security clearance.
* Create full HTTP CRUD for agent aliases.
* Implement global error handling.
### Technical Requirements
* Do not change the database schema. Only Java changes are required.
* Use Spring to make your life easier. Specifically, use as much Spring Testing as possible to save steps.
* Test both data and domain layer components. 
    * The @MockBean isn't required for domain testing, but if you don't use it you must create a test double.
    * Controller testing isn't strictly required, but try to test at least one HTTP endpoint with a mock web server. 
    * It might be interesting to trigger global exception handling.
* Tests must never run against the production database. They run against the test database.

### Stretch Goals
* Implement full HTTP CRUD for mission.
* Implement full HTTP CRUD for the mission/agent many-to-many relationship.
* Implement a database logging strategy for global exceptions. (It's okay to alter database schema for this goal.) It's possible to disable detailed exception messages via configuration, so that's not the real value of global exception handling. The real value is the ability to log exceptions and track their behavior.
* Create a database table, a repository, and possibly a service (maybe pass-through?) to log exceptions to the database.
* Implement @RESTControler testing for controllers