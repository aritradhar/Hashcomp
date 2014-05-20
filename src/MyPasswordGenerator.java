//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar																* *
//MT12004																			* *
//M.TECH CSE																		* * 
//INFORMATION SECURITY																* *
//IIIT-Delhi																		* *
//																					* *
//*********************************************************************************** *

import java.awt.RenderingHints.Key;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import sun.misc.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;


public class MyPasswordGenerator 
{
	static char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	public static String filename=new String();
	
    private static byte[] keyValue;
    //new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't','S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
    
    public static void setKeyValue(String P)
    {
    	keyValue=P.getBytes();
    }
    
    
	public static int choice;
	
	public static String encrypt(String Data)
	{
		String encryptedValue=new String();
		try
		{
			SecretKeySpec key = generateKey();
			Cipher c = Cipher.getInstance("AES", "SunJCE");
			c.init(Cipher.ENCRYPT_MODE, (java.security.Key) key);
			byte[] encVal = c.doFinal(Data.getBytes());
			String enc_temp=encVal.toString();
			encryptedValue = new BASE64Encoder().encode(encVal);
			//encryptedValue=encVal.toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return encryptedValue;
    }
	
	 public static String decrypt(String encryptedData)
	 {
		 String decryptedValue=new String();
		 try
		 {
	        SecretKeySpec key = generateKey();
	        Cipher c = Cipher.getInstance("AES");
	        c.init(Cipher.DECRYPT_MODE, key);
	        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
	        byte[] decValue = c.doFinal(decordedValue);
	        decryptedValue = new String(decValue);	    
	    }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 System.exit(1);
		 }
		 return decryptedValue;
	 }
	 
	
	 private static SecretKeySpec generateKey() throws Exception {
	        SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
	        return key;
	 }

	
	public static int setChioce()
	{
		try
		{
			System.out.println("Enter Choice : ");
			String choice_s=new BufferedReader(new InputStreamReader(System.in)).readLine();
			choice=Integer.parseInt(choice_s);
		}
		catch(Exception e)
		{
			System.out.println("Wrong format of choice");
		}
		return choice;
	}
	
	
	public static void main(String[] args) throws Exception
	{	
		System.out.println("-------------------password Manager by Aritra Dhar---------------");
		System.out.println("1.sign up");
		System.out.println("2.sign in");
		
		FileWriter fwrite=null;
		//String choice_s=new BufferedReader(new InputStreamReader(System.in)).readLine();
		
		//int choice=Integer.parseInt(choice_s);
		MyPasswordGenerator.setChioce();
		
		switch(choice)
		{

		case 1:
			//hash of file name (id)
			System.out.println("Enter login id");
			String id=new BufferedReader(new InputStreamReader(System.in)).readLine();
			
			
			MessageDigest idhash = MessageDigest.getInstance("SHA1");
			idhash.update(id.toString().getBytes()); 
			byte[] id_hash_byte=idhash.digest();
	      	String id_hash=bytesToHex(id_hash_byte);
			
			File f=new File(id_hash+".txt");
			
			if(f.exists())
			{
				System.out.println("Already registered!!! System Exiting");
				System.exit(1);
			}
			
			int flag=0,count=0;
			String pass=new String();
			System.out.println("Enter password");
			pass=new BufferedReader(new InputStreamReader(System.in)).readLine();
			while(true)
			{
				if(count>0 && count<4)
				{
					System.out.println("Wrong password "+(3-count)+" attempt(s) left");
				}
				if(count==3)
				{
					//count++;
					System.out.println("3 attempt finished !! System Exiting");
					System.exit(1);
				}
				count++;
				System.out.println("Enter again");
				String pass1=new BufferedReader(new InputStreamReader(System.in)).readLine();
				
				if(pass1.equals(pass))
				{
					flag=1;
					break;
				}
				
			}
			MessageDigest passmd = MessageDigest.getInstance("MD5");
			passmd.update(pass.toString().getBytes()); 
	      	byte[] pass_o = passmd.digest();
	      	String pass_out=bytesToHex(pass_o);
	      	
	      	
	      	MessageDigest passmdt = MessageDigest.getInstance("MD5");
			passmdt.update(pass.toString().getBytes()); 
	      	byte[] key_temp=passmdt.digest();
	      	String pass_temp_string=bytesToHex(key_temp);
	      	String AES_KEY=pass_temp_string.substring(0,16);
	      	
	      	f.createNewFile();
	      	fwrite=new FileWriter(f);
	      	//id and passout
	      	
	      	//AES--------------------------------------------------------------------------------------
	      	MyPasswordGenerator.setKeyValue(AES_KEY);  //must be of 16 bytes
	      	fwrite.append(MyPasswordGenerator.encrypt(id));
	      	//System.out.println("Deceypy : "+ MyPasswordGenerator.decrypt("sByKyZXx7QwfY3n/nw9KPg==")); // encryption of aritra1
			//fwrite.append(id);
	      	fwrite.append("\n"+pass_out);
	      	System.out.println(pass);
	      	
	      	
	      	fwrite.close();
	      	System.out.println("------------Account for "+id+" is created------------------");
	      	break;
		
		case 2:
			//sign in
			
			System.out.println("Enter Login id:");
			String id_match=new BufferedReader(new InputStreamReader(System.in)).readLine();
			
			MessageDigest idmatchhash = MessageDigest.getInstance("SHA1");
			idmatchhash.update(id_match.toString().getBytes()); 
			byte[] id_match_hash_byte=idmatchhash.digest();
	      	String id_match_hash=bytesToHex(id_match_hash_byte);
	      	
	      	filename=id_match_hash+".txt";
			
			//System.out.println(id_match_hash);
			File f1=new File(id_match_hash+".txt");
			
			//C8FF36EA3ECD3EF4FD0EAA55A1D7959846F45A52
			//System.out.println(f1.exists());   debug data
			if(!f1.exists())
			{
				System.out.println("id not found!!! System Exiting");
				System.exit(1);
			}
			else
			{
				System.out.println("Enter Password");
				String passw=new BufferedReader(new InputStreamReader(System.in)).readLine();
				FileInputStream fin=new FileInputStream(f1);
				BufferedReader b_in=new BufferedReader(new InputStreamReader(new DataInputStream(fin)));
				String read;
				int line=0;
				
				while((read=b_in.readLine())!=null)
				{
					line++;
					if(line==2)
					{
						//System.out.println("read : "+read);    //debug
						
						MessageDigest passmd1 = MessageDigest.getInstance("MD5");
						passmd1.update(passw.toString().getBytes()); 
				      	byte[] pass_o1 = passmd1.digest();
				      	String pass_out1=bytesToHex(pass_o1);
				      	
				      	
				      	//System.out.println("passout : "+pass_out1);     //debug
				      	
				      	
				      	if(pass_out1.equals(read))
				      	{
				      		System.out.println("---------------Login success. Welcome "+id_match+"----------------");
				      	}
				      	else
				      	{
				      		System.out.println("no match... System Exiting.");
				      		System.exit(1);
				      	}
					}
				}
				
			}
		break;
		default:
			System.out.println("Wrong Choice... System Exiting");
			System.exit(1);
		
		}
		
		System.out.println("1. Enter new pasword :");
		System.out.println("2. View Password");
		System.out.println("3. Edit password");
		//System.out.println("Enter choice :");
		
		
		
		MyPasswordGenerator.setChioce();
		
		switch(choice)
		{
		case 1: 
			System.out.print("Enter password length (preffered more than 8) : ");
			String input=new BufferedReader(new InputStreamReader(System.in)).readLine();
			Integer pass_len=Integer.parseInt(input);
			System.out.print("Enter site name (use only www.sitename.domain format) : ");
			String site_name=new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.out.print("Enter your name : ");
			String name=new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.out.print("Enter your age : ");
			String age=new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.out.print("Enter any random word : ");
			String word=new BufferedReader(new InputStreamReader(System.in)).readLine();
		
			StringBuffer read=new StringBuffer();
			read.append(site_name);
			read.append(name);
			read.append(age);
			read.append(word);
			read.append(pass_len);
		
			String spec_symb="!@#$%^&*()_+={}[]\\|:\";'<>,.?/";
			
			MessageDigest md_stpoint = MessageDigest.getInstance("MD5");
			md_stpoint.update(age.toString().getBytes()); 
			byte[] age_out = md_stpoint.digest();
			String out=bytesToHex(age_out);
			float a1_f=(float) Math.random()*100;
			int a1=((int)a1_f)%128;
			
			float a2_f=(float) Math.random()*100;
			int a2=((int)a2_f)%128;
			
			float a3_f=(float) Math.random()*100;
			int a3=((int)a3_f)%128;
			
			float a4_f=(float) Math.random()*100;
			int a4=((int)a4_f)%128;
			
			float a5_f=(float) Math.random()*100;
			int a5=((int)a5_f)%128;
			
			float a6_f=(float) Math.random()*100;
			int a6=((int)a6_f)%128;
		
			Character out_c=out.charAt(6);
      		int modulus=Integer.parseInt(out_c.toString(),16);
      		modulus=modulus%19;
      		int start_point=(a1+a2+a3+a4+a5+a6)%modulus;
      		
      		//System.out.println(a1+a2+a3+a4+a5+a6);
		
      		//System.out.println(start_point);
      		//System.out.println(read);
		
      		MessageDigest md = MessageDigest.getInstance("SHA1");
      		md.update(read.toString().getBytes()); 
      		byte[] output = md.digest();
      	
      		String password=bytesToHex(output).substring(start_point, pass_len+start_point);
      		StringBuffer pass=new StringBuffer(password);
      	
      		for(int i=0;i<pass_len;i++)
      		{
      			Character chk=password.charAt(i);
      			if(((int)chk<=90) && ((int)chk>=65))
      			{
      				//password.replace(password.charAt(i), (char) ((int)password.charAt(i)+32));
      				Character p=(char) ((int)password.charAt(i)+32);
      				pass.append(p.toString());
      				//pass.insert(i, pass.toString());
      				//System.out.print("in");
      				pass.deleteCharAt(i);
   
      				break;
      			}
      		}
      		
      		//insert special symbol
      		
      		float spec_symbol_pos_f=((float)(Math.random()*100));
      		int spec_symbol_pos=((int)spec_symbol_pos_f)%pass_len;
      		
      		float spec_symbol_index_f=((float)Math.random()*100);
      		int spec_symbol_index=((int)spec_symbol_index_f)%spec_symb.length();
      		
      		//System.out.println(spec_symbol_pos);
      		//System.out.println(spec_symbol_index);
      		
      		
      		pass.deleteCharAt(spec_symbol_pos);
      		pass.insert((int)spec_symbol_pos, spec_symb.charAt((int)spec_symbol_index));
      		
      		System.out.println("Site :"+site_name);
      		System.out.print("Password is : "+pass);
      		
      		
      		//fwrite.append(site_name+" "+pass);
      		break;
      		
		//case 2:
		default:
			System.out.println("Wrong choice!! System exiting");
			System.exit(1);
			
			
		}
	}
	
	public static String bytesToHex(byte[] b) 
	 {
	      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	         buf.append(hexDigit[b[j] & 0x0f]);
	      }
	      return buf.toString();
	   }

}
