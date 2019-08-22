package taf.cases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.htmlelements.annotations.Name;
import taf.core.Dictionary;
import taf.data.TestData;
import taf.pages.example.GoogleResultsPage;
import taf.pages.example.GoogleSearchPage;

@Name("Validate currency rate")
@Test(groups = {"regression", "ui"})
public class ValidateCurrencyRateTest extends CommonActionsTest {

    GoogleSearchPage searchPage;
    GoogleResultsPage resultPage;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        openBrowser();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowser();
    }

    @Test
    public void tc_rate_01_open_google_start_page() {

        searchPage = openGoogleSearchPage();

        softAssert.assertTrue(
                searchPage.isGoogleSearchButtonDisplayed(),
                "'Google search' button is displayed"
        );

        softAssert.assertTrue(
                searchPage.isSearchLineDisplayed(),
                "Search line is displayed"
        );

        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "tc_rate_01_open_google_start_page")
    public void tc_rate_02_search_current_rate() {

        resultPage = searchPage.search(TestData.CASE_02_TEXT_EURO_RATE_TO_USD);

        Assert.assertTrue(
                resultPage.getNumberOfResultsOnPage() > 0,
                "Result page contains search results"
        );

        Assert.assertTrue(
                resultPage.isCurrencyConverterAreaDisplayed(),
                "Currency converter area is displayed"
        );

        Assert.assertEquals(
                resultPage.getConverterAreaSelectorCurrencyFromText().toUpperCase(),
                TestData.CASE_02_EXPECTED_CURRENCY_FROM.toUpperCase(),
                "Name of currency 'From'"
        );

        Assert.assertEquals(
                resultPage.getConverterAreaCurrencyToText().toUpperCase(),
                TestData.CASE_02_EXPECTED_CURRENCY_TO.toUpperCase(),
                "Name of currency 'To'"
        );
    }

    @Test(dependsOnMethods = "tc_rate_02_search_current_rate")
    public void tc_rate_03_compare_with_data_from_bank() {

        String amountFromGoogle = resultPage.getConverterAreaAmountToText();

        String amountFromBank = getCurrencyAmountFromBank(Dictionary.USD);

        Assert.assertEquals(
                amountFromGoogle,
                amountFromBank,
                "Rate"
        );
    }

}
