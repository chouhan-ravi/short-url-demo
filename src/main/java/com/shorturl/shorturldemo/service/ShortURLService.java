package com.shorturl.shorturldemo.service;

import com.shorturl.shorturldemo.dto.ShortUrlDTO;
import com.shorturl.shorturldemo.model.UserLog;

import java.util.List;

public interface ShortURLService {
    ShortUrlDTO getOriginalURLByShortURL(String shortURL);

    ShortUrlDTO saveUrl(String originalURL, String customShortURL) throws Exception;
    ShortUrlDTO getURL(String shortURL);

    boolean isExistCustomShortURL(String customShortURL);

    void saveUserLog(String shortURL, String logInfo);

    List<UserLog> getAllLogs();
}
