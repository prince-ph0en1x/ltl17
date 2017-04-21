/**
*	@author : Aritra Sarkar
*	@date	: 14-04-2017
*	@purpose: Evolution of the hypothesis for SML
*/

import java.io.*;
import java.util.regex.*;
import java.util.concurrent.TimeUnit;

class PlotHypos
{
	public static void main(String args[]) throws Exception
	{
		System.out.println("Welcome to the future\n");
		Terminal obj1 = new Terminal();
		obj1.pno = Integer.parseInt(args[0]);
		obj1.RunCmd();
		System.out.println("\nBack to the past");
	}
}

class Terminal
{
	private final String pathRepo = "/home/osboxes/eclipsejava/ltl17/";
	public int pno = 0;
	
	public void RunCmd() throws Exception
	{
		
		ProcessBuilder pb;
		Process p;
		BufferedReader br;
		String line;
		int step = 0;
		BufferedReader term = new BufferedReader(new InputStreamReader(System.in));
		//int[] hypo = {0,13,19,105,109,125,270,291,0}; // Number of hypothesis files to run
		int[] hypo = {0,13,19,105,109,14,270,291,0}; // Number of hypothesis files to run
		
		
		// Runs the State Machine Learning for LTL problem
		// Code Input	: C code executable
		// Code Output	: dot file for model
		System.out.println("... running Problem "+pno+" LearnSM\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			pb = new ProcessBuilder("java","-cp",pathRepo+"learnlib.jar:"+pathRepo+"bin/","RERSlearner.RERSExperiment",pno+"").redirectErrorStream(true); 
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
			System.out.println("... skipping Step 0");
		
		// Correct Dot file labels
		// Code Input	: dot file for model
		// Code Output	: corrected dot file for model
		System.out.println("... running Problem "+pno+" ConvLabel\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			for (int h = 0; h < hypo[pno-1]; h++)
			{
				ConvLabel(h);
				System.out.print("#");
			}
			System.out.println();
		}
		else
			System.out.println("... skipping Step 1");
		
		// Converts Dot to Nu format
		// Code Input	: corrected dot file for model
		// Code Output	: nu file for model
		System.out.println("... running Problem "+pno+" MainDot2Nu\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			for (int h = 0; h < hypo[pno-1]; h++)
			{
				Dot2Nu(h);
				System.out.print("#");
			}
			System.out.println();
		}
		else
			System.out.println("... skipping Step 2");
		
		// Corrects Nu file input symbols
		// Code Input	: nu file for model
		// Code Output	: corrected nu file for model
		System.out.println("... running Problem "+pno+" ConvISym\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			for (int h = 0; h < hypo[pno-1]; h++)
			{
				ConvISym(h);
				System.out.print("#");
			}
			System.out.println();
		}
		else
			System.out.println("... skipping Step 3");
		
		// Merges Nu files for constraints and model
		// Code Input	: nu file for model, constraint
		// Code Output	: full nu file for problem
		System.out.println("... running Problem "+pno+" FullFile\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			for (int h = 0; h < hypo[pno-1]; h++)
			{
				MergeNu(h);
				System.out.print("#");
			}
			System.out.println();
		}
		else
			System.out.println("... skipping Step 4");
		
		// Runs NuSMV to get constraints satisfiability
		// Code Input	: full nu file for problem
		// Code Output	: satisfiability for problem
		System.out.println("... running Problem "+pno+" NuSMV\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			for (int h = 0; h < hypo[pno-1]; h++)
			{
				RunNuSMV(h);
				System.out.print("#");
			}
			System.out.println();
		}
		else
			System.out.println("... skipping Step 5");
		
		// Extract constraints satisfiability
		// Code Input	: satisfiability for problem
		// Code Output	: solution for problem
		System.out.println("... running Problem "+pno+" GenSoln\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			for (int h = 0; h < hypo[pno-1]; h++)
			{
				GenSoln(h);
				System.out.print("#");
			}
			System.out.println();
		}
		else
			System.out.println("... skipping Step 6");
			
		System.out.println("... running Problem "+pno+" ProcLog\t\t [0/1]?");
		step = Integer.parseInt(term.readLine());
		if (step == 1)
		{
			ProcLog();
		}
		else
			System.out.println("... skipping Step 7");
			
		
	}
	
	public void ConvLabel(int h) throws Exception
	{
		BufferedReader br;
		BufferedWriter bw;
		
		br = new BufferedReader(new FileReader(pathRepo+"opteclipse/Problem"+pno+"/hypothesis"+h+".dot"));
		bw = new BufferedWriter(new FileWriter(pathRepo+"plothypos/optStep1/"+pno+"_h"+h+".dot",false));
		
		String regex1 = "(.*)(label=\")(\\d)(\\s.*)";
		Pattern p1 = Pattern.compile(regex1);
		
		int lno;
		boolean found;
		String line;
		while((line = br.readLine()) != null)
		{
			Matcher m1 = p1.matcher(line);
			found = false;
			while(m1.find())
			{	
				lno = Integer.parseInt(m1.group(3));
				bw.write(m1.group(1)+m1.group(2)+(lno-1)+m1.group(4)+"\n");
				found = true;
			}
			if (!found)
				bw.write(line+"\n");
		}	
		
		bw.flush();
		bw.close();
		
		br.close();
	}
	
	public void Dot2Nu(int h) throws Exception
	{
		ProcessBuilder pb;
		Process p;
		BufferedReader br;
		String line;
		
		pb = new ProcessBuilder("powershell","gc",pathRepo+"plothypos/optStep1/"+pno+"_h"+h+".dot","|",pathRepo+"postprocessing/LTL/dist/build/MainDot2Nu/MainDot2Nu","|","out-file",pathRepo+"plothypos/optStep2/"+pno+"_h"+h+".smv","-encoding","ascii").redirectErrorStream(true); 
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
	
	public void ConvISym(int h) throws Exception
	{
		int iAlphSz[] = {5,10,15,10,15,15,10,15,20};
		char alphS = 'K';
		char alphE = (char)(alphS + (iAlphSz[pno-1] - 10));
		
		if (alphE-alphS < 0) return;
		
		BufferedReader br;
		BufferedWriter bw;
		
		br = new BufferedReader(new FileReader(pathRepo+"plothypos/optStep2/"+pno+"_h"+h+".smv"));
		bw = new BufferedWriter(new FileWriter(pathRepo+"plothypos/optStep3/"+pno+"_h"+h+".smv"));
		
		String regex1 = "(.*?)(i["+alphS+"-"+alphE+"])(.*?)";
		Pattern p1 = Pattern.compile(regex1);
		
		String line,linen;
		while((line = br.readLine()) != null)
		{
			Matcher m1 = p1.matcher(line);
			linen = line;
			while(m1.find())
			{	
				linen = line.replaceAll(m1.group(2),"i"+(char)(m1.group(2).charAt(1)-1));
				line = linen;
			}
			bw.write(line+"\n");
		}	
		
		bw.flush();
		bw.close();
		
		br.close();
	}
	
	public void MergeNu(int h) throws Exception
	{
		ProcessBuilder pb;
		Process p;
		BufferedReader br;
		String line;
		
		pb = new ProcessBuilder("powershell","cp",pathRepo+"plothypos/optStep3/"+pno+"_h"+h+".smv",pathRepo+"plothypos/optStep4/"+pno+"_h"+h+".smv",";","gc",pathRepo+"smv/Problem"+pno+"_s1.smv","|","Add-Content","-Path",pathRepo+"plothypos/optStep4/"+pno+"_h"+h+".smv").redirectErrorStream(true); 
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
	
	public void RunNuSMV(int h) throws Exception
	{
		ProcessBuilder pb;
		Process p;
		BufferedReader br;
		String line;
		
		pb = new ProcessBuilder(pathRepo+"NuSMV-2.6.0-Linux/bin/NuSMV",pathRepo+"plothypos/optStep4/"+pno+"_h"+h+".smv").redirectErrorStream(true); 
		pb.redirectOutput(new File(pathRepo+"plothypos/optStep5/"+pno+"_h"+h+".txt"));
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
	
	public void GenSoln(int h) throws Exception
	{
		BufferedReader br, br1;
		BufferedWriter bw, bw1;
		
		BufferedReader term = new BufferedReader(new InputStreamReader(System.in));
		
		br = new BufferedReader(new FileReader(pathRepo+"plothypos/optStep5/"+pno+"_h"+h+".txt"));
		bw = new BufferedWriter(new FileWriter(pathRepo+"plothypos/optStep6/"+pno+"_h"+h+".txt"));
		br1 = new BufferedReader(new FileReader(pathRepo+"plothypos/optStep6/"+pno+".csv"));
		bw1 = new BufferedWriter(new FileWriter(pathRepo+"plothypos/optStep6/"+pno+"temp.csv"));
		
		String regex1 = "^(-- specification )(?:.*)(is )(.*)";
		Pattern p1 = Pattern.compile(regex1);
		System.out.println("H = "+h);
		int cno = 0;
		String line;
		while((line = br.readLine()) != null)
		{
			Matcher m1 = p1.matcher(line);
			while(m1.find())
			{	
				bw.write(pno+", "+(cno++)+", "+m1.group(3)+"\n");
				if(m1.group(3).trim().equals("true")) 	bw1.write(br1.readLine()+",1\n");
				else					bw1.write(br1.readLine()+",0\n");
			}
		}	
		
		bw.flush();
		bw.close();
		bw1.flush();
		bw1.close();
		
		br1.close();
		br.close();
		
		ProcessBuilder pb;
		Process p;
		
		pb = new ProcessBuilder("cp","-f",pathRepo+"plothypos/optStep6/"+pno+"temp.csv",pathRepo+"plothypos/optStep6/"+pno+".csv").redirectErrorStream(true); 
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
	
	public void ProcLog() throws Exception
	{
		BufferedReader br;
		BufferedWriter bw;
		
		br = new BufferedReader(new FileReader(pathRepo+"opteclipse/Problem"+pno+"/Log file.txt"));
		bw = new BufferedWriter(new FileWriter(pathRepo+"plothypos/optStep6/"+pno+".csv",true));
		
		String regex1 = "(.*)(size: )(\\d*)( states)";
		Pattern p1 = Pattern.compile(regex1);
		
		int lno;
		boolean found;
		String line;
		bw.write("0");
		while((line = br.readLine()) != null)
		{
			Matcher m1 = p1.matcher(line);
			found = false;
			while(m1.find())
			{	
				lno = Integer.parseInt(m1.group(3));
				//System.out.println(m1.group(3));
				bw.write(","+lno);
				//found = true;
			}
			//if (!found)
				//bw.write(line+"\n");
		}	
		
		bw.flush();
		bw.close();
		
		br.close();
	}
}
