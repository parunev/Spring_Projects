package softuni.exam.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.exam.service.TownService.TownService;
import softuni.exam.service.OfferService.OfferService;
import softuni.exam.service.AgentService.AgentService;
import softuni.exam.service.ApartmentService.ApartmentService;

@AllArgsConstructor
@Controller
public class HomeController extends BaseController {

    private final TownService townService;
    private final OfferService offerService;
    private final AgentService agentService;
    private final ApartmentService apartmentService;


    @GetMapping("/")
    public ModelAndView index() {
        boolean areImported = this.townService.areImported() &&
                this.offerService.areImported() &&
                this.agentService.areImported() &&
                this.apartmentService.areImported();

        return super.view("index", "areImported", areImported);
    }
}
