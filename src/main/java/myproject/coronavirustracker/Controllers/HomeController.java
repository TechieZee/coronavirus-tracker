package myproject.coronavirustracker.Controllers;

import myproject.coronavirustracker.models.LatestDataModel;
import myproject.coronavirustracker.services.CoronavirusTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronavirusTrackerService service;

    @GetMapping("/")
    public String Home(Model model) {
        List<LatestDataModel> data = service.getData();
        int totalCases = data.stream().mapToInt(stat -> stat.getLastDayCases()).sum();
        int totalNewCases = data.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("data", data);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "index";
    }
}
