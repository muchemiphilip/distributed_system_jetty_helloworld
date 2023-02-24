package org.example.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.example.model.Audio;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ResourceServlet", value = "ResourceServlet", asyncSupported = true)
public class ResourceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public final Map<String, Audio> audioData = BlockingServlet.audioData;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Inside the doGet() method of ResourceServlet.java

        // Create an instance of AsyncServlet
        AsyncServlet asyncServlet = new AsyncServlet();

        // Forward the request to AsyncServlet
        asyncServlet.service(request, response);

        // Retrieve the id parameter from the request URL
        String id = request.getParameter("id");

        if (id != null) {
            // Retrieve the corresponding Audio object from the audioData map
            Audio audio = audioData.get(id);

            // Set the response content type to JSON
            response.setContentType("application/json");
            JSONObject audioObject = new JSONObject(audio);
            response.getWriter().write(audioObject.toString());
        } else {
            JSONArray jsonArray = new JSONArray();
            Collection<Audio> audios = audioData.values();
            for (Audio audio : audios) {

                JSONObject audioObject = new JSONObject();
                audioObject.put("id", audio.getId());
                audioObject.put("artistName", audio.getArtistName());
                audioObject.put("trackTitle", audio.getTrackTitle());
                audioObject.put("albumTitle", audio.getAlbumTitle());
                audioObject.put("trackNumber", audio.getTrackNumber());
                audioObject.put("year", audio.getYear());
                audioObject.put("numReviews", audio.getNumReviews());
                audioObject.put("numCopiesSold", audio.getNumCopiesSold());

               // Add the JSONObject to the JSONArray
                jsonArray.put(audioObject);            }
               response.setContentType("application/json");
               response.getWriter().write(jsonArray.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BlockingServlet blockingServlet = new BlockingServlet();
        blockingServlet.doPost(request, response);

    }

    @Override
    public void destroy() {
        // Shutdown the executor when the servlet is destroyed
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
