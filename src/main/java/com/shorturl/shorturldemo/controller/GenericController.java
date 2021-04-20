package com.shorturl.shorturldemo.controller;

import com.shorturl.shorturldemo.dto.ShortUrlDTO;
import com.shorturl.shorturldemo.model.UserLog;
import com.shorturl.shorturldemo.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GenericController {

    private ShortURLService shortURLService;

    @Autowired
    public GenericController(ShortURLService shortURLService) {
        this.shortURLService = shortURLService;
    }

    @GetMapping(value = "/{shortURL}", produces = MediaType.TEXT_HTML_VALUE)
    public String actualRedirect(@PathVariable(value = "shortURL", required = true) String shortURL,
                        Model model) {
        if(!StringUtils.hasText(shortURL)) {
            return "404";
        }
        if(!shortURLService.isExistCustomShortURL(shortURL)) {
            return "404";
        }
        ShortUrlDTO urlDTO = shortURLService.getOriginalURLByShortURL(shortURL);
        model.addAttribute("originalURL", urlDTO.getOriginalURL());
        return "redirect";
    }

    @GetMapping(value = "/logs")
    public String viewLogs(Model model) {
        List<UserLog> logs = shortURLService.getAllLogs();
        model.addAttribute("userLogs", logs);
        return "userLogs";
    }
}
