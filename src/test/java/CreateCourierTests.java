import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests {

    private Courier courier;
    private CourierMethod courierMethod;
    private int courierId;

    @Before
    public void setUp() {
        courierMethod = new CourierMethod();
    }


    @Test
    @DisplayName("Courier account create successfully")
    @Description("Send post to /api/v1/courier, status code 201 created")
     public void courierAccountCanBeCreatedTest() {
        courier = new Courier("linda", "5325", "boyd");
        ValidatableResponse response = courierMethod.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isCreated);

        ValidatableResponse loginResponse = courierMethod.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status is invalid", SC_OK, loginStatusCode );
        courierId = loginResponse.extract().path("id");
        assertTrue("Courier Id is invalid", courierId > 0);
        courierMethod.delete(courierId);


    }
    @Test
    @DisplayName("Accounts with two same logins doesn't created")
    @Description("Send post twice with same data to /api/v1/courier, status code 409")
    public void cannotBeCreatedTwoAccountsWithSameLoginsTest() {
        courier = new Courier("karla", "5325", "boyd");
        ValidatableResponse response = courierMethod.create(courier);
        int statusCode= response.extract().statusCode();
        assertEquals("Status code is invalid", SC_CREATED, statusCode);
        ValidatableResponse responseSecond = courierMethod.create(courier);
        int statusCodeSecond = responseSecond.extract().statusCode();
        assertEquals("Status code is invalid", SC_CONFLICT, statusCodeSecond);

        ValidatableResponse loginResponse = courierMethod.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status is invalid", SC_OK, loginStatusCode );
        courierId = loginResponse.extract().path("id");
        assertTrue("Courier Id is invalid", courierId > 0);
        courierMethod.delete(courierId);
//код ошибки приходит верный, но текст сообщения не совпадает
    }

    @Test
    @DisplayName("All credentials should be for creating account")
    @Description("Send post to /api/v1/courier only with credentials, status code 201")
    public void courierWithCredentialsCanBeCreatedTest() {
        courier = new Courier("linda", "5325", "boyd");
        courier = Courier.from(courier);
        ValidatableResponse response = courierMethod.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isCreated);

        ValidatableResponse loginResponse = courierMethod.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status is invalid", SC_OK, loginStatusCode );
        courierId = loginResponse.extract().path("id");
        assertTrue("Courier Id is invalid", courierId > 0);
        courierMethod.delete(courierId);

    }

    @Test
    @DisplayName("Courier without password cannot be created")
    @Description("Send post only with login to /api/v1/courier, status code 400")
    public void courierWithoutPasswordCannotBeCreatedTest() {
        courier = new Courier("linda", "5325", "boyd");
        courier = Courier.fromTwo(courier);
        ValidatableResponse response = courierMethod.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_BAD_REQUEST,statusCode);
        String message = response.extract().path("message");
        assertEquals("Message is not correct", "Недостаточно данных для создания учетной записи", message);
    }



    }

