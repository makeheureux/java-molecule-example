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

	@Test
	public void testDecompositionOf1() throws Exception {
		response = request.get("/primeFactors?number=1");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":1,\"decomposition\":[]}");
	}

	@Test
	public void testDecompositionOf10() throws Exception {
		response = request.get("/primeFactors?number=10");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":10,\"decomposition\":[2,5]}");
	}

	@Test
	public void testDecompositionOf12() throws Exception {
		response = request.get("/primeFactors?number=12");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":12,\"decomposition\":[2,2,3]}");
	}

	@Test
	public void testDecompositionOf21() throws Exception {
		response = request.get("/primeFactors?number=21");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":21,\"decomposition\":[3,7]}");
	}

	@Test
	public void testDecompositionOf300() throws Exception {
		response = request.get("/primeFactors?number=300");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":300,\"decomposition\":[2,2,3,5,5]}");
	}

	@Test
	public void testDecompositionOf997() throws Exception {
		response = request.get("/primeFactors?number=997");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":997,\"decomposition\":[997]}");
	}

	@Test
	public void testBigNumber() throws Exception {
		response = request.get("/primeFactors?number=1000001");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":1000001,\"error\":\"too big number (>1e6)\"}");
	}
}
