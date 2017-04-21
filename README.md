Setup:

 Install GRaphViz for Dot File
 Install Eclipse for Java
 Install LearnLib
 Download NuSMV
 Download Git repository
 Install Powershell

Model Learn:

 Compile original C code of LTL RERS17
 Learn model of binary with Eclipse RERSlearner to generate dot file
 Change library dependency in cabal file
 Use dot file and constraint file in post processing Haskell scripts to get smv file in Powershell
 Use smv file in NuSMV to get result file
 Process result file to extract satisfiability for each constraint


Command History:

 sudo apt-get install graphviz

Install Haskell:
 
 sudo apt-get install haskell-platform
 cabal install


Install and Run Powershell:

 curl https://packages.microsoft.com/keys/microsoft.asc | sudo apt-key add -
 curl https://packages.microsoft.com/config/ubuntu/16.04/prod.list | sudo tee /etc/apt/sources.list.d/microsoft.list
 sudo apt-get update
 sudo apt-get install -y powershell
 powershell

Post Processing:
  
  gc .\<constraint_file_path>.txt | .\<MainR2Nu_path> | out-file -Encoding ascii <file1>.smv 
  gc <model_dot_file_path>.dot | .\<MainDot2Nu_path> | out-file -Encoding ascii <file2>.smv
  cp <file2>.smv <result_file>.smv; gc <file1>.smv | Add-Content -Path <file3>.smv
  
NuSMV Processing:
 
 <NuSMV_path> <file3>.smv > <result_file>.txt
 
gc .\convlabel\dots\p1.dot | .\postprocessing\LTL\dist\build\MainDot2Nu\MainDot2Nu | out-file -Encoding ascii Problem1_s2.smv                                                 
cp Problem1_s2.smv p1.smv; gc Problem1_s1.smv | Add-Content -Path p1.smv