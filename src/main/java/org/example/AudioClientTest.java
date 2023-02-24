package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioClientTest {
    private static final String BASE_URL = "http://localhost:8080/";
    private static final int NUM_TRIALS = 10;
    private static final int NUM_THREADS = 10;
    private static final int[] CLIENT_RATIOS = { 2, 5, 10 };
    private static final int[] CLIENT_TOTALS = { 10, 50, 100 };
    private static final int[] GET_RATIOS = { 1, 2 };
    private static final int TIMEOUT = 5000; // milliseconds

    private ExecutorService executor;
    private CountDownLatch latch;

    public void test() {
        for (int ratio : CLIENT_RATIOS) {
            for (int total : CLIENT_TOTALS) {
                int numGets = ratio * total / (GET_RATIOS[0] + GET_RATIOS[1] * ratio);
                int numPosts = total - numGets;
                int numGets1 = numGets * GET_RATIOS[0] / (GET_RATIOS[0] + GET_RATIOS[1]);
                int numGets2 = numGets - numGets1;

                List<Long> getTimes = new ArrayList<>();
                List<Long> postTimes = new ArrayList<>();

                for (int trial = 0; trial < NUM_TRIALS; trial++) {
                    executor = Executors.newFixedThreadPool(NUM_THREADS);
                    latch = new CountDownLatch(total);

                    for (int i = 0; i < numGets1; i++) {
                        executor.execute(new GetPropertyRunnable(getTimes, "artist1", "property1"));
                    }
                    for (int i = 0; i < numGets2; i++) {
                        executor.execute(new GetArtistRunnable(getTimes, "artist2"));
                    }
                    for (int i = 0; i < numPosts; i++) {
                        executor.execute(new PostAudioRunnable(postTimes));
                    }

                    try {
                        latch.await(TIMEOUT, java.util.concurrent.TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    executor.shutdownNow();
                }

                // Compute average times
                long avgGetTime = (long) getTimes.stream().mapToLong(Long::longValue).average().orElse(0);
                long avgPostTime = (long) postTimes.stream().mapToLong(Long::longValue).average().orElse(0);

                // Print results
                System.out.println(String.format("Ratio: %d:%d, Total: %d", ratio, 1, total));
                System.out.println(String.format("GET (property1): %.2f ms", avgGetTime));
                System.out.println(String.format("GET (all artists): %.2f ms", avgGetTime));
                System.out.println(String.format("POST: %.2f ms", avgPostTime));
                System.out.println();
            }
        }
    }

    private class GetPropertyRunnable implements Runnable {
        private List<Long> times;
        private String artist;
        private String property;

        public GetPropertyRunnable(List<Long> times, String artist, String property) {
            this.times = times;
            this.artist = artist;
            this.property = property;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            AudioClient.getProperty(BASE_URL, artist, property);
            times.add(System.currentTimeMillis() - start);
            latch.countDown();
        }
    }

    private class GetArtistRunnable implements Runnable {
        private List<Long> times;
        private String artist;
    
        public GetArtistRunnable(List<Long> times, String artist) {
            this.times = times;
            this.artist = artist;
        }
    
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            AudioClient.getArtist(BASE_URL, artist);
            times.add(System.currentTimeMillis() - start);
            latch.countDown();
        }
    }

    private class PostAudioRunnable implements Runnable {
        private List<Long> times;
    
        public PostAudioRunnable(List<Long> times) {
            this.times = times;
        }
    
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            AudioClient.postAudio(BASE_URL, "audio data");
            times.add(System.currentTimeMillis() - start);
            latch.countDown();
        }
    }
    
    
}
