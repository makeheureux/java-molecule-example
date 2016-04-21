package yose;

import static com.vtence.molecule.http.MimeTypes.JSON;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;

public class PrimeFactorsRequestHandler {
	private final Gson gson;

	public PrimeFactorsRequestHandler(Gson gson) {
		this.gson = gson;
	}

	public void numbers(Request request, Response response) throws Exception {
		String parameter = request.parameter("number");
		try {
			int n = Integer.parseInt(parameter);
			response.contentType(JSON).body(gson.toJson(new PrimeJson(n, PrimeFactors.decompose(n))));
		} catch (NumberFormatException e) {
			response.contentType(JSON).body(gson.toJson(new ErrorJson(parameter, "not a number")));
		}
	}

	public static class PrimeJson {
		public final int number;
		public final int[] decomposition;

		PrimeJson(int n, int[] a) {
			number = n;
			decomposition = a;
		}
	}

	public static class ErrorJson {
		public final String number;
		public final String error;

		ErrorJson(String number, String error) {
			this.number = number;
			this.error = error;
		}
	}
}
