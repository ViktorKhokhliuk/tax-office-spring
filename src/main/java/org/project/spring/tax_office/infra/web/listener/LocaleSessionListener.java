package org.project.spring.tax_office.infra.web.listener;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Locale;

@Log4j2
@RequiredArgsConstructor
public class LocaleSessionListener implements HttpSessionListener {

    private final List<Locale> locales = List.of(new Locale("en"), new Locale("ru"));

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        log.info("Session created");
        session.setAttribute("locales", locales);
        log.info("Set session locales --> " + locales);
        session.setAttribute("selectedLocale", locales.get(0));
        log.info("Set session selected locale --> " + locales.get(0));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
    }
}
