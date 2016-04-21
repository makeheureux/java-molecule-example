package yose;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;

public class HTTPHelpers {

	private HTTPHelpers() {
	}

	public static HttpResponse getFromURL(String url) throws IOException {
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

	public static void assertValidHTMLResponse(HttpResponse response) {
		assertThat(response).isOK();
		String contentType = response.contentType();
		assertTrue(contentType.matches("^text/html(;.*)?$"));
	}

	public static void assertValidJSONResponse(HttpResponse response) {
		assertThat(response).isOK();
		String contentType = response.contentType();
		assertTrue(contentType.matches("^application/json(;.*)?$"));
	}
}
