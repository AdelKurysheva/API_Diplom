package tests;

import io.qameta.allure.Owner;
import models.LoginBodyModel;
import models.LoginErrorResponseModel;
import models.RegisterErrorResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.reqresRequest;
import static specs.Specs.responseFailed;

public class AuthTest {
    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Проверка валидации логина (Negative)")
    void loginUnsuccessful() {
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("peter@klaven");

        LoginErrorResponseModel responseBody = step("Make request", () ->
                given(reqresRequest)
                        .body(data)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseFailed)
                        .extract().as(LoginErrorResponseModel.class));

        step("Verify response", () -> {
            assertThat(responseBody.getError()).isEqualTo("Missing password");
        });
    }

    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Проверка валидации пароля (Negative)")
    void loginUnsuccessfulWithPojo() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("sydney@fife");
        RegisterErrorResponseModel responseBody = step("Make request", () ->
                given(reqresRequest)
                        .body(loginBody)
                        .when()
                        .post("/register")
                        .then()
                        .log().body()
                        .spec(responseFailed)
                        .extract().as(RegisterErrorResponseModel.class));

        step("Verify response", () ->
                assertThat(responseBody.getError()).isEqualTo("Missing password"));
    }
}

