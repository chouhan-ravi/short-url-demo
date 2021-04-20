package com.shorturl.shorturldemo.controller;

import com.shorturl.shorturldemo.dto.ShortUrlDTO;
import com.shorturl.shorturldemo.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/url")
public class ShortUrlController {

    private ShortURLService shortURLService;

    @Autowired
    public ShortUrlController(ShortURLService shortURLService) {
        this.shortURLService = shortURLService;
    }

    @RequestMapping(value = "/shorten",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveURL(@RequestParam(value = "originalURL", required = true) String originalURL,
                                     @RequestParam(value = "customShortURL", required = false) String customShortURL) {
        if(StringUtils.hasText(customShortURL)) {
            if (shortURLService.isExistCustomShortURL(customShortURL))
               return new ResponseEntity<>("Custom Short URL already exists", HttpStatus.BAD_REQUEST);
        }
        try {
            ShortUrlDTO dto = shortURLService.saveUrl(originalURL, customShortURL);
            return new ResponseEntity<>("Short URL created", HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reverse", method = RequestMethod.GET)
    public ResponseEntity<?> getURL(@RequestParam(value = "shortURL") String shortURL) {
        if(!shortURLService.isExistCustomShortURL(shortURL)) {
            return new ResponseEntity<>("shortURL not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shortURLService.getURL(shortURL), HttpStatus.OK);
    }
}
