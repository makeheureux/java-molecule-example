package yose;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;

import static com.vtence.molecule.http.MimeTypes.JSON;

public class Ping {
    private final Gson gson;

    public Ping(Gson gson) {
        this.gson = gson;
    }

    public void pong(Request request, Response response) throws Exception {
        request.attribute("number");
    	response.contentType(JSON).body(gson.toJson(new Pong()));
    }

    public static class Pong {
        public final boolean alive = true;
    }
}
