Compile and run:

In the terminal opened where the source files are, run the following commands.
	javac project02.java
	java project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]

Language of choice:

I decided to use Java because it is the language in which I have more experience and 
I work much faster on it compared to Python or any other language.

Observations:

In terms of performance, I can say even with 10k encounters it barely takes a couple of seconds. 
The only way it may slow down is if there are too many hawks dead, since the random number generator 
keeps producing numbers until it gets 2 alive. This will make the simulation slow only if the 
population is large enough. The initial parameters will determine if there is an ESS. If the 
population has too many doves the few hawks will displace the dove strategy. If the population has 
too many hawks it will only be an ESS if the resource available is greater than the cost of a 
Hawk/Hawk encounter. Otherwise, there won’t be any ESS.

ESS and its relation to Game Theory/AI:

ESS made me understand how nature works with what the paper said in its introduction about 
snakes wrestling instead of using their fangs and the other examples they gave. In terms of 
how it relates to game theory and intelligent systems, since it is a type of strategy it is 
pretty significant to game theory. Game theory bases around game simulations and choosing what 
is the best strategy for a player. For example, with this project the best strategy seems to 
be an equilibrium that can be reached with the correct parameters such as the default ones 
(20% Hawks, 80% Doves, loss>resource). So basically, ESS broadens the capabilities of game 
theory since it can be used in different simulations where other strategies aren’t as efficient.