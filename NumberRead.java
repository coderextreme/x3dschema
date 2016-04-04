import org.json.JSONObject;
import org.json.JSONTokener;
import java.math.BigInteger;

import java.io.*;

public class NumberRead {
    public static void main(String args[]) throws Exception {
	JSONTokener tokener = new JSONTokener(new FileInputStream("number.json"));
	JSONObject jsonSubject = new JSONObject(tokener);
	Object object = jsonSubject.getJSONObject("MovieTexture").optBigInteger("@startTime", new BigInteger("0"));
	System.out.println(object+" is a "+object.getClass().getSimpleName());
    }
}
