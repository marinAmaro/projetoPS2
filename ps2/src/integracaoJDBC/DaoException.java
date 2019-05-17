package integracaoJDBC;

public class DaoException extends Exception {
	private String msg;
	public DaoException(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
}
