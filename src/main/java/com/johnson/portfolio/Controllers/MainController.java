package com.johnson.portfolio.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String getIndex(HttpServletRequest request){

        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null){
            clientIp = request.getRemoteAddr();
        }

        logger.info("user [{}] entered homepage",clientIp);

        return "index";
    }

    @GetMapping("/privacy")
    public String getPrivacyPolicy(HttpServletRequest request){
        return "privacy";
    }

}
