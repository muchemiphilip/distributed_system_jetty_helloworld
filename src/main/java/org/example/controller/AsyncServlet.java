package org.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.example.model.Audio;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Start the asynchronous operation
        AsyncContext asyncContext = request.startAsync();

        // Submit the operation to the executor service
        executorService.submit(new AsyncOperation(asyncContext));
    }

    private static class AsyncOperation implements Runnable {

        private final AsyncContext asyncContext;

        public AsyncOperation(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }

        @Override
        public void run() {
            try {
                // Perform the asynchronous operation here
                List<Audio> audioList = new ArrayList<>(); // create a list of Audio objects
                audioList.add(new Audio("Artist 1", "Track ", "Album 1", 1, 2022, 10, 100)); 
                audioList.add(new Audio("Artist 2", "Track 2", "Album 1", 1, 2022, 10, 100)); 
                audioList.add(new Audio("Artist 3", "Track 3", "Album 1", 1, 2022, 10, 100)); 

                // Serialize the list as JSON
                ObjectMapper mapper = new ObjectMapper();
                String audioJson = mapper.writeValueAsString(audioList);

                // Write the response
                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                response.setContentType("application/json"); // set content type to JSON
                response.getWriter().write(audioJson);

                // Complete the asynchronous operation
                asyncContext.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
