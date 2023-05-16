# Project : Mapping

## Frameworks and languages used
* SpringBoot
* Java

## Data flow in the project
Here we have 5 models namely:
Student, 
Laptop,
Book,
Course, 
Address
1. Controller : All models has all the Crud operations : Create(PostMapping), Read(GetMapping), Update(PutMapping), Delete(DeleteMapping).
   these layer interact with the client and service layer.
2. Services : All the operations linked with controller is been implemented with additional logics in service layer, 
   service layer also interact with repository layer.
3. Repository : Repository layer interacts with Database(mySql) and persist the changes requested, We extend the JPA repository to get the 
   predefined function.
4. DataBase : Here i have used mySql database.
5. Util : Util layer is added to do Validation of the data.


## Data Structure Used in Project
* ArrayList
* String Array

## Project Summary
We have implemented the Project to learn Mappings
* One to One (Laptop - Student)
* Many to One (Book - Student)
* Many to Many (Course - Student)




