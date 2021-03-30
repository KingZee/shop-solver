Overview
========

The Shop Solver repository is split into two components: The matrix solving component, and the result display component. Developers looking to contribute to the repo can do so by adding their own Solving algorithm that inherits from the Abstract Class “Solver”, or by improving on the “Controller” class, that manages the results display, and all the UI-related elements.

## Releases

### [Genetic Algorithm 1.3.0](https://github.com/KingZee/shop-solver/releases/tag/v1.3.0)

This version introduces : 

* Genetic algorithm : 
    - A genetic algorithm has been added to run the solving problems. It can take number of populations and iterations as parameters.

- Jar executable : <https://github.com/KingZee/shop-solver/releases/download/v1.3.0/shop-solver-1.3.0.jar>
- Windows executable : <https://github.com/KingZee/shop-solver/releases/download/v1.3.0/Shop.Solver-1.3.0.exe>

### [Benchmark Version 1.2.0](https://github.com/KingZee/shop-solver/releases/tag/v1.2.0)

This version introduces : 

* Benchmark mode : 
    - You can run multiple algorithms, defining a max matrix size, and number of runs. The benchmark will generate a random set of problems within the defined matrix size and compare results, runtime, and memory usage.

* Fixes : 
    - Fixed 0 values causing solving errors.
    - Improved thread control code, solver now doesn't use the full CPU and threads will be killed on cancel.
    - Better memory managament and garbage collection.

- Jar executable : <https://github.com/KingZee/shop-solver/releases/download/v1.2.0/shop-solver-1.2.0.jar>
- Windows executable : <https://github.com/KingZee/shop-solver/releases/download/v1.2.0/Shop.Solver-1.2.0.exe>

#### Jar Instructions :
JavaFX is now packaged with the jar executable.  Simply have any JRE higher than 9 to run it.
The windows executable is still packaged with Java 8 until issues with javafx-maven-compiler are solved. 

### [Stable Build 1.1.0](https://github.com/KingZee/shop-solver/releases/tag/v1.1.0)

This version introduces : 

* A quick solver algorithm for all 3 scheduling type, that can solve to a high level of accuracy for a minimum overhead.

* Greatly improved the speed of Open-shop exact solver by re-writing the algorithm.

* Added error UI display and fixed .jar from previous build.

Since JavaFX 11 is unable to be included with the .jar package, to run it, it is necessary to have it downloaded. 

Once you have it installed, on any OS, just run : 

     java --module-path %JAVAFX_LIB_PATH% --add-modules javafx.controls,javafx.fxml -jar /path/to/this/jar.jar

The windows executable is standalone, but it is packaged with Java 8.

- Jar executable : <https://github.com/KingZee/shop-solver/releases/download/v1.1.0/shop-solver-1.1.0.jar>
- Windows executable : <https://github.com/KingZee/shop-solver/releases/download/v1.1.0/Shop.Solver-1.1.0.exe>

### [Initial Build 1.0.0](https://github.com/KingZee/shop-solver/releases/tag/v1.0.0)

This release has a single functional "Exact Solver" algorithm. Most combinations over 5 machines x 5 jobs will require a relatively high overhead, so kindly do not use large matrices with this version.

The next release will probably include a "Quick Solver", a non-exact heuristic for a more user-friendly software.

- Jar Executable : <https://github.com/KingZee/shop-solver/releases/download/v1.0.0/shop-solver-1.0.0.jar>
- Windows Executable : <https://github.com/KingZee/shop-solver/releases/download/v1.0.0/Shop.Solver-1.0.0-win.exe>
