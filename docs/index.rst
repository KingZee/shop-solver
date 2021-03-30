Shop Solver
===========

> An application to solve scheduling problems written in Java.

.. image:: _static/demo.gif
  :alt: Demo Animation

This library provides a framework for solving all three types of scheduling problems (Flow-Shop, Job-Shop, and Open-Shop).

Problem and solution space encoding schemes are optimized across all three types with a permutation schedule definition.

Our software is written in Java, and provides a simple structure for researchers to build upon. It also provides a visual interface for single solvers to display results, and a benchmark function that can run across multiple threads, multiple solvers on multiple problem matrices.

With an output comparing CPU usage, Memory usage, and solution quality across all iterations, it is very easy to implement any "Solver" for any of these types by inheriting from the base abstract Solver class and overriding corresponding methods.

Dependencies
------------

This project is built with JDK11 & Maven. It uses JavaFX 11 with some `JFoenix 9 <https://github.com/jfoenixadmin/JFoenix>`_ components for UI. JMH toolkit is included for micro-benchmarks. JDK 11 can be downloaded from the OpenJDK archive here :  `<https://jdk.java.net/archive/>`_ .

Installation
------------

After automatically installing dependencies through Maven, the project can be run through the javafx-maven-plugin :

.. code-block:: bash

    mvn clean
    mvn javafx:compile
    mvn javafx:run

If you would like to run using IntelliJ's built in runtime, you will need to have JavaFX 11 installed, and include the VM directives :

.. code-block:: bash

    --module-path $PATH_TO_JAVAFX --add-modules javafx.controls,javafx.fxml

Testing
-------

To run the test suite :

.. code-block:: bash

    mvn clean
    mvn surefire:test

The tests are done through the `TestFX <https://github.com/TestFX/TestFX>`_ package.
As it tests every UI use-case, kindly do not move the mouse while the tests are running. It takes one minute on average.

License
-------

Distributed under the Apache license. See `LICENSE` for more information.

.. toctree::
    :caption: Documentation
    :maxdepth: 3

    overview
    interface
    solving

.. toctree::
    :caption: Reference
    :maxdepth: 3

    genindex
    packages

.. toctree::
    :caption: License
    :maxdepth: 5

    license