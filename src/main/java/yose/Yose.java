package yose;

import java.io.IOException;
import java.net.URI;

import com.google.gson.Gson;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.routing.DynamicRoutes;

public class Yose {

	private final WebServer server;

	public Yose(int port) {
		this.server = WebServer.create(port);
	}

	public void start() throws IOException {
		final Gson gson = new Gson();

		server.start(new DynamicRoutes() {
			{
				get("/").to((request, response) -> response.body(
						"Hello Yose <a id='repository-link' href='https://github.com/makeheureux/java-molecule-example'>source code</a>"));
				get("/ping").to(new Ping(gson)::pong);
				get("/primeFactors").to(new PrimeFactorsRequestHandler(gson)::numbers);
			}
		});
	}

	public URI uri() {
		return server.uri();
	}

	public void stop() throws IOException {
		server.stop();
	}

	private static final int PORT = 0;

	private static int port(String[] args) {
		return args.length > 0 ? Integer.parseInt(args[PORT]) : 8080;
	}

	public static void main(String[] args) throws IOException {
		Yose yose = new Yose(port(args));
		yose.start();
		System.out.print("To play the game visit " + yose.uri());
	}
}