package tabz.service;

import java.util.List;

import tabz.entidade.Tab;
import tabz.excecao.ValidacaoException;
import tabz.view.ItemTag;

/**
 * Interface de serviços para as tabs.
 * @author Senio Caires
 */
public interface TabServiceLocal {

	/**
	 * Salva no banco de dados.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 * @throws ValidacaoException {@link ValidacaoException}
	 */
	void salvar(Tab tab) throws ValidacaoException;

	/**
	 * Remove do banco de dados.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 * @throws ValidacaoException {@link ValidacaoException}
	 */
	void remover(Tab tab) throws ValidacaoException;

	/**
	 * Atualiza de acordo com os dados do banco.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 */
	void atualizar(Tab tab);

	/**
	 * Retorna a tab de acordo com o id passado por parâmetro.
	 * @author Senio Caires
	 * @param id {@link Long}
	 * @return {@link Tab}
	 */
	Tab carregarPorId(final Long id);

	/**
	 * Retorna a lista de tabs cujo nome ou alguma categoria seja coincidente com o parâmetro passado.
	 * @author Senio Caires
	 * @param nomeOuCategoria {@link String}
	 * @return {@link List}<{@link Tab}>
	 */
	List<Tab> listarPorNomeOuCategoria(final String nomeOuCategoria);

	/**
	 * Retorna a lista de itens para tag cloud.
	 * @author Senio Caires
	 */
	List<ItemTag> listaTags();
}
