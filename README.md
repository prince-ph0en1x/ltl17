# State Machine Learning for RERS LTL 2017 problems

### Setup guide for Ubuntu

 * Install GraphViz
 * Install Eclipse (if you want to change the learning/testing algorithm)
 * Install Powershell
 * git clone https://github.com/prince-ph0en1x/ltl17
 * cd ltl17/automate/
 * java AutomateLTL <problem_no>
 
### Sample Run

 osboxes@osboxes:~/trygit/ltl17/automate$ java AutomateLTL 3
 Welcome
 
 Project Directory : /home/osboxes/trygit/ltl17/
 
 ... running Problem 3 Compile		 [0/1]?
 1
 ... running Problem 3 LearnSM		 [0/1]?
 1
 ... running Problem 3 ConvLabel	 [0/1]?
 1
 ... running Problem 3 MainDot2Nu	 [0/1]?
 1
 ... running Problem 3 ConvISym		 [0/1]?
 1
 ... running Problem 3 MainR2Nu		 [0/1]?
 1
 ... running Problem 3 FullFile		 [0/1]?
 1
 ... running Problem 3 NuSMV		 [0/1]?
 1
 ... running Problem 3 GenSoln		 [0/1]?
 1
 
 Result : /home/osboxes/trygit/ltl17/gensoln/opt_nusmv/p3_soln.csv
 
 Thanks

### FAQ

 * Which Learning algorithm is used?
  * TTT 
 * Which Testing algorithm is used?

### Contacts

 * Aritra Sarkar
 * Prashanth GL
 * Vivek Subramaniam
 
### Credits

 * https://github.com/TUDelft-CS4110-20162017/syllabus/tree/master/RERSLearning
 * https://gitlab.science.ru.nl/moerman/rers-2016/tree/master/postprocessing
 * http://nusmv.fbk.eu/NuSMV/download/getting-v2.html
 
### Notes

 curl https://packages.microsoft.com/keys/microsoft.asc | sudo apt-key add -
 curl https://packages.microsoft.com/config/ubuntu/16.04/prod.list | sudo tee /etc/apt/sources.list.d/microsoft.list
 sudo apt-get update
 sudo apt-get install -y powershell
 sudo apt-get install graphviz
 sudo apt-get install haskell-platform
 cabal install
