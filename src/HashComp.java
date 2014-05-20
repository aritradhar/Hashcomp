//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar																* *
//MT12004																			* *
//M.TECH CSE																		* * 
//INFORMATION SECURITY																* *
//IIIT-Delhi																		* *
//																					* *
//*********************************************************************************** *

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class HashComp 
{

	public static void main(String[] args) throws Exception
	{
		System.out.print("MAC 1 :");
		String hash1=new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.print("MAC 2 :");
		String hash2=new BufferedReader(new InputStreamReader(System.in)).readLine();
		
		int i,j;
		String H1=new String();
		String H2=new String();
		
		for(j=0;j<hash1.length();j++)
		{
			Character hs1=hash1.charAt(j);
			Integer hash1_dec=Integer.parseInt(hs1.toString(),16);
			String h1=Integer.toBinaryString(hash1_dec);
					
			if(h1.length()<4)
			{
				String temp = new String();
				for(i=0;i<4-h1.length();i++)
					temp=temp.concat("0");
				h1=temp.concat(h1);
			}
			H1=H1.concat(h1);
		}	
		
		for(j=0;j<hash2.length();j++)
		{
			Character hs2=hash2.charAt(j);
			Integer hash2_dec=Integer.parseInt(hs2.toString(),16);
			String h2=Integer.toBinaryString(hash2_dec);
			
			if(h2.length()<4)
			{
				String temp2 = new String();
				for(i=0;i<4-h2.length();i++)
					temp2=temp2.concat("0");
				h2=temp2.concat(h2);
			}
			
			H2=H2.concat(h2);
		}
		
		System.out.println("Binary (MAC1) (length "+H1.length()+" bit):"+H1);
		System.out.println("Binary (MAC2) (length "+H2.length()+" bit):"+H2);
		
		int count=0;
		if(H1.length()==H2.length())
		{
			for(i=0;i<H1.length();i++)
			{
				if(H1.charAt(i)==H2.charAt(i))
					count++;
			}
		//System.out.print(count);
		float percnt=(float)count/H1.length();
		percnt=percnt*100;
		System.out.println("Similarity :"+percnt+"%");
		}
		else
			System.out.print("Not of same length");
	}

}
