package yose.worlds;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;

import yose.HTMLDocument;
import yose.YoseDriver;

public class StartWorld {

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
	public void firstWebPageChallenge() throws IOException {
		yose.home().displaysGreeting("Hello Yose");
	}

	@Test
	public void firstWebServiceChallenge() throws IOException {
		response = request.get("/ping");

		assertThat(response).isOK().hasContentType("application/json").hasBodyText("{\"alive\":true}");
	}

	@Test
	public void testPowerOfTwo() throws IOException {
		response = request.get("/primeFactors?number=16");

		assertThat(response).isOK().hasContentType("application/json")
				.hasBodyText("{\"number\":16,\"decomposition\":[2,2,2,2]}");
	}

	// @Test
	public void shareChallengeLink() throws Exception {
		response = request.get("/");
		assertThat(response).isOK();
		Document doc = HTMLDocument.from(response.bodyText());
		NodeList a = doc.getElementsByTagName("a");// ("repository-link");
		System.out.println(a.getLength());
		Element link = doc.getElementById("repository-link");
		System.out.println(link);
		assertNotNull(link);
		assertEquals(link.getTagName(), "a");
		String target = link.getTextContent();
		assertNotEquals(target, "");
	}
}
