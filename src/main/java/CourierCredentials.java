public class CourierCredentials {
    private String login;
    private String password;

    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials(String password) {
        this.password = password;
    }
    public static CourierCredentials fromTwo(Courier courier) {
        return new CourierCredentials(courier.getPassword());
    }


}
