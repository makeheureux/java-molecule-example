package yose;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;

import static com.vtence.molecule.http.MimeTypes.JSON;

import java.util.Arrays;

public class PrimeFactorsRequestHandler {
    private final Gson gson;

    public PrimeFactorsRequestHandler(Gson gson) {
        this.gson = gson;
    }

    public void numbers(Request request, Response response) throws Exception {
    	int n = Integer.parseInt(request.parameter("number"));
    	response.contentType(JSON).body(gson.toJson(new PrimeJson(n,PrimeFactors.factorOfTwo(n))));
    }

  
    public static class PrimeJson {
        public final int number;
        public final int[] decomposition; 
        
        PrimeJson(int n,int[] a) {
        	number = n;
        	decomposition = a;
        }
    }
}
