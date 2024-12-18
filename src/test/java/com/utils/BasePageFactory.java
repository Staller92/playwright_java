package com.utils;

import com.microsoft.playwright.Page;
import com.pages.BasePage;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public final class BasePageFactory {

    private static final Logger log = LoggerFactory.getLogger(BasePageFactory.class);

    public static <T extends BasePage> T createInstance(final Page page, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            instance.setAndConfigurePage(page);
            instance.initComponents();
            return instance;
        } catch (Exception e) {
            log.error(e, () -> "Create instance failed");
            throw new RuntimeException(e);
        }
    }
}
