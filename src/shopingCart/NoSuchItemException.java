package shopingCart;

public class NoSuchItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchItemException(String message) {
		super(message);
	}

}
