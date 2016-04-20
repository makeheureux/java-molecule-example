package yose;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;

import static com.vtence.molecule.http.MimeTypes.JSON;

import java.util.Arrays;

public class Prime {
    private final Gson gson;

    public Prime(Gson gson) {
        this.gson = gson;
    }

    public void numbers(Request request, Response response) throws Exception {
    	int n = Integer.parseInt(request.parameter("number"));
    	response.contentType(JSON).body(gson.toJson(new PrimeJson(n,getPower(n))));
    }

    public int getPower(int n) {
    	int x = n;
    	int i = 0;
    	while (x > 1) {
    		i++;
    		x = x/2;
    	}
		return i;
    }
    
    public static class PrimeJson {
        public int number;
        public final int[] decomposition; 
        
        PrimeJson(int n,int i) {
        	number = n;
        	int[] a = new int[i];
        	Arrays.fill(a, 2);
        	
        	decomposition = a;
        }
    }
}
