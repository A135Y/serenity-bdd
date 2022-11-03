package serenityMain.authentication;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;

public class LoginAction extends UIInteractionSteps {

    @Step("Log in as {0}")
    public void as(Users users) {
    openUrl("https://www.saucedemo.com/");

    find("[data-test='username']").sendKeys(users.getUsername());
    find("[data-test='password']").sendKeys(users.getPassword());
    find("[data-test='login-button']").click();
    }



}
