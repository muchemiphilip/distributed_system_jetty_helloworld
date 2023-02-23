package org.example.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import org.example.model.Audio;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BlockingServlet extends HttpServlet {

    private Audio audio;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

        // Complete the async context
        asyncContext.complete();
    }

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

        // Complete the async context
        asyncContext.complete();
    }
    public Audio getAudio() {
        return audio;
    }
}
