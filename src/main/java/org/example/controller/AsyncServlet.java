package org.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "AsyncServlet", value = "/async-servlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create an async context
        AsyncContext asyncContext = request.startAsync();

        // Start a new thread to handle the async request
        asyncContext.start(() -> {
            try {
                // Simulate a long-running task
                Thread.sleep(5000);

                // Get the response writer
                PrintWriter writer = response.getWriter();

                // Write the response
                // writer.write("Async operation complete!");
                writer.flush();

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } finally {
                // Complete the async context
                asyncContext.complete();
            }
        });
    }
}
