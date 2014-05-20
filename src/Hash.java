import java.security.MessageDigest;
import java.util.*; 

public class Hash {

    public static String calculateHash(MessageDigest algorithm,
            String message) throws Exception{

        algorithm.update(message.getBytes());

        byte[] hash = algorithm.digest();
		return byteArray2Hex(hash);
    }

    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) 
        {
            formatter.format("%02x", b);
        }
		return formatter.toString();
    }
	
    public static void main(String[] args) throws Exception 
    {
    	long startTime = System.currentTimeMillis();
        //String message = "Adarsh Agarwal";
		long temp = (long)Math.pow(2,23);
		//System.out.println(temp);
	
		int count=0;
		
		Character hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        
		Integer found=0;
		HashMap hashmap=new HashMap();
		
		while(found!=1)
		{
			//count++;
			//System.out.println(count);
			
			//String bits = "";
			StringBuffer bits_temp = new StringBuffer();
			
			
			for(int i=0; i<16; i++) //this is message size, I gave it as 64 bytes, this is not 40 bytes
			{
				float a=((float)Math.random()*100)%16;  //make it Hex and it will give complete randomness, 100 is just for amplification
				int p=(int)a;
				//.out.print(p);
				//int x = 0;
				//if(r.nextBoolean()) x=1;
					//bits += x;
				bits_temp.append(hexDigit[p].toString());
			}
			String bits=bits_temp.toString();
			
			//System.out.println(bits);
			
			
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        
			String ans=calculateHash(sha1, bits);
			String sha40 = ans.substring(0,10); //(10*4)=40 bits 
		
			//System.out.println("SHA:  "+sha40);
			
			
			//from here I am doing major changes to increase the speed of this code
			//now its is super fast :)
			
			
			long dd = Long.parseLong(sha40, 16);
			
			if(hashmap.containsKey(dd))
	         {
	        	
	        	 System.out.println("collision found");
	        	 
	        	 System.out.println("M1: " + hashmap.get(dd));
	        	 System.out.println("M2: " + bits);
	        	 
	        	 System.out.println("COLL: " + sha40);
	        	 found=1;    //collision found
	        	 break;	     //collision found so out of the loop   	 
	         }
	         else
	         {
	        	 //System.out.println("not");
	        	 hashmap.put(dd, bits);
	         }
			
			/*Iterator iterator = hashmap.keySet().iterator();
			 
			while (iterator.hasNext()) {
			   String key = iterator.next().toString();
			   String value = hashmap.get(key).toString();
			 
			   System.out.println("key: "+key + " value: " + value);
			   */
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime+" ms");
	}
}

	            
			//no need of this part
			/*HashMap hm = new HashMap(); 
			boolean blnExists = hm.containsKey(sha40);
			if(blnExists){
				//hm.put(sha40,bits);
				System.out.println("Match Found for Hash:" + sha40 + "   For Msg2:"+ bits +"\n At iteration :"+loop);
			}
			else
				hm.put(sha40,bits);
			//System.out.println("Match Found for Hash:" + sha40 + "   For Msg2:"+ bits +"\n At iteration :"+loop);*/	

