package softuni.exam.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.exam.service.OfferService.OfferService;

@AllArgsConstructor
@Controller
@RequestMapping("/export")
public class ExportController extends BaseController{

    private final OfferService offerService;

    @GetMapping("/best-offers")
    public ModelAndView exportOffersByAreaAndPrice() {
        String offersOrderByAreaThenPrice = this.offerService.getOffersOrderByAreaThenPrice();

        return super.view("export/export-offers-by-area-then-price.html", "offersByAreaThenPrice", offersOrderByAreaThenPrice);
    }
}
