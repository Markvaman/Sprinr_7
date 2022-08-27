import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginCourierOkTests {
    private CourierCredentials courierCredentials;
    private CourierMethod courierMethod;
    private int courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierMethod = new CourierMethod();
        courier = new Courier("jack", "5325", "boyd");
        ValidatableResponse response = courierMethod.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_CREATED, statusCode);
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }
    @After
    public void deleteCreatedCourier(){
        courierMethod.delete(courierId);
    }

    @Test
    @DisplayName("Courier can login")
    @Description("Send post to api/v1/courier/login, status 200 ok")
    public void courierBeAbleToLoginTest() {
        ValidatableResponse response = courierMethod.login(courierCredentials);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_OK, statusCode);
        courierId = response.extract().path("id");
        assertTrue("Courier id is not created", courierId > 0);
    }





}
