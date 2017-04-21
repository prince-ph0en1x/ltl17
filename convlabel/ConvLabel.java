/**
*	@author : Aritra Sarkar
*	@date	: 08-04-2017
*	@purpose: Take dot file generated by Eclipse RERSlearner to adjust label names from 0
*/

import java.io.*;
import java.util.regex.*;

class ConvLabel
{
	public static void main(String args[]) throws IOException
	{
		Analyse obj1 = new Analyse();
		obj1.Regexp(args[1], Integer.parseInt(args[0]));
	}
}

class Analyse
{
	public void Regexp(String pathRepo, int pno) throws IOException
	{
		BufferedReader br;
		BufferedWriter bw;
		
		BufferedReader term = new BufferedReader(new InputStreamReader(System.in));
		
		//System.out.print("\nEnter Problem Number : ");
		//int pno = Integer.parseInt(term.readLine());
		
		br = new BufferedReader(new FileReader(pathRepo+"opteclipse/Problem"+pno+"/Problem"+pno+"_model.dot"));
		bw = new BufferedWriter(new FileWriter(pathRepo+"convlabel/dots/p"+pno+".dot"));
		
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
}
