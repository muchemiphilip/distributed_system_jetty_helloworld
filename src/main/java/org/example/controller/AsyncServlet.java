package org.example.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class AsyncServlet extends HttpServlet {
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Start asynchronous processing
    AsyncContext asyncContext = request.startAsync();
    
    // Get a CompletableFuture object representing the async operation
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      // Perform some long-running operation
      return "Async operation complete!";
    });
    
    // Attach a listener to the CompletableFuture to handle the result
    future.thenAccept(result -> {
      try {
        // Write the result to the response
        response.getWriter().write(result);
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      // Complete the async context
      asyncContext.complete();
    });
  }
  
}
