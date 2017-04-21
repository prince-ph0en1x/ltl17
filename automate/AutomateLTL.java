/**
*	@author : Aritra Sarkar
*	@date	: 09-04-2017
*	@purpose: Tries to automate the entire LTL procedures
*/

import java.io.*;
import java.util.concurrent.TimeUnit;
class AutomateLTL
{
	public static void main(String args[]) throws Exception
	{
		System.out.println("Welcome\n");
		Terminal obj1 = new Terminal();
		obj1.RunCmd(Integer.parseInt(args[0]));
		System.out.println("\nThanks");
	}
}

class Terminal
{
	public void RunCmd(int pno) throws Exception
	{
		ProcessBuilder pb;
		Process p;
		BufferedReader br;
		String line;
		int step = 0;
		
		BufferedReader term = new BufferedReader(new InputStreamReader(System.in));
		
		pb = new ProcessBuilder("pwd").redirectErrorStream(true); 
		p = pb.start();
		p.waitFor();
		br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		line = br.readLine();
		final String pathRepo = line.substring(0,line.lastIndexOf('/'))+"/";
		System.out.println("Project Directory : "+pathRepo+"\n");  
			
		System.out.println("... running Problem "+pno+" Compile\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("gcc",pathRepo+"LTL17/Problem"+pno+"/Problem"+pno+".c","-o",pathRepo+"ltl17c/Problem"+pno+".out").redirectErrorStream(true); 
			p = pb.start();
			p.waitFor();
			br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			line = br.readLine(); 
			while(line != null) 
			{ 
				System.out.println("Output Lines : "+line); 
				line=br.readLine(); 
			}
		}
		else
			System.out.println("... skipping Step 1");
			
		System.out.println("... running Problem "+pno+" LearnSM\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("java","-cp",pathRepo+"learnlib.jar:"+pathRepo+"bin/","RERSlearner.RERSExperiment",pno+"",pathRepo).redirectErrorStream(true); 
			pb.redirectOutput(new File(pathRepo+"optsm/Problem"+pno+"/Log file.txt"));
			p = pb.start();
			p.waitFor();
		}
		else
			System.out.println("... skipping Step 2");
			
		System.out.println("... running Problem "+pno+" ConvLabel\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("java","-cp",pathRepo+"convlabel/","ConvLabel",pno+"",pathRepo).redirectErrorStream(true); 
			p = pb.start();
			p.waitFor();
			br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			line = br.readLine(); 
			while(line != null) 
			{ 
				System.out.println("Output Lines : "+line); 
				line=br.readLine(); 
			}
		}
		else
			System.out.println("... skipping Step 3");
		
		System.out.println("... running Problem "+pno+" MainDot2Nu\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("powershell","gc",pathRepo+"convlabel/dots/p"+pno+".dot","|",pathRepo+"postprocessing/LTL/dist/build/MainDot2Nu/MainDot2Nu","|","out-file",pathRepo+"smv/Problem"+pno+"_s2.smv","-encoding","ascii").redirectErrorStream(true); 
			p = pb.start();
			p.waitFor();
			br=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			line = br.readLine(); 
			while(line != null) 
			{ 
				System.out.println("Output Lines : "+line); 
				line=br.readLine(); 
			}
		}
		else
			System.out.println("... skipping Step 4");
		
		System.out.println("... running Problem "+pno+" ConvISym\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("java","-cp",pathRepo+"convisym/","ConvISym",pno+"",pathRepo).redirectErrorStream(true); 
			p = pb.start();
			p.waitFor();
			br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			line = br.readLine(); 
			while(line != null) 
			{ 
			System.out.println("Output Lines : "+line); 
			line=br.readLine(); 
			}
		}
		else
			System.out.println("... skipping Step 5");
		
		System.out.println("... running Problem "+pno+" MainR2Nu\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("powershell","gc",pathRepo+"LTL17/Problem"+pno+"/constraints-Problem"+pno+".txt","|",pathRepo+"postprocessing/LTL/dist/build/MainR2Nu/MainR2Nu","|","out-file",pathRepo+"smv/Problem"+pno+"_s1.smv","-encoding","ascii").redirectErrorStream(true); 
			pb.redirectOutput(new File(pno+"_S6_Log.txt"));
			p = pb.start();
			p.waitFor();
		}
		else
			System.out.println("... skipping Step 6");
			
		System.out.println("... running Problem "+pno+" FullFile\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("powershell","cp",pathRepo+"smv/Problem"+pno+"_s3.smv",pathRepo+"smv/p"+pno+".smv",";","gc",pathRepo+"smv/Problem"+pno+"_s1.smv","|","Add-Content","-Path",pathRepo+"smv/p"+pno+".smv").redirectErrorStream(true); 
			p = pb.start();
			p.waitFor();
			br=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			line = br.readLine(); 
			while(line != null) 
			{ 
				System.out.println("Output Lines : "+line); 
				line=br.readLine(); 
			}
		}
		else
			System.out.println("... skipping Step 7");
		
		System.out.println("... running Problem "+pno+" NuSMV\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder(pathRepo+"NuSMV-2.6.0-Linux/bin/NuSMV",pathRepo+"smv/p"+pno+".smv").redirectErrorStream(true); 
			pb.redirectOutput(new File(pathRepo+"gensoln/opt_nusmv/p"+pno+".txt"));
			p = pb.start();
			p.waitFor();
		}
		else
			System.out.println("... skipping Step 8");
		
		System.out.println("... running Problem "+pno+" GenSoln\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("java","-cp",pathRepo+"gensoln/","GenSoln",pno+"",pathRepo).redirectErrorStream(true); 
			p = pb.start();
			p.waitFor();
			br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			line = br.readLine(); 
			while(line != null) 
			{ 
			System.out.println("Output Lines : "+line); 
			line=br.readLine(); 
			}
			System.out.println("\nResult : "+pathRepo+"gensoln/opt_nusmv/p"+pno+"_soln.csv");
		}
		else
			System.out.println("... skipping Step 9");
		
	}
}
