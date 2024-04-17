package emailchecker;
import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.*;
public class Crypto{

	public Crypto(){
		try{
			cipher = Cipher.getInstance("DESede", "SunJCE");
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");

			//byte[] passwd = new String(".:,;@òçù§+*]è[ì/*-%$!|(/").getBytes();
			byte[]passwd= new byte[]{
				46, 58, 44, 59, 64, -14,
				-25, -7, -89, 43, 42, 93,
				-24, 91, -20, 47, 42, 45,
				37, 36, 33, 124, 40, 47
			};

			DESedeKeySpec keySpec = new DESedeKeySpec(passwd);
			key = skf.generateSecret(keySpec);
		}catch(Exception e){}
	}

	public byte[] encrypt(byte[]data)throws IllegalBlockSizeException, BadPaddingException{
		try{
			cipher.init(Cipher.ENCRYPT_MODE, key);
		}catch(InvalidKeyException ike){}
		return cipher.doFinal(data);

	}

	public byte[] decrypt(byte[]data)throws IllegalBlockSizeException, BadPaddingException{
		try{
			cipher.init(Cipher.DECRYPT_MODE, key);
		}catch(InvalidKeyException ike){}
		return cipher.doFinal(data);
	}


private Cipher cipher;
private SecretKey key;
}