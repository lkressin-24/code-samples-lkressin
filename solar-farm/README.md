# Module 3 Assessment Plan: Solar Farm
Goal: Design, test, and build an application to track solar panels on a solar farm.

## Class Catalog
(see images folder for application diagrams)
* App: a class for running the application, completes the dependency chain
### Models
* Panel: a class to model the solar panel as a real-world object
  * private String section
  * private int row
  * private int column
  * private int yearInstalled
  * private MaterialType material
  * private boolean isTracking
  * getters and setters (public)  
* MaterialType: a enum holding the possible material types for solar panels
### Data Access
* SolarFarmRepository: an interface for all repositories pertaining to project
  * findAll(): a method for finding all panels at farm
  * findBySection(): a method for finding panels by section
  * findById(): a method for finding panels by specific id
  * findTracking(): a method for finding which panels have sun-tracking and which do not  
  * add(): a method for adding a solar panel to the farm
  * update(): a method for updating a solar panel on the farm
  * deleteById: a method for removing a solar panel from the farm via ID  
* SolarFarmFileRepository: a class implementing SolarFarmRepository, used for interacting with the production data file
* SolarFarmRepositoryDouble: a class implementing SolarFarmRepository, used for testing the service
* SolarFarmRepositoryTest: a class for testing the repository
  * shouldFindAll()
  * shouldFindBySection()
  * shouldFindById()
  * shouldFindTracking()
  * shouldAdd()
  * shouldNotAddNull()
  * shouldUpdate()  
  * shouldNotUpdateNonExisting()
  * shouldDelete()
  * shouldNotDeleteNonExisting()  
* DataAccessException: a exception extending Exception, thrown when application fails to access current data source
### Domain
* SolarFarmService: a class to validate input going into the repository
  * most methods similar to data access layer
  * validateInputs()  
* PanelResult: a class to determine if a panel was successfully managed; stores error messages if not
  * private String message()
  * public boolean isSuccess()
  * public String getMessage()
  * public void addErrorMessage(String message)  
### User Interface
* View: a class for reading from and writing to the console
  * methods for reading strings and integers
  * method for displaying menu 
* Controller: a class for communication between View and SolarFarmService
  * methods for creating, updating, deleting solar panels - communicate with service
  * method for viewing panels by section
  * run() method to run application (called from App.java)  

## Tasks and Goals
### Thursday, December 31
* [ ] create Data Access Layer
* [ ] test Data Access Layer
### Friday, January 1
* [ ] create Domain Layer
### Saturday, January 2
* [ ] test Domain Layer
* [ ] create User Interface Layer
### Sunday, January 3
* [ ] any refactoring?
* [ ] ensure all components are working, see if application can break
* [ ] review rubric
* [ ] turn in assessment
## Notes
### High-Level Requirements
The user is a solar farm administrator.

* Add a solar panel to the farm.
* Update a solar panel.
* Remove a solar panel.
* Display all solar panels in a section.
### Technical Requirements
* Start with a three-layer architecture. If you have a compelling reason to vary from the architecture, share it with your instructor and they will make a decision.
* Data must be stored in a delimited file. Stopping and starting the application should not change the underlying data. The application picks up where it left off.
* Repositories should throw a custom exception, never file-specific exceptions.
* Repository and service classes must be fully tested with both negative and positive cases. Do not use your "production" data file to test your repository.
* Solar panel material should be a Java enum with five values. Since solar technology is changing quickly, an enum may be a risky choice. The requirement is included specifically to practice with enums.

### Stretch Goals
* In the Find Panels by Section scenario, provide a list of existing sections to choose from.
* Add search scenarios: find by installation year ranges, specific rows in sections, or specific materials.
* If a panel is a duplicate, don't make the user re-enter all of the data. Instead, offer to let them change a row, column, or section to prevent the duplicate. (Maybe they mis-keyed one value.)
* Add a bulk update feature.