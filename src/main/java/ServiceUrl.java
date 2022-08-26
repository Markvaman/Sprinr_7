import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ServiceUrl {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";
    public RequestSpecification getBaseUrl() {
        return new RequestSpecBuilder().addHeader("Content type", "application/json").setBaseUri(BASE_URL).build();
    }
}
