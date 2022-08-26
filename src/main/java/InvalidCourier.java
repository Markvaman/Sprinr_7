public class InvalidCourier {
    private String login;

    public InvalidCourier(String login) {
        this.login = login;
    }

    public static InvalidCourier from(Courier courier) {
        return new InvalidCourier(courier.getLogin());
    }
}
