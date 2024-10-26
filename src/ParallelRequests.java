import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelRequests {

    private static final int REQUEST_COUNT = 100;  // Total number of requests
    private static final String BASE_URL = "http://localhost:8000/";

    // List of different requests that we will send
    private static final String[] REQUEST_DATA = {
            "TaskBitcoin&3",
            "TaskBitcoin&5",
            "TaskBubbleSort&20,10,15,7,33,31,25,10,16,9,28,77,1,4,3,5,6,2,22,13",
            "TaskFactorial&10",
            "TaskFactorial&5",
            "TaskSum&100",
            "TaskSum&10",
            "TaskFiboRecursive&43",
            "TaskFiboRecursive&44",
            "TaskFiboRecursive&45"
    };

    public static void main(String[] args) {
        // Create an HTTP client to send requests
        HttpClient client = HttpClient.newHttpClient();
        // Set number of threads equal to the number of requests
        ExecutorService executor = Executors.newFixedThreadPool(REQUEST_COUNT);  // Number of threads equal to the number of requests

        // List to hold future responses
        List<Future<HttpResponse<String>>> futures = new ArrayList<>();

        // Send 100 requests with different data to the server
        for (int i = 0; i < REQUEST_COUNT; i++) {
            // Select request data in a round-robin fashion from REQUEST_DATA array
            String requestData = REQUEST_DATA[i % REQUEST_DATA.length];

            // Set up the HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestData))
                    .build();

            // Send the request asynchronously
            Future<HttpResponse<String>> future = executor.submit(() -> client.send(request, HttpResponse.BodyHandlers.ofString()));
            futures.add(future);
        }

        // Process responses after all requests have been completed
        futures.forEach(future -> {
            try {
                // Retrieve and print the response body
                HttpResponse<String> response = future.get();
                System.out.println("Response: " + response.body());
            } catch (Exception e) {
                // Print error message if any request fails
                System.out.println("Error: " + e.getMessage());
            }
        });

        // Shut down ExecutorService
        executor.shutdown();
        System.out.println("All requests have been sent.");
    }
}
