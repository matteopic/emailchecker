package emailchecker;

import java.io.*;
import sun.misc.*;
public class DecodificaB64{

	public static void main (String[]args){
		try{
			FileReader fr = new FileReader(args[0]);
			BufferedReader br = new BufferedReader(fr);

			String line;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			fr.close();

			BASE64Decoder decoder = new BASE64Decoder();
			byte[]bytes = decoder.decodeBuffer(sb.toString());


			FileOutputStream fos = new FileOutputStream(args[0]);
			fos.write(bytes);
			fos.flush();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}