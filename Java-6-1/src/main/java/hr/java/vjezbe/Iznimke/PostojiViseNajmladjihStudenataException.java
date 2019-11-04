package hr.java.vjezbe.Iznimke;

public class PostojiViseNajmladjihStudenataException extends RuntimeException {
    private static final long serialVersionUID = 2711724378809456882L;

    public PostojiViseNajmladjihStudenataException() {
        super("Dogodila se pogreška u radu programa!");
    }

    public PostojiViseNajmladjihStudenataException(String message) {
        super(message);
    }


}
