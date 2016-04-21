package yose.worlds;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;

import yose.HTMLDocument;
import yose.YoseDriver;

public class StartWorld {

	private static final Pattern HTML_CONTENT_TYPE_PATTERN = Pattern.compile();

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

	@Test
	public void shareChallengeLink() throws Exception {
		response = request.get("/");
		assertThat(response).isOK();
		Document doc = HTMLDocument.from(response);
		Element link = doc.getElementById("repository-link");
		assertNotNull(link);
		assertEquals("A", link.getTagName());
		String href = link.getAttribute("href");
		assertNotEquals(href, "");
		HttpResponse linkResponse = getFromURL(href);
		assertValidHTMLResponse(linkResponse);
		Document linkDoc = HTMLDocument.from(linkResponse);
		Element readme = linkDoc.getElementById("readme");
		assertNotNull(readme);
		assertTrue(readme.getTextContent().contains("YoseTheGame"));
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
		HttpResponse linkResponse = getFromURL(href);
		assertValidHTMLResponse(linkResponse);
	}

	@Test
	public void shareChallengeCheckLinkTargetValid() throws Exception {
		response = request.get("/");
		Document doc = HTMLDocument.from(response);
		Element link = doc.getElementById("repository-link");
		String href = link.getAttribute("href");
		HttpResponse linkResponse = getFromURL(href);
		Document linkDoc = HTMLDocument.from(linkResponse);
		Element readme = linkDoc.getElementById("readme");
		assertNotNull(readme);
		assertTrue(readme.getTextContent().contains("YoseTheGame"));
	}

	private static HttpResponse getFromURL(String url) throws IOException {
		URL split = new URL(url);
		String host = split.getHost();
		int port = split.getPort();
		if (port < 0) {
			String protocol = split.getProtocol();
			port = "https".equals(protocol) ? 443 : 80;
		}
		String file = split.getFile();
		HttpRequest request = new HttpRequest(host, port);
		request.secure(true);
		request.followRedirects(true);
		return request.get(file);
	}

	private static void assertValidHTMLResponse(HttpResponse response) {
		assertThat(response).isOK();
		String contentType = response.contentType();
		assertTrue(contentType.matches("^text/html[;$].*"));
	}
}
