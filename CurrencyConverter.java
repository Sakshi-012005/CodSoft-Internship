import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;


public class CurrencyConverter {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("AVAILABLE CURRENCIES EXAMPLE: USD, EUR, INR, GBP, JPY");
        System.out.print("Enter base currency: ");
        String base = sc.next().toUpperCase();

        System.out.print("Enter target currency: ");
        String target = sc.next().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = sc.nextDouble();

        // Build API URL
        String apiUrl = "https://open.er-api.com/v6/latest/" + base;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON
            String responseBody = response.body();

// Check success
if (!responseBody.contains("\"result\":\"success\"")) {
    System.out.println("Error fetching rates. Try again!");
    return;
}

// Find rate manually
String searchKey = "\"" + target + "\":";
int index = responseBody.indexOf(searchKey);

if (index == -1) {
    System.out.println("Target currency not supported.");
    return;
}

int start = index + searchKey.length();
int end = responseBody.indexOf(",", start);

if (end == -1) {
    end = responseBody.indexOf("}", start);
}

double rate = Double.parseDouble(responseBody.substring(start, end).trim());

            double convertedAmount = amount * rate;

            System.out.printf("%.2f %s = %.2f %s\n", amount, base, convertedAmount, target);

        } catch (IOException | InterruptedException e) {
            System.out.println("Network Error");
        }

        sc.close();
    }
}