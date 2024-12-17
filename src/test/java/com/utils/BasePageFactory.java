package com.utils;

import com.microsoft.playwright.Page;
import com.pages.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BasePageFactory {

    public static <T extends BasePage> T createInstance(final Page page, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            instance.setAndConfigurePage(page);
            instance.initComponents();
            return instance;
        } catch (Exception e) {
            log.error("Create instance failed ", e);
            throw new RuntimeException(e);
        }
    }
}
