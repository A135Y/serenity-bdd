package serenityMain.shopping;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.steps.UIInteractionSteps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import serenityMain.authentication.LoginAction;
import serenityMain.authentication.Users;
import serenityMain.inventory.InventoryPage;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SerenityJUnit5Extension.class)
public class ShoppingAction extends UIInteractionSteps {

    @Managed(driver = "chrome", options = "headless")
    WebDriver driver;

    @Steps
    LoginAction loginAction;

    InventoryPage inventoryPage;

    @Test

    public void shoppingCartStartsEmptyFromLoggingIn(){
        loginAction.as(Users.Standard_User);

        Serenity.reportThat("Expect shopping cart value is not visible from logging in",
                () ->
                        assertThat(findBy(".shopping_cart_badge").isVisible()).isFalse()
        );
        find("[data-test='add-to-cart-sauce-labs-bike-light']").click();
        find("[data-test='add-to-cart-sauce-labs-onesie']").click();

        Serenity.reportThat("Expect shopping cart value to be visible from adding items",
                () ->
                        assertThat(findBy(".shopping_cart_badge").isVisible()).isTrue()
        );

        Serenity.reportThat("Expect shopping cart total to equal total items added to basket",
                () ->
                        assertThat(findBy(".shopping_cart_badge").getText())
                                .isEqualTo("2")
        );
    }

    @Test
    public void userCanAddItemsToCartCorrectly(){
        loginAction.as(Users.Standard_User);

        find("[data-test='add-to-cart-sauce-labs-backpack']").click();
        find("[data-test='add-to-cart-sauce-labs-bike-light']").click();
        find("[data-test='add-to-cart-sauce-labs-onesie']").click();
        Serenity.reportThat("Expect shopping cart total to equal total items added to basket",
                () ->
                        assertThat(findBy(".shopping_cart_badge").getText())
                                .isEqualTo("3")
                );

    }

    @Test
    public void itemsSelectedShowUpCorrectlyInCart(){
        loginAction.as(Users.Standard_User);
        find("[data-test='add-to-cart-sauce-labs-backpack']").click();
        find("[data-test='add-to-cart-sauce-labs-bike-light']").click();
        find("[data-test='add-to-cart-sauce-labs-onesie']").click();
        find(".shopping_cart_link").click();
        Serenity.reportThat("Title of cart page should display correctly", () ->
                assertThat(find(".title").getText()).isEqualToIgnoringWhitespace("YOUR CART")
                );
    }

}
