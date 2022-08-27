import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class LoginCourierErrorTests {
    private CourierCredentials courierCredentials;
    private CourierMethod courierMethod;
    private Courier courier;

    @Before
    public void setUp() {
        courierMethod = new CourierMethod();
        courier = new Courier("homer", "5325", "boyd");
        ValidatableResponse response = courierMethod.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_CREATED, statusCode);
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @Test
    public void courierCannotLoginWithoutPasswordTest() {
        courierCredentials = new CourierCredentials(courier.getLogin());
        ValidatableResponse response = courierMethod.login(courierCredentials);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_BAD_REQUEST, statusCode);
        String message = response.extract().path("message");
        assertEquals("Message is uncorrected", "Недостаточно данных для входа", message );
//падает с ошибкой 504, хотя все остальные тесты проходят, если подставить полные данные - тоже проходит, не понимаю, в чем дело
        //в Постман этот тест тоже проходит - не может же быть баг только тут?
    }
    @Test
    public void cannotLoginUnExistingAccountTest() {
        courierCredentials = new CourierCredentials("barny", "4587");
        ValidatableResponse response = courierMethod.login(courierCredentials);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_NOT_FOUND, statusCode);
        String message = response.extract().path("message");
        assertEquals("Message is incorrect", "Учетная запись не найдена", message);
    }

    @Test
    public void errorIfInputLoginWithMistakeTest() {
        courierCredentials = new CourierCredentials("gomer", "5325");
        ValidatableResponse response = courierMethod.login(courierCredentials);
        int statusCode = response.extract().statusCode();
        assertEquals("Status is invalid", SC_NOT_FOUND, statusCode);
    }
}
