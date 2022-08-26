

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class CourierMethod extends ServiceUrl{
    @Step("Create new courier account")
public ValidatableResponse create(Courier courier) {
    return
        given()
            .spec(getBaseUrl())
            .and()
            .body(courier)
            .when()
            .post("/api/v1/courier")
            .then();
}
     @Step("Login courier with login and password")
public ValidatableResponse login(CourierCredentials courierCredentials) {
        return
        given()
            .spec(getBaseUrl())
            .and()
            .body(courierCredentials)
            .when()
            .post("/api/v1/courier/login")
            .then();
}

    @Step("Delete creating courier")
    public ValidatableResponse delete(int id) {
        return
                given()
                        .spec(getBaseUrl())
                        .and()
                        .pathParam("id", id)
                        .when()
                        .post("/api/v1/courier" + "{id}")
                        .then();
    }

}

