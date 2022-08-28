import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private Order order;
    private OrderCreateMethod orderMethod;
    private String[]colour;

    public OrderCreateTest(String[] colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{}}
        };
    }
    @Before
    public void setUp() {
        order = new Order("name", "surname", "address", "metro", "3698521478", 2, "12.09.2022","comment", colour);
        orderMethod = new OrderCreateMethod();
    }


    @Test
    @DisplayName("Create order with any scooter colour")
    @Description("Send post to /api/v1/orders with list of colour params")
   public void orderCanBeCreateWithAnyParamColourTest() {
        ValidatableResponse response = orderMethod.createOrder(order);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_CREATED, statusCode);
        int track = response.extract().path("track");
        assertTrue("Track is not created", track > 0);


   }

}
