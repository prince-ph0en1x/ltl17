/**
*	@author : Aritra Sarkar
*	@date	: 09-04-2017
*	@purpose: Correct input alphabet missing iJ
*/

import java.io.*;
import java.util.regex.*;

class ConvISym
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
		int iAlphSz[] = {5,10,15,10,15,15,10,15,20};
		char alphS = 'K';
		char alphE = (char)(alphS + (iAlphSz[pno-1] - 10));
		
		if (alphE-alphS < 0) return;
		
		BufferedReader br;
		BufferedWriter bw;
		
		br = new BufferedReader(new FileReader(pathRepo+"smv/Problem"+pno+"_s2.smv"));
		bw = new BufferedWriter(new FileWriter(pathRepo+"smv/Problem"+pno+"_s3.smv"));
		
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
}
