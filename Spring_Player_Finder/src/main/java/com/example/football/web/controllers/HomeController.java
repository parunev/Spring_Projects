package com.example.football.web.controllers;

import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
public class HomeController extends BaseController {

    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;
    private final PlayerService playerService;

    @GetMapping("/")
    public ModelAndView index() {
        boolean areImported = this.townService.areImported() &&
                this.teamService.areImported() &&
                this.statService.areImported() &&
                this.playerService.areImported();

        return super.view("index", "areImported", areImported);
    }
}
