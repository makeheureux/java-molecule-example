package yose;

import static com.vtence.molecule.http.MimeTypes.JSON;

import java.util.List;

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
			try {
				response.contentType(JSON).body(gson.toJson(new PrimeJson(n, PrimeFactors.decompose(n))));
			} catch (PrimeFactorException e) {
				response.contentType(JSON).body(gson.toJson(new PrimeNumberErrorJson(n, e.getMessage())));
			}
		} catch (NumberFormatException e) {
			response.contentType(JSON).body(gson.toJson(new NotANumberErrorJson(parameter, "not a number")));
		}
	}

	public static class PrimeJson {
		public final int number;
		public final List<Integer> decomposition;

		PrimeJson(int n, List<Integer> a) {
			number = n;
			decomposition = a;
		}
	}

	public static class NotANumberErrorJson {
		public final String number;
		public final String error;

		NotANumberErrorJson(String number, String error) {
			this.number = number;
			this.error = error;
		}
	}

	public static class PrimeNumberErrorJson {
		public final int number;
		public final String error;

		PrimeNumberErrorJson(int number, String error) {
			this.number = number;
			this.error = error;
		}
	}
}
