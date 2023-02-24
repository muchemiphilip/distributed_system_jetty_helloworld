package org.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.example.model.Audio;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BlockingServlet extends HttpServlet {

    public static Map<String, Audio> audioData = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Create an async context
        AsyncContext asyncContext = request.startAsync();

        // Create a future task for the blocking task
        RunnableFuture<Void> blockingTask = new FutureTask<>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Perform the blocking task here
                // ...
                Thread.sleep(5000); // sleep for 5 seconds to simulate a blocking task

                JSONArray audioArray = new JSONArray(
                        IOUtils.toString(request.getInputStream().readAllBytes(), "UTF-8"));
                // Set the Audio object as a property of the BlockingServlet instance
                // String id = request.getParameter("id");
                // String artistName = request.getParameter("artistName");
                // String trackTitle = request.getParameter("trackTitle");
                // String albumTitle = request.getParameter("albumTitle");
                // String trackNumber = request.getParameter("trackNumber");
                // String year = request.getParameter("year");
                // String numReviews = request.getParameter("numReviews");
                // String numCopiesSold = request.getParameter("numCopiesSold");

                // Audio audio = new Audio(id,artistName, trackTitle, albumTitle, trackNumber,
                // year, numReviews, numCopiesSold);
                // synchronized(audioData) {
                // audioData.put(audio.getId(), audio);
                // }
                for (int i = 0; i < audioArray.length(); i++) {
                    JSONObject audioObject = audioArray.getJSONObject(i);
                    String artistName = audioObject.getString("artistName");
                    String trackTitle = audioObject.getString("trackTitle");
                    String albumTitle = audioObject.getString("albumTitle");
                    String trackNumber = Integer.toString(audioObject.getInt("trackNumber"));
                    String year = Integer.toString(audioObject.getInt("year"));
                    String numReviews = Integer.toString(audioObject.getInt("numReviews"));
                    String numCopiesSold = Integer.toString(audioObject.getInt("numCopiesSold"));

                    Audio audio = new Audio(artistName, trackTitle, albumTitle, trackNumber,
                            year, numReviews, numCopiesSold);
                    audioData.put(audio.getId(), audio);
                    System.out.println("Audio id is :: " + audio.getId());
                }
                response.setStatus(HttpStatus.OK_200);
                response.setContentType("application/json");
                String jsonResponse = "{\"statusMessage\":\"Track posted successfully\"}";
                response.getOutputStream().write(jsonResponse.getBytes());
                return null;
            }
        });

        // Submit the task to the executor service
        asyncContext.start(blockingTask);

        try {
            // Wait for the task to complete
            blockingTask.get();
        } catch (InterruptedException | ExecutionException e) {
            // Handle the exception
            throw new ServletException(e);
        }

        System.out.println(audioData.toString());
        // Complete the async context
        asyncContext.complete();
    }
}
