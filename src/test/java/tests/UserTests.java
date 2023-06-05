package tests;

import io.qameta.allure.Owner;
import models.CreateBodyModel;
import models.CreateResponseModel;
import models.GetUserDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.Specs;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static specs.Specs.reqresRequest;

public class UserTests {
    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Проверка наличия выбранного email")
    void checkSingleEmailLombok() {
        GetUserDataModel data = step("Make request", () ->
                given(reqresRequest)
                        .when()
                        .get("/users/2")
                        .then()
                        .log().body()
                        .spec(Specs.response)
                        .extract().as(GetUserDataModel.class));
        step("Verify response", () ->
                assertThat(data.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in"));
    }

    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Проверка наличия выбранного наименования цвета")
    void getNameSingleResourceWithGroovy() {
        step("Make request", () ->
                given(reqresRequest)
                        .when()
                        .get("/unknown")
                        .then()
                        .log().body()
                        .spec(Specs.response)
                        .body("data.findAll{it.name =~/./}.name.flatten()",
                                hasItem("fuchsia rose")));
    }

    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Проверка данных пользователя")
    void checkSingleNameLombok() {
        GetUserDataModel data = step("Make request", () ->
                given(reqresRequest)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(Specs.response)
                        .log().body()
                        .extract().as(GetUserDataModel.class));

        step("Verify response: first name", () -> assertThat(data.getUser().getFirstName()).isEqualTo("Janet"));
        step("Verify response: last name", () -> assertThat(data.getUser().getLastName()).isEqualTo("Weaver"));
    }

    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Создание нового пользователя")
    void createUserWithPojo() {
        CreateBodyModel createBody = new CreateBodyModel();
        createBody.setName("Adel");
        createBody.setJob("QA engineer");
        CreateResponseModel createResponse = step("Make request", () ->
                given(reqresRequest)
                        .body(createBody)
                        .when()
                        .post("/users")
                        .then()
                        .log().body()
                        .spec(Specs.responseCreate)
                        .extract().as(CreateResponseModel.class));

        step("Verify response: name", () -> assertThat(createResponse.getName()).isEqualTo("Adel"));
        step("Verify response: job", () -> assertThat(createResponse.getJob()).isEqualTo("QA engineer"));
    }

    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Проверка отсутствия ресурса (Negative)")
    void singleResourceNotFound() {
        step("Make request", () ->
                given(reqresRequest)
                        .when()
                        .get("/unknown/23")
                        .then()
                        .log().body()
                        .spec(Specs.responseNotFound));
    }

    @Test
    @Owner("Курышева Адэль")
    @DisplayName("Удаление данных")
    void deleteUser() {
        step("Make request", () ->
                given(reqresRequest)
                        .when()
                        .delete("/users/2")
                        .then()
                        .log().body()
                        .spec(Specs.responseDelete));
    }
}
