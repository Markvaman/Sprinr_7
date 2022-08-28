import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderListMethod extends RestClient {
    @Step("Get order list")

    public ValidatableResponse getOrdersList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/api/v1/orders")
                .then();
    }
    }

