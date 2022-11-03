package serenityMain.inventory;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.steps.UIInteractionSteps;
import net.serenitybdd.junit5.SerenityTest;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import serenityMain.authentication.LoginAction;
import serenityMain.authentication.Users;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SerenityTest
public class HighlightedItems extends UIInteractionSteps {

    @Managed()
    WebDriver driver;

    @Steps
    LoginAction loginAction;


    @Test
    public void displayHighlightedItems(){
        loginAction.as(Users.Standard_User);
        List<String> productList = findAll((".inventory_item_name")).textContents();
        assertThat(productList.size()).isEqualTo(6);
        assertThat(productList.contains("Sauce Labs Backpack")).isTrue();
    }

    @Test
    public void clickingItemShouldDisplayCorrectly(){
        loginAction.as(Users.Standard_User);
        List<String> productList = findAll((".inventory_item_name")).textContents();
        String firstItem = productList.get(0);
        String secondItem = productList.get(1);
        find(By.linkText(firstItem)).click();
        Serenity.reportThat("When item is clicked correct item is rendered on the page",() ->
                assertThat(find((".inventory_details_name")).getText())
                        .isEqualToIgnoringCase(firstItem)
                );
        Serenity.reportThat("incorrect item is not shown on the new rendered page ", () ->
                        assertThat(find((".inventory_details_name")).getText())
                                .isNotEqualTo(secondItem));
         // testing that the test will fail
    }
}
