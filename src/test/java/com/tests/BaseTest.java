package com.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.pages.LoginPage;
import com.utils.BasePageFactory;
import com.utils.BrowserManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.config.ConfigurationManager.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected LoginPage loginPage;
    private boolean needVideo;

    @RegisterExtension
    AfterTestExecutionCallback callback =
            context -> {
                Optional<Throwable> exception = context.getExecutionException();
                if (exception.isPresent()) {
                    needVideo = true;
                    captureScreenshotOnFailure();
                }
            };

    @AfterEach
    public void attach() {
        browserContext.close();
        if (config().video() && needVideo) {
            captureVideo();
        }
        needVideo = false;
    }

    @BeforeEach
    public void createContext() {
        if (config().video()) {
            browserContext = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get(config().baseTestVideoPath())).setViewportSize(config().screenWidth(), config().screenHeight()));
        } else {
            browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(config().screenWidth(), config().screenHeight()));
        }
        page = browserContext.newPage();
        loginPage = BasePageFactory.createInstance(page, LoginPage.class);
    }


    @Attachment(value = "Test Video", type = "video/webm")
    private byte[] captureVideo() {
        try {
            return Files.readAllBytes(page.video().path());
        } catch (IOException e) {
            log.error(e, () -> "Video capture is failed");
            throw new RuntimeException(e);
        }
    }

    @Attachment(value = "Failed Test Case Screenshot", type = "image/png")
    private byte[] captureScreenshotOnFailure() {
        return page.screenshot();
    }


    @BeforeAll
    public void initBrowser() {
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);
    }

    @AfterAll
    public void close() {
        browser.close();
        playwright.close();
    }
}