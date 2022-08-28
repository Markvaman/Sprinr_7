import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethod extends RestClient {
    @Step("Create new order")

    public ValidatableResponse createOrder(Order order) {
        return
                given()
                        .spec(getBaseSpec())
                        .body(order)
                        .when()
                        .post("/api/v1/orders")
                        .then();
    }

}
