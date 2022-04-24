package myproject.coronavirustracker.services;

import myproject.coronavirustracker.models.LatestDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusTrackerService {

    @Value("${dataURL}")
    private String dataUrl;

    private List<LatestDataModel> data = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "0 0  11 * * ?")
    public void fetchData() throws IOException, InterruptedException {

        List<LatestDataModel> tempStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(dataUrl)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader responseReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(responseReader);
        for (CSVRecord record : records) {
            LatestDataModel locationData = new LatestDataModel();
             locationData.setState(record.get("Province/State") != "" ? record.get("Province/State") : "Combined country wide data");
             locationData.setCountry(record.get("Country/Region"));
             int lastDayCases = Integer.parseInt(record.get(record.size() - 1));
             locationData.setLastDayCases(lastDayCases);
             int lastSecondDayCases = Integer.parseInt(record.get(record.size() - 2));
             locationData.setDiffFromPrevDay(locationData.getLastDayCases() - lastSecondDayCases);
             int last7DayCases = lastDayCases + lastSecondDayCases
                     + Integer.parseInt(record.get(record.size() - 3))
                     + Integer.parseInt(record.get(record.size() - 4))
                     + Integer.parseInt(record.get(record.size() - 5))
                     + Integer.parseInt(record.get(record.size() - 6))
                     + Integer.parseInt(record.get(record.size() - 7));
             locationData.setLast7DaysCases(last7DayCases);
             System.out.println(locationData);
             tempStats.add(locationData);
        }
        this.data = tempStats;
    }

    public List<LatestDataModel> getData() {
        return data;
    }

}
