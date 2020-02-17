# Shop Solver ![Java CI](https://github.com/KingZee/shop-solver/workflows/Java%20CI/badge.svg)
> An application to solve scheduling problems written in Java.

![Demo Animation](https://i.imgur.com/MT5ZHKp.gif)

This library can solve all three types of scheduling problems (Flow-Shop, Job-Shop, and Open-Shop).

Problem and solution space encoding schemes are optimized across all three types with a permutation schedule definition. 

It is extremely easy to implement any "Solver" for any of these types by inheriting from the base abstract Solver class and overriding corresponding methods.

## Dependencies :

This project is built with JDK11 & Maven. It uses JavaFX 11 with some [JFoenix 9](https://github.com/jfoenixadmin/JFoenix) components for UI. JMH toolkit is included for micro-benchmarks. JDK 11 can be downloaded from the OpenJDK archive here : https://jdk.java.net/archive/.

## Installation :

After automatically installing dependencies through Maven, the project can be run through the javafx-maven-plugin :

    mvn clean
    mvn javafx:compile
    mvn javafx:run

If you would like to run using IntelliJ's built in runtime, you will need to have JavaFX 11 installed, and include the VM directives : 

    --module-path $PATH_TO_JAVAFX --add-modules javafx.controls,javafx.fxml

## Testing : 

To run the test suite : 

    mvn clean
    mvn surefire:test

The tests are done through the [TestFX](https://github.com/TestFX/TestFX) package. 
As it tests every UI use-case, kindly do not move the mouse while the tests are running. It takes one minute on average.

## Roadmap

- [x] Build initial framework.
- [x] Add benchmarking tools for algorithm comparison.
- [x] Add test suite.
- [ ] Add example(s) of local search algorithm(s).
- [ ] Add example(s) of genetic algorithm(s).
- [ ] Add integrations for GPU computation (CUDA).

## License

Distributed under the Apache license. See ``LICENSE`` for more information.
