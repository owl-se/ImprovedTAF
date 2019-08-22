package taf.pages.example;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;

import taf.core.ConfigManager;
import taf.locators.example.GoogleResultsPageLocators;
import taf.pages.AbstractPage;

public class GoogleResultsPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    @FindBy(xpath = GoogleResultsPageLocators.CURRENCY_CONVERTER_AREA)
    private WebElement currencyConverterArea;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_CURRENCY_FROM_SELECT)
    private Select currencyFromSelector;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_CURRENCY_TO_SELECT)
    private Select currencyToSelector;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_AMOUNT_TO_INPUT)
    private TextInput amountToInput;

    GoogleResultsPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(By.xpath(GoogleResultsPageLocators.RESULT_STATS_AREA), ConfigManager.getWaitForPageUploadSec());
        waitUntilPageScriptsReady();
    }

    public int getNumberOfResultsOnPage() {
        int num = getDriver().findElements(By.xpath(GoogleResultsPageLocators.SINGLE_RESULT)).size();
        log.info("page contains " + num + " results");
        return num;
    }

    public boolean isCurrencyConverterAreaDisplayed() {
        return isElementPresent(currencyConverterArea, 1);
    }

    public String getConverterAreaSelectorCurrencyFromText() {
        String currency = currencyFromSelector.getFirstSelectedOption().getText();
        log.info("currency from = " + currency);
        return currency;
    }

    public String getConverterAreaCurrencyToText() {
        String currency = currencyToSelector.getFirstSelectedOption().getText();
        log.info("currency to = " + currency);
        return currency;
    }

    public String getConverterAreaAmountToText() {
        String amount = amountToInput.getText();
        log.info("Google Page: Rate = " + amount);
        return amount;
    }

}
