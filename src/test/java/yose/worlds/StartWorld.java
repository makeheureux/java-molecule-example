package yose.worlds;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;

import yose.HTMLDocument;
import yose.HTTPHelpers;
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
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"alive\":true}");
	}

	@Test
	public void testPowerOfTwo() throws IOException {
		response = request.get("/primeFactors?number=16");
		HTTPHelpers.assertValidJSONResponse(response);
		assertThat(response).hasBodyText("{\"number\":16,\"decomposition\":[2,2,2,2]}");
	}

	@Test
	public void shareChallengeCheckLinkFound() throws Exception {
		response = request.get("/");
		assertThat(response).isOK();
		Document doc = HTMLDocument.from(response);
		Element link = doc.getElementById("repository-link");
		assertNotNull(link);
		assertEquals("A", link.getTagName());
		String href = link.getAttribute("href");
		assertNotEquals(href, "");
	}

	@Test
	public void shareChallengeCheckLinkValid() throws Exception {
		response = request.get("/");
		Document doc = HTMLDocument.from(response);
		Element link = doc.getElementById("repository-link");
		String href = link.getAttribute("href");
		HttpResponse linkResponse = HTTPHelpers.getFromURL(href);
		HTTPHelpers.assertValidHTMLResponse(linkResponse);
	}

	@Test
	public void shareChallengeCheckLinkTargetValid() throws Exception {
		response = request.get("/");
		Document doc = HTMLDocument.from(response);
		Element link = doc.getElementById("repository-link");
		String href = link.getAttribute("href");
		HttpResponse linkResponse = HTTPHelpers.getFromURL(href);
		Document linkDoc = HTMLDocument.from(linkResponse);
		Element readme = linkDoc.getElementById("readme");
		assertNotNull(readme);
		assertTrue(readme.getTextContent().contains("YoseTheGame"));
	}
}
