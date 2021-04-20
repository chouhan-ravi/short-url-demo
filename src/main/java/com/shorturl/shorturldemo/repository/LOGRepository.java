package com.shorturl.shorturldemo.repository;

import com.shorturl.shorturldemo.model.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LOGRepository extends JpaRepository<UserLog, Long> {
}
