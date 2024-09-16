package hu.bca.library.config;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenLibraryClient {

  private final RestTemplate restTemplate = new RestTemplate();

  public Integer getPublicationYear(String workId) {
    String url = String.format("https://openlibrary.org/works/%s.json", workId);
    try {
      String response = restTemplate.getForObject(url, String.class);
      JSONObject jsonObject = new JSONObject(response);

      if (jsonObject.has("first_publish_date")) {
        return extractYear(jsonObject.getString("first_publish_date"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Integer extractYear(String date) {
    if (date != null && !date.isEmpty()) {
      String[] parts = date.split(" ");
      String yearPart = parts[parts.length - 1];
      return Integer.parseInt(yearPart);
    }
    return null;
  }
}
