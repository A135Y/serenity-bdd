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

import java.util.*;

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

        String item1 =findBy("//a[@id ='item_4_title_link']/div").getText();
        String item2 = findBy("//a[@id ='item_0_title_link']/div").getText();
        String item3 = findBy("//a[@id ='item_2_title_link']/div").getText();

        List<String> allItemsPrices = new ArrayList<>(findAll(".inventory_item_price").textContents());

        find("[data-test='add-to-cart-sauce-labs-backpack']").click();
        find("[data-test='add-to-cart-sauce-labs-bike-light']").click();
        find("[data-test='add-to-cart-sauce-labs-onesie']").click();
        find(".shopping_cart_link").click();

        Serenity.reportThat("Title of cart page should display correctly", () ->
                assertThat(find(".title").getText()).isEqualToIgnoringWhitespace("YOUR CART")
                );

        List<String> itemsInCart = new ArrayList<>(findAll(".inventory_item_name").textContents());

        List<String> allPricesInCart = new ArrayList<>(findAll(".inventory_item_price").textContents());


        Serenity.reportThat("Items in the cart are the same as the items clicked", () -> {
            assertThat(item1).isEqualTo(itemsInCart.get(0));
            assertThat(item2).isEqualTo(itemsInCart.get(1));
            assertThat(item3).isEqualTo(itemsInCart.get(2));
        });

        Serenity.reportThat("prices are shown correctly", () -> {
           assertThat(allItemsPrices).containsAll(allPricesInCart);
        });
    }



    @Test
    public void userCanSortItemsCorrectly() {
        loginAction.as(Users.Standard_User);
        findBy("//select/option[3]").click();
        boolean correctOrder = false;
        List<String> allItemsPrices = new ArrayList<>(findAll(".inventory_item_price").textContents());

        List<Double> allPrices = new ArrayList<>();

        for (String allItemsPrice : allItemsPrices) {
            String newString = allItemsPrice.substring(1);
            allPrices.add(Double.parseDouble(newString));
        }

        for(int i = 0; i < allPrices.size()-1; i++){
            correctOrder = allPrices.get(i) < allPrices.get(i + 1);
        }

        boolean finalCorrectOrder = correctOrder;
        Serenity.reportThat("Items are rendered correctly as low-hi prices", () -> {
            assertThat(finalCorrectOrder).isTrue();
        });
    }

    @Test
    public void canUseHamburgerIconCorrectly(){
        loginAction.as(Users.Standard_User);

        findBy("//div[@class = 'bm-burger-button']/button").click();
        findBy("//a[@id='about_sidebar_link']").click();
      String title =  find(".supertitle").getText();
        Serenity.reportThat("When clicking on hamburger icon/about correct page renders with title", () -> {
            assertThat(title).isEqualTo("DEVELOP WITH CONFIDENCE");
        });
    }

}
