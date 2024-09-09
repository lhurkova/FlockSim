# FlockSim
## User Manual
### Introduction
FlockSim is a simulation enviroment used for agent-based flocking simulations. Application is based on plug-ins that can be loaded and used for the simulation. Plug-ins represents flocking models with different behavior and parameters and FlockSim already contains two plug-ins ready to use. FlockSim has a graphical user interface and displays graphically the course of the simulation.
### Build the FlockSim
To build the simulatin use `mvn compile` in ./FlockSim directory of the project.
### Run the FlockSim
Project needs to be run on JDK 21 or newer. To run the FlockSim use `mvn exec:java` in ./FlockSim directory of the project.
### Graphical User Interface
FlockSim is a program with graphical user interface. After the program starts the main window with starting page is displays. In the top section of the window there is a menu with corresponding options: "*FlockSim*", "*File*", "*Run*", "*Help*". Toolbar is situated under the menu and contains shortcut buttons for actions in menu.
#### Menu
Option "*FlockSim*" contains basic program actions.
- "*FlockSim*" > "*About FlockSim*" displays brief information a the application
- "*FlockSim*" > "*Settings*" displays a settings dialog
- "*FlockSim*" > "*Quit FlockSim*" quits the application

Option "*File*" contains actions with plug-ins.
- "*File*" > "*Load plug-in*" opens a file choosing dialog used for loading a new plug-in
- "*File*" > "*Remove plug-in*" contains items with names of loaded plug-ins and by clicking on specifief name of a plug-in, the plug-in will be removed from the application

Option "*Run*" is used for running the simulation and regulating the simulation run.
- "*Run*" > "*Run simulation*" contains items with names of loaded plug-ins and by clicking on specifief name of a plug-in, the simulation will be run with this plug-in
- "*Run*" > "*Pause simulation*" pauses the running simulation
- "*Run*" > "*Continue simulation*" continues the paused simulation
- "*Run*" > "*Stop simulation*" stops the running simulation

Option "*Help*" contains link for additional infromation.
- "*Help*" > "*Online doc*" opens link to online documentation in default browser
- "*Help*" > "*Show front page*" displays the startting page
#### Settings
Settings dialog can be dislays be selecting "*FlockSim*" > "*Settings*" in menu or clicking "*Settings*" button in toolbar. Settings contains two tabbes called "*Simulation*" and "*Plug-ins*". "*Simulation*" tab contains basic simulation parameters that are independent of the plug-ins, for example: number of steps of the simulation, number of birds in the simulation, color of the birds and size of the birds. "*Plug-ins*" tab contains parameters for loaded plug-ins and each plug-in has its own tab with prameters with its own name.
### VFlockAgent plug-in
VFlockAgent plug-in is used for simulating
the fromation of a V-shaped flock inspired by V-shaped flocks formed by geese and other large birds. To use this plug-in in FlockSim you have to first build the pluigin with executing `mvn compile` command in ./VFlockAgent directory of the project. To load VFlockAgent plug-in you have to select "*File*" > "*Load plug-in*" in FlockSim menu and choose JAR file created while building the VFlockAgent subproject. After loading select the "V-Flock Model" in toolbar or menu of the FlockSim and run the simulation. To change parameters open "*FlockSim*" > "*Settings*" and select "Plug-ins" tab and then "V-Flock Model".
#### Parameters
View of the bird
- how far can bird see, bird only reacts to other birds that are inside the range of its view

Minimum distance between the birds
- if a bird is closer to another bird that this value, it will try to put more distance between them

Field of view in degrees
- birds only reacts to other birds that are inside its field of view

Wingspan
- wingspan of the bird influences the shape of the V-flock

Cohesion force
- how much is the bird trying to get closer to its closest neighbour

Separation force
- how much is the bird tying to get away from birds that are too close

Clear view force
- how much is the bird tying to find a place in the flock with a clear view
#### References
[Nathan, A., & Barbosa, V. C. (2008). V-like formations in flocks of artificial birds. Artificial life, 14(2), 179-188.](https://arxiv.org/pdf/cs/0611032)

### BoidsAgent plug-in
VFlockAgent plug-in is used for simulating a fromation nad behavior of flocks. Plug-in is inspired by BOIDS model. Each bird in the flock only reacts to its surrounding and in each step of the simulatin tries to get to the center of the flock, align its direction with other birds and get away from birds that are too close. 

To use this plug-in in FlockSim you have to first build the pluigin with executing `mvn compile` command in ./BoidsAgent directory of the project. To load BoidsAgent plug-in you have to select "*File*" > "*Load plug-in*" in FlockSim menu and choose JAR file created while building the BoidsAgent subproject. After loading select the "BOIDS Model" in toolbar or menu of the FlockSim and run the simulation. To change parameters open "*FlockSim"* > "*Settings*" and select "Plug-ins" tab and then "BOIDS Model".
#### Parameters
View of the bird
- how far can bird see, bird only reacts to other birds that are inside the range of its view

Minimum distance between the birds
- if a bird is closer to another bird that this value, it will try to put more distance between them

Maximum turning angle in degrees
- during one step bird cannot turn more than this angle

Cohesion force
- how much is the bird trying to get closer to the center of the flock

Separation force
- how much is the bird tying to get away from birds that are too close

Alignment force
- how much is the bird tying to align its direction with other birds
#### References
[Reynolds, C. W. (1987) Flocks, Herds, and Schools: A Distributed Behavioral Model, in Computer Graphics, 21(4) (SIGGRAPH '87 Conference Proceedings) pages 25-34.](http://www.cs.toronto.edu/~dt/siggraph97-course/cwr87/)

### Implementing your own plug-in
To create your own plug-in you have to implement *cz.cuni.mff.hurkovalu.flocksim.spi.FlockModel* and *cz.cuni.mff.hurkovalu.flocksim.spi.Agent* interfaces. While implementing the interfaces use classes inside the *cz.cuni.mff.hurkovalu.flocksim.spi* package and class *cz.cuni.mff.hurkovalu.flocksim.Flock* that provides several methods that can be used to get information about the flock. FlockSim also supports display and modification of the plug-in parameters in graphical user interface. To define your own parameter that would be displayed in FlockSim use descriptors in *cz.cuni.mff.hurkovalu.flocksim.spi.descriptors* package.

The completed plug-in has to contain provider-configuration file in the resource directory META-INF/services. The name of the file has to be *cz.cuni.mff.hurkovalu.flocksim.spi.FlockModel* and file should contain the fully-qualified name of your class that implemets the FlockModel interface.
