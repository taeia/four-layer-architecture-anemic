# 4-layer architecture (anemic)

Companion repository for Chapter 3 of [*Domain-Centric Architecture*](https://github.com/taeia/domain-centric-architecture): the library lending application in the four layers of Domain-Driven Design, built the way most of the industry builds them.

The business layer of Chapter 2 is split in two. An application layer coordinates the operations, and a domain layer holds the model of the business. The domain layer contains the five entities, with private fields and public getters and setters, and nothing else. Every rule lives in one of three application services. This is the Controller, Service, Entity, Repository shape that frameworks and tutorials produce, and Fowler's name for its model is the anemic domain model. The chapter explains what the fourth layer was meant to hold and why it holds nothing here.

Each layer is a Maven module, and each module's `pom.xml` names the layers it depends on. `domain` depends on nothing. `infrastructure` depends on `domain`, because the repositories store the entities. `application` depends on both. `presentation` calls only `application`, and lists `domain` and `infrastructure` as well, because the entities travel to the screen and the entry point builds the repositories. Note which module is at the bottom: the domain, not the infrastructure. The chapter explains why that is already true here and why nobody notices yet.

The library it implements, with its rules and scenarios, is described in [Chapter 0](https://github.com/taeia/domain-centric-architecture/blob/main/chapters/00-the-library.md). The chapter about this code is [Chapter 3](https://github.com/taeia/domain-centric-architecture/blob/main/chapters/03-four-layer-architecture-anemic.md).

## Running

You need Java 25 and Maven. JavaFX is a Maven dependency, so nothing else has to be installed.

```
mvn install
mvn -pl presentation javafx:run
```

The first command builds all four modules and runs the tests. The second starts the application from the presentation module, which is the only one with a main class. Repeat the first command after changing anything outside `presentation`.

A window opens with the library in its starting state. The date picker in the toolbar sets the library's idea of today, so you can walk through the scenarios of Chapter 0 and check the due dates and fines by hand. The *Reset library* button returns to the starting state.

To start it from an IDE, run `library.presentation.Launcher`. Running `MainWindow` directly fails with *JavaFX runtime components are missing*, because JavaFX is on the classpath and the Java launcher checks for the modules whenever the main class extends `Application`. `Launcher` does not extend it, so the check is skipped.

## Tests

```
mvn test
```

`ScenarioTest` lives in the `application` module and has one test per scenario of Chapter 0, with the same name. The tests call the three application services directly, with the repositories loaded with the starting state. They do not start JavaFX, and neither the application module nor the domain module has JavaFX on its classpath.

There are no unit tests of single rules. The entities have no behaviour to test, and every rule lives inside a service method next to repository calls, so the smallest thing that can be tested is a service with its repositories. The chapter explains why.

## Layout

```
pom.xml                        the parent: lists the four modules, holds the versions
presentation/                  the screens: the main window and five dialogs
  pom.xml                      depends on application (and on domain and infrastructure, see above)
  src/main/java/library/presentation/
    MainWindow.java            wires the layers together in start()
    Launcher.java              starts MainWindow from an IDE
    BorrowDialog.java
    ReturnDialog.java
    RenewDialog.java
    ReserveDialog.java
    PayDialog.java
application/                   the application layer: every rule of Chapter 0 is here
  pom.xml                      depends on domain and infrastructure
  src/main/java/library/application/
    LoanService.java           borrow, return, renew, and the questions about loans and copies
    ReservationService.java    reserve, and the questions about books and reservations
    MemberService.java         pay, and the questions about members and notifications
    LibraryException.java      thrown when the library refuses an operation
    Clock.java                 the library's idea of today
  src/test/java/library/application/
    ScenarioTest.java          one test per scenario
domain/                        the domain layer: the entities, with getters and setters and nothing else
  pom.xml                      depends on nothing
  src/main/java/library/domain/
    Book.java
    Copy.java
    Member.java
    Loan.java
    Reservation.java
infrastructure/                storage and messaging
  pom.xml                      depends on domain
  src/main/java/library/infrastructure/
    BookRepository.java        one repository per entity, in memory
    CopyRepository.java
    MemberRepository.java
    LoanRepository.java
    ReservationRepository.java
    Notifier.java              sends messages to members, by adding them to a list
    StartingState.java         the starting state of Chapter 0
```
