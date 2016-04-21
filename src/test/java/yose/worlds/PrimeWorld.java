package yose.worlds;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;

import yose.HTTPHelpers;
import yose.YoseDriver;

public class PrimeWorld {

	YoseDriver yose = new YoseDriver(9999);

	HttpRequest request = new HttpRequest(9999);
	HttpResponse response;

	@Before
	public void startGame() throws Exception {
		yose.start();
	}

	@After
	public void stopGame() throws Exception {
		yose.stop();
	}

	@Test
	public void testDecompostionOfPowerOfTwoOf16() throws IOException {
		response = request.get("/primeFactors?number=16");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":16,\"decomposition\":[2,2,2,2]}");
	}

	@Test
	public void testDecompostionOfPowerOfTwoOf1024() throws IOException {
		response = request.get("/primeFactors?number=1024");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":1024,\"decomposition\":[2,2,2,2,2,2,2,2,2,2]}");
	}

	@Test
	public void testNotANumber() throws Exception {
		response = request.get("/primeFactors?number=foo");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":\"foo\",\"error\":\"not a number\"}");
	}
}
