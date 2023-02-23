package org.example.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.model.Audio;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ResourceServlet", value = "ResourceServlet")
public class ResourceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final AtomicInteger copiesSold = new AtomicInteger(0);
    private static final Object lock = new Object();

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Use the AsyncServlet to handle the GET request
        AsyncServlet asyncServlet = new AsyncServlet();
        asyncServlet.doGet(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        // Start asynchronous processing
        final AsyncContext asyncContext = request.startAsync(request, response);
    
        // Use the executor to handle the background task of processing the request
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // Use the BlockingServlet to handle the POST request
                    BlockingServlet blockingServlet = new BlockingServlet();
                    blockingServlet.doPost(request, (HttpServletResponse) asyncContext.getResponse());
    
                    // Retrieve the Audio object from the BlockingServlet
                    Audio audio = blockingServlet.getAudio();
    
                    // Add the number of copies sold to the total copies sold count
                    synchronized (lock) {
                        copiesSold.addAndGet(audio.getNumCopiesSold());
                    }
    
                    // Update the database with the new Audio object
                    // ...
    
                    // Complete the asynchronous request
                    asyncContext.complete();
    
                } catch (Exception e) {
                    // Handle exceptions
                    e.printStackTrace();
                }
            }
        });
    
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
