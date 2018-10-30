## Vodafone ASSIGNMENT


## General Description 

###  1.technologies
* spring boot, maven
* postgres  for db. 
(Unfortunately due to some problems installing postgis for geospatial data
data is stored in normal format)
* soap Ui and postman for testing the apis
* dao layer, jpa

###  2. Input
* Input is generated in bootstrap pkg on application start up and points are stored in db.
* Number of location coordinates and their min and max respectively are configurable in application.properties file.

###  3.API
* Soap request returns an object of PointTwoD
* Initial Wsdl is nearestNeighborWsdl.wsdl   and is served at /service/nearest-neighbor
* Rest request is served at {ip:port}/location/gfpv?counterValue={value}
 example : http://localhost:8080/location/gfpv?counterValue=1

### 4.General Instructions 
1.Create an db schema of postgres
2.In application.properties change according to your database configuration
        spring.datasource.url=jdbc:postgresql://{ip}:5432/{dbname}
        spring.datasource.username={username}
        spring.datasource.password={password}
example
        spring.datasource.url=jdbc:postgresql://localhost:5432/vodafone
        spring.datasource.username=postgres
        spring.datasource.password=nikolas
2.   Maven Clean,Install and ready to deploy.
3.   Deploy application through start button of your ide or from terminal ith command : java -jar target\vodafone-0.0.1-SNAPSHOT.jar
4.   Database is auto created. In application.properties keep
     spring.jpa.hibernate.ddl-auto = update if you don't want to recreate 
     database schema every time you restart the application
5.    Create a Soap request with initial wsdl : nearestNeighborWsdl.wsdl.
      Provide latitude and longitude you like to find nearest neighbor in double format.
6.   Hit url described above for getting most frequent points        


### 5.TODOS
* Due to time limitations 
* Create a balanced set by spliting at the median value in every directional split
* Add logger and catch exceptions 
* Add Junit tests 
Can be delivered by the end of the week 

#### General Architecture and Design
* Packages - Classes-Interfaces
// TODO more details for classes
    
    * Model:
        PointTwoD : A class that creates Location coordinates
         to store in database and/or Data Structures
        KdTree :Data structure that stores objects of Class PointToD
        Has inner class of Nodes and 2 methods for insertion and nearest neighbor search
        RectHV : Class that creates Rectangles in hich nearest neighbor algorithm will search
                 edges of rectangles are splits in 2D trees.
    * RestController:
        LocationController : Returns frequent nearest neighbors
    * Soap endpoint : 
        NearestNeighborPoint with soap configuration for Soap reuests
    * Dto
        Point2DDto : Data transfer Object for Ret Controller
    * Repositories :
        LocationRepository jpa transaction    
    * Utils
        Constants and static functions for computation or anything else 
    * xml.point :
      Contains all Request and Response objects as described in the point.xsd contract


