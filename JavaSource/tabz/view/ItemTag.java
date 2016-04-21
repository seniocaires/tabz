package tabz.view;

/**
 * Classe para representar os itens da tag cloud.
 * @author Senio Caires
 */
public class ItemTag {

	/* ------------------------------
	 * ATRIBUTOS
	 * ------------------------------
	 */

	/**
	 * @author Senio Caires
	 */
	private Integer quantidade;

	/**
	 * @author Senio Caires
	 */
	private String tag;

	/* ------------------------------
	 * CONSTRUTORES
	 * ------------------------------
	 */

	/**
	 * Construtor.
	 * @author Senio Caires
	 * @param quantidade - {@link Integer}
	 * @param tag - {@link String}
	 */
	public ItemTag(Integer quantidade, String tag) {
		this.quantidade = quantidade;
		this.tag = tag;
	}

	/* ------------------------------
	 * GET / SET
	 * ------------------------------
	 */

	/**
	 * Retorna a quantidade.
	 * @author Senio Caires
	 * @return {@link Integer}
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * Altera a quantidade.
	 * @author Senio Caires
	 * @param quantidade - {@link Integer}
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * Retorna a tag.
	 * @author Senio Caires
	 * @return {@link String}
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Altera a tag.
	 * @author Senio Caires
	 * @param tag - {@link String}
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/* ------------------------------
	 * OUTROS
	 * ------------------------------
	 */

	/**
	 * Retorna a quantidade de acordo com o css do primefaces.
	 * @author Senio Caires
	 * @return {@link Integer}
	 */
	public Integer getQuantidadeTag() {

		Integer resultado = quantidade / 5;
		Integer modulo = quantidade % 5;
		Integer retorno = resultado + modulo;

		if (retorno > 5) {
			return 5;
		} else {
			return retorno;
		}
	}
}
