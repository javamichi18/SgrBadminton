package de.soflimo.sgr.web;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.soflimo.sgr.SgrBadmintonApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SgrBadmintonApplication.class)
//@WebIntegrationTest(randomPort = true)
@WebIntegrationTest({ "server.port:9000", "management.port=0" })
@Ignore
public class ServerWebTests {

    private static final String TEST_USER = "michi@klingtlogisch.de";

    private static FirefoxDriver browser;

    @Value("${local.server.port}")
    private int port;


    @BeforeClass
    public static void openBrowser () {

        System.setProperty("spring.profiles.active", "test");

        FirefoxProfile prof = new FirefoxProfile();
        prof.setPreference("browser.startup.homepage_override.mstone", "ignore");
        prof.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        browser = new FirefoxDriver(prof);
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    @Test
    public void getRangliste () throws InterruptedException {
        String baseUrl = "http://localhost:" + port + "/sgrbadminton";
        browser.get(baseUrl);

        String currentUrl = browser.getCurrentUrl();
        assertEquals(baseUrl + "/login.html", currentUrl);

        browser.findElementByName("username").sendKeys(TEST_USER);
        browser.findElementByName("password").sendKeys(TEST_USER);
        browser.findElementByTagName("form").submit();

        waitForAjax();

        WebElement erster = browser.findElementById("1");
        String text = erster.getText();

        assertEquals("1\n"
            + "Thomas Härtel", text);
        //        WebElement dl =
        //            browser.findElementByCssSelector("dt.bookHeadline");
        //        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)",
        //            dl.getText());
        //        WebElement dt =
        //            browser.findElementByCssSelector("dd.bookDescription");
        //        assertEquals("DESCRIPTION", dt.getText());
    }


    private void waitForAjax () throws InterruptedException { //throws InterruptedException {

        while (true) {

            Boolean ajaxIsComplete = (Boolean) ((JavascriptExecutor) browser)
                .executeScript("return window.jQuery != undefined && jQuery.active === 0");
            if (ajaxIsComplete) {
                break;
            }
            Thread.sleep(100);
        }
    }


    @AfterClass
    public static void closeBrowser () {
        browser.quit();
    }

}
