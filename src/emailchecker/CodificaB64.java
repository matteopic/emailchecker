package emailchecker;

import java.io.*;
import sun.misc.*;
public class CodificaB64{

	public static void main (String[]args){
		try{
			FileInputStream fis = new FileInputStream(args[0]);
			int length = fis.available();
			byte[]buffer = new byte[length];
			fis.read(buffer);
			BASE64Encoder encoder = new BASE64Encoder();
			String codificato = encoder.encode(buffer);
			fis.close();

			FileWriter fw = new FileWriter(args[0]);
			fw.write(codificato);
			fw.flush();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}