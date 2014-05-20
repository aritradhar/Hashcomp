//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar																* *
//MT12004																			* *
//M.TECH CSE																		* * 
//INFORMATION SECURITY																* *
//IIIT-Delhi																		* *
//																					* *
//*********************************************************************************** *
//*************************************************************************************
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class SHA1 
{

	 public static void main(String[] a) 
	 {
		 long startTime = System.currentTimeMillis();
	      try 
	      {
	    	 //FileWriter out_bunch=new FileWriter(new File("out_msg.txt"));
	    	  
	         MessageDigest md = MessageDigest.getInstance("SHA1");  //scheme
	         System.out.println("Message digest object info: ");
	         System.out.println("   Algorithm = "+md.getAlgorithm());
	         System.out.println("   Provider = "+md.getProvider());
	         System.out.println("   toString = "+md.toString());

	         //String input = "";
	         int bits=160;
	         int radix=16;
	         
	         bits=bits/4;  //birthday attack
	         FileWriter out_hash=new FileWriter(new File("hash_pair_"+bits+"byte_res1"+".txt"));
	         double message_space=Math.pow(radix, bits);
	        // System.out.print(message_space);
	         int []input=new int[64];
	         int col_count=0;
	         
	         //randamized 
	         for(int i=0;i<bits;i++)
	         {
	        	 input[i]=(int)( (Math.random()*16) % 16 );
	         }
	         
	         Long count=new Long(0);
	         Character hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                     '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	         
	         String read = null;
	         
	         HashMap hashmap=new HashMap();
	         int flag=0;
	         
	         //while(flag!=1)
	         while(col_count!=10)
	         {
	        	 	        	   
	        	 StringBuffer temp= new StringBuffer();
	        	// System.out.println("a");
	        	 for(int j=bits-1;j>=0;j--)
	             {
	                 //System.out.print(input[j]);
	            	 //out_bunch.append(""+hexDigit[input[j]]);
	        		 temp.append(hexDigit[input[j]].toString());
	             }
	        	 //System.out.print(temp);
	        	 read=temp.toString();
	             
	             input[0]++;
	             for(int i=0;i<bits;i++)
	             {
	                 if((input[i]>1) && (input[i]%(radix)==0))
	                 {
	                     input[i]=0;
	                     input[i+1]++;
	                 }
	             }
	             count++;
	         
	         		
	 		 md.update(read.getBytes()); 
	      	 byte[] output = md.digest();
	      	 
	         //input = "aritra";
	         md.update(read.getBytes());
	      	 output = md.digest();
	         //System.out.println();
	         //System.out.println("SHA1(\""+read+"\") =");
	         //System.out.println("   "+bytesToHex(output));
	         String hash40=bytesToHex(output).substring(0, 10);
	         //System.out.println("Hash SHA1(40) = "+hash40);
	         
	         long dd = Long.parseLong(hash40, 16);
	         
	         long hashInt = output[0] + (output[1]<<8) + (output[2]<< 16)+(output[3]<< 24)+(output[4]<< 32);
	         
	     
	         
	         if(hashmap.containsKey(dd))
	         {
	        	 flag=1;
	        	 System.out.println((col_count+1)+" collision found");
	        	 
	        	 System.out.println("R1: " + hashmap.get(dd));
	        	 System.out.println("R2: " + read);
	        	 out_hash.append("R1: " + hashmap.get(dd) + "\n");
	        	 out_hash.append("R2: " + read + "\n");
	        	 
	        	 System.out.println("COLL: " + hash40);
	        	 out_hash.append("COLL: " + hash40 + "\n");
	        	 System.out.println("Count: " + count+"\n-------------------");
	        	 out_hash.append("------------------------------\n");
	        	 
	        	 count=(long) 0;
	        	 
	        	 col_count++;
	        	 hashmap.clear();
	        	 
	        	 //reinitialize
	        	  for(int i=0;i<bits;i++)
	 	         {
	 	        	 input[i]=(int)( (Math.random()*83) % 16 );
	 	         }
	        	 
	        	 
	        	 
	         }
	         else
	         {
	        	 hashmap.put(dd, read);
	         }
	            
	         }
	         out_hash.close();
	      	 
	         
	      } 
	      catch (Exception e) 
	      {
	         System.out.println("Exception: "+e);
	      }
	      long stopTime = System.currentTimeMillis();
	      long elapsedTime = stopTime - startTime;
	      System.out.println(elapsedTime+" ms");
	   }
	 
	   public static String bytesToHex(byte[] b) {
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
