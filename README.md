# State Machine Learning for RERS LTL 2017 problems

### Project Details

 This Repository is part of the *CS4110 - Software Testing and Reverse Engineering* course at TU Delft.
 It hosts the code for Assignment 2 (State Machine Learning) for
 * Aritra Sarkar (4597982 MSc. Computer Engineering)
 * Prashanth GL (4610849 MSc. Computer Engineering)
 * Vivek Subramanian (4601211 MSc. )

### Setup Guide

 Installing required softwares
 ```sh
 $ curl https://packages.microsoft.com/keys/microsoft.asc | sudo apt-key add -
 $ curl https://packages.microsoft.com/config/ubuntu/16.04/prod.list | sudo tee /etc/apt/sources.list.d/microsoft.list
 $ sudo apt-get update
 $ sudo apt-get install -y powershell
 $ sudo apt-get install graphviz
 ```
 State Machine Learning for LTL 2017
 ```sh
 $ git clone https://github.com/prince-ph0en1x/ltl17
 $ cd ltl17/automate/
 $ java AutomateLTL <problem_no>
 ```

### Automation Steps

 The following table details the various processes invoked for the automation.
 
 | Step | Name 			| Inputs 				| Executable 												| Outputs				|
 | ---- |:-------------:| ---------------------:| ---------------------------------------------------------:|----------------------:|
 | 1	| Compile		| source C code 		| gcc 														| executable			|
 | 2	| LearnSM		| executable			| ltl17/bin/RERSlearner/RERSExperiment.class				| model.dot				|
 | 3	| ConvLabel		| model.dot				| ltl17/convlabel/ConvLabel.class							| corrected dot			|
 | 4	| MainDot2Nu	| corrected dot			| ltl17/postprocessing/LTL/dist/build/MainDot2Nu/MainDot2Nu	| model nu				|
 | 5	| ConvISym		| model nu				| ltl17/convisym/ConvISym.class       						| corrected model nu	|	
 | 6	| MainR2Nu		| constraints			| ltl17/postprocessing/LTL/dist/build/MainR2Nu/MainR2Nu 	| constraints nu		|
 | 7	| FullFile		| both nu 				| shell script       										| full nu				|
 | 8	| NuSMV			| full nu 				| ltl17/NuSMV-2.6.0-Linux/bin/NuSMV       					| solution				|	
 | 9	| GenSoln		| solution 				| ltl17/gensoln/GenSoln.class       						| satisfiability		|
 
### Sample Run

 ```sh
 osboxes@osboxes:~/trygit/ltl17/automate$ java AutomateLTL 3
 Welcome
 
 Project Directory : /home/osboxes/trygit/ltl17/
 
 ... running Problem 3 Compile		 [0/1]?
 1
 ... running Problem 3 LearnSM		 [0/1]?
 1
 ... running Problem 3 ConvLabel		 [0/1]?
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
 ```
 
### FAQ

 * Which algorithms are used for state machine learning?
   * TTT algorithm is used for Learning and Wp and Random Walk for Testing
 * How can I set these?
   * You need to open the .project file in Eclipse (or import the files from the src folder)
   * In ltl17/src/RERSlearner/RERSExperiment.java you can set the learning and testing method
   * Currently, the supported values for LearningMethod are { LStar, RivestSchapire, TTT, KearnsVazirani } and for TestingMethod { RandomWalk, WMethod, WpMethod, UserQueries }
 * Is the postprocessing folder same as in the Credit link?
   * No, the Haskell codes in the link is not suited for LTL 2017 problems, as they allow only input alphabets of 5, 10 or 20 size. LTL 2017 problems also has input alphabets of 15. This is the only change done to the source codes. Also, in the cabal file, the build dependency is reduced from 4.9 to 4.8.
 * How long should I wait for the model.dot file?
   * Problem 1-3 (go grab a coffee), Problem 4-6 (take your dog for a walk), Problem 7-9 (go for a weekend trip to Amsterdam). Jokes apart, this is the main task of predicting how the model is evolving, and whether any new constraints are getting satisfiable. It depends on the chosen learning and testing method. It is wise to run the LTL satisfiability on the hypothesis files once in a while to check if it has stabilised.
 * What if I manually terminated the learning process?
   * This part is not automated. For manual termination, you need to rename the last hypothesis file to model.dot and rerun the automate program (and skip step 1 & 2).
 * Why is ConvLabel and ConvISym required?
   * While the requirement is explained in the video, the reason for the errors of missing iJ and label ids with 0 now working in RERSLearner was beyond our scope. RegEx handling is an ad-hoc fix. And this is indeed an open issue.
 * How can I run in Windows system?
   * Though Java is machine independent, the process builder codes for shell script are based on the host system's OS, Ubuntu in our case. So right now there is no support for Windows.

### References

 * https://github.com/TUDelft-CS4110-20162017/syllabus/tree/master/RERSLearning
 * https://gitlab.science.ru.nl/moerman/rers-2016/tree/master/postprocessing
 * http://nusmv.fbk.eu/NuSMV/download/getting-v2.html