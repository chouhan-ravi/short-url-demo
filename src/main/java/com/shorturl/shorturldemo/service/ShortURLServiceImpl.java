package com.shorturl.shorturldemo.service;

import com.shorturl.shorturldemo.dto.ShortUrlDTO;
import com.shorturl.shorturldemo.model.ShortURL;
import com.shorturl.shorturldemo.model.UserLog;
import com.shorturl.shorturldemo.repository.LOGRepository;
import com.shorturl.shorturldemo.repository.URLRepository;
import com.shorturl.shorturldemo.utils.Base62Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

@Service
public class ShortURLServiceImpl implements ShortURLService {

    private final String domain;

    private URLRepository urlRepository;
    private LOGRepository logRepository;

    @Autowired
    public ShortURLServiceImpl(@Value("${domain.shortener}") String domain,
                               URLRepository urlRepository, LOGRepository logRepository) {
        this.domain = domain;
        this.urlRepository = urlRepository;
        this.logRepository = logRepository;
    }

    /**
     * Reverse the original URL from the shortened URL
     */
    @Override
    public ShortUrlDTO getURL(String shortURL) {
        ShortUrlDTO dto = new ShortUrlDTO();
        if (validateShortURL(shortURL)) {
            String str = shortURL.replace(this.domain +"/", "");
            Optional<ShortURL> urlShortener = urlRepository.findByShortURL(str);
            if(!urlShortener.isPresent()) {
                long id = Base62Util.toBase10(str);
                urlShortener = urlRepository.findById(id);
            }
            if(urlShortener.isPresent()) {
                ShortURL url = urlShortener.get();
                dto = mapToDTO(url);
            }
        }
        return dto;
    }

    @Override
    public ShortUrlDTO getOriginalURLByShortURL(String shortURL) {
        saveUserLog(shortURL, "");
        return mapToDTO(urlRepository.findByShortURL(shortURL).get());
    }

    @Override
    public ShortUrlDTO saveUrl(String originalURL, String customShortURL) throws Exception {
        ShortURL url = new ShortURL();
        if (validateURL(originalURL)) {
            originalURL = sanitizeURL(originalURL);
            Optional<ShortURL> existURL = urlRepository.findByOriginalURL(originalURL);
            if(existURL.isPresent()) {
                url = existURL.get();
            } else {
                url.setId(urlRepository.getIdWithNextUniqueId());
                if(!StringUtils.hasText(customShortURL)) {
                    customShortURL = Base62Util.toBase62(url.getId().intValue());
                }
                url.setShortURL(customShortURL);
                url.setOriginalURL(originalURL);
                url = urlRepository.save(url);
            }
        } else {
            throw new Exception("Invalid originalURL");
        }
        return mapToDTO(url);
    }

    @Override
    public boolean isExistCustomShortURL(String customShortURL) {
        String str = customShortURL.replace(this.domain +"/", "");
        if(urlRepository.findByShortURL(str).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveUserLog(String shortURL, String logInfo) {
        UserLog log = new UserLog();
        log.setShortURL(shortURL);
        log.setLogInfo(logInfo);
        logRepository.save(log);
    }

    @Override
    public List<UserLog> getAllLogs() {
        return logRepository.findAll();
    }

    /**
     * Generate a shortened URL.
     */
    private ShortUrlDTO mapToDTO(ShortURL url) {
        // Mapped domain to DTO
        ShortUrlDTO dto = new ShortUrlDTO();
        dto.setId(url.getId().toString());
        dto.setOriginalURL(url.getOriginalURL());
        dto.setCreatedOn(url.getCreatedOn().toString());
        String shortenedURL = this.domain +"/" + url.getShortURL();
        dto.setShortURL(shortenedURL);
        return dto;
    }

    /**
     * Validate URL not implemented, but should be implemented to
     * check whether the given URL is valid or not
     */
    private boolean validateURL(String urlString) {
        boolean isValid = false;
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.connect();
            isValid = true;
        } catch (MalformedURLException e) {
            isValid = false;
        } catch (IOException e) {
            // the connection couldn't be established
            isValid = false;
        }
        return isValid;
    }

    private boolean validateShortURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * This method should take care various issues with a valid url
     * e.g. www.google.com,www.google.com/, http://www.google.com,
     * http://www.google.com/
     * all the above URL should point to same shortened URL
     * There could be several other cases like these.
     */
    private String sanitizeURL(String url) {
        if (url.substring(0, 7).equals("http://"))
            url = url.substring(7);

        if (url.substring(0, 8).equals("https://"))
            url = url.substring(8);

        if (url.charAt(url.length() - 1) == '/')
            url = url.substring(0, url.length() - 1);
        return url;
    }
}
