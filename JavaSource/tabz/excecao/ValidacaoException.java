package tabz.excecao;

/**
 * Classe base para criação das classes de exceção (Validação).
 * @author Senio Caires
 */
public class ValidacaoException extends Exception {

	/* ------------------------------
	 * CONSTANTES
	 * ------------------------------
	 */

	/**
	 * serialVersionUID gerado automaticamente.
	 * @author Senio Caires
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Mensagens.
	 * @author Senio Caires
	 */
	private Object[] mensagem;

	/* ------------------------------
	 * CONSTRUTORES
	 * ------------------------------
	 */

	/**
	 * Construtor padrão. <br/>
	 * Mensagem padrão da exceção: Ocorreu um erro na validação.
	 * @author Senio Caires
	 */
	public ValidacaoException() {
		super("Ocorreu um erro na validação.");
	}

	/**
	 * Construtor passando uma mensagem personalizada.
	 * @param mensagemParametro - Mensagem
	 * @author Senio Caires
	 */
	public ValidacaoException(final String mensagemParametro) {
		super(mensagemParametro);
	}

	/**
	 * Construtor passando uma mensagem personalizada.
	 * @param chave - Chave
	 * @param mensagemParametro - Mensagem
	 * @author Senio Caires
	 */
	public ValidacaoException(final String chave, final Object... mensagemParametro) {
		super(chave);
		this.mensagem = mensagemParametro;
	}

	/**
	 * Retorna as mensagens.
	 * @return {@link Object}[]
	 */
	public final Object[] getMensagem() {
		return mensagem;
	}
}
