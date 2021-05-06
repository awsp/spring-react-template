package com.github.awsp.frontend;

import com.github.awsp.config.ApplicationConfiguration;
import com.github.awsp.model.Sample;
import com.github.awsp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/")
@Controller
public class PageController {

    private final ApplicationConfiguration configuration;

    @Autowired
    public PageController(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("foo", configuration.getFoo());
        model.addAttribute("sample", new Sample());

        return "frontend/index";
    }

    @GetMapping("/sub-page")
    public String subPage() {
        return "frontend/subpage";
    }

    @GetMapping("/request-param")
    public String withRequestParameter(@RequestParam(name = "projectId", required = false) String projectId, Model model) {
        model.addAttribute("id", projectId);

        return "frontend/with_parameter";
    }

    @GetMapping("/query-param/{id:[0-9]*}")
    public String withQueryParameter(@PathVariable(name = "id", required = false) int id, Model model) {
        model.addAttribute("id", id);

        return "frontend/with_parameter";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Sample sample) {
        return "frontend/result";
    }

    @GetMapping("/form-submission")
    public String formSubmission() {
        return "frontend/form_submission";
    }

    @PostMapping("/form-submission")
    public String handleFormSubmission(@ModelAttribute User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "frontend/form_result";
    }
}