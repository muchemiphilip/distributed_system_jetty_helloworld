package org.example.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                Thread.sleep(5000); // sleep for 5 seconds to simulate an asynchronous operation

                // Write the response
                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                response.setContentType("text/html");
                response.getWriter().write("<html><body><h1>Async operation complete!</h1></body></html>");

                // Complete the asynchronous operation
                asyncContext.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
