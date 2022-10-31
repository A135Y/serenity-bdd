package serenityMain.authentication;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.steps.UIInteractionSteps;
import net.serenitybdd.junit5.SerenityTest;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import serenityMain.inventory.InventoryPage;

import static org.assertj.core.api.Assertions.assertThat;

@SerenityTest
public class WhenLoggingIn extends UIInteractionSteps {

    @Managed
    WebDriver driver;

    @Steps
    LoginAction loginAction;

    InventoryPage inventoryPage;

    @Test
    public void usersCanLogIn() {
        loginAction.as(Users.Standard_User);
        Serenity.reportThat("The main page should display the correct title", () ->
                assertThat(inventoryPage.getHeading()).isEqualToIgnoringCase("Products")
        );
    }

    @Test
    public void lockedUserShowsCorrectErrorMessage(){
        loginAction.as(Users.Locked_Out_User);
        Serenity.reportThat("Correct error message is shown for locked out user", () ->
                assertThat(find("[data-test='error']").getText())
                        .isEqualToIgnoringCase("Epic sadface: Sorry, this user has been locked out.")
                );
    }

    @Test
    public void websiteShowsRepeatedImages(){
        loginAction.as(Users.Problem_User);
//        System.out.println(find("#item_4_img_link").findElement(By.className("inventory_item_img")).getAttribute("src"));
//        System.out.println(find("#item_0_img_link").findElement(By.className("inventory_item_img")).getAttribute("src"));
        Serenity.reportThat("", () ->
                assertThat(find("#item_4_img_link").findElement(By.className("inventory_item_img")).getAttribute("src"))
                        .isEqualToIgnoringCase(find("#item_0_img_link").findElement(By.className("inventory_item_img"))
                                .getAttribute("src"))
                );
    }


}
