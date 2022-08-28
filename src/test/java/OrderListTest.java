import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderListTest {
private OrderListMethod orderListMethod;

@Before
    public void setUp() {
    orderListMethod = new OrderListMethod();
}

@Test
    @DisplayName("Get orders list")
    @Description("Send get to /api/v1/orders, status 200")
    public void getOrdersListTest() {
    ValidatableResponse response = orderListMethod.getOrdersList();
    int statusCode = response.extract().statusCode();
    assertEquals("Status code is not valid", SC_OK, statusCode);
    ArrayList<String> ordersList = response.extract().path("orders");
    assertNotEquals("List is empty", Collections.EMPTY_LIST, ordersList);
}

}
