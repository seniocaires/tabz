package tabz.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import com.sporeon.baseutil.ConversaoUtil;

import tabz.entidade.Tab;
import tabz.excecao.ValidacaoException;
import tabz.util.Util;
import tabz.view.ItemTag;

/**
 * Classe de serviços para as tabs.
 * @author Senio Caires
 */
@Stateful(name = "TabService" + Util.CONSTANTE_SOFTWARE)
@Local(value = TabServiceLocal.class)
public class TabService implements TabServiceLocal {

	/* ------------------------------
	 * ATRIBUTOS
	 * ------------------------------
	 */

	/**
	 * Entity Manager.
	 * @author Senio Caires
	 */
	@PersistenceContext(unitName = Util.PERSISTENCE_UNIT, type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	/* ------------------------------
	 * AÇÕES
	 * ------------------------------
	 */

	/**
	 * Salva no banco de dados.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 * @throws ValidacaoException {@link ValidacaoException}
	 */
	public void salvar(Tab tab) throws ValidacaoException {

		validarAntesSalvar(tab);

		if (tab.getId() == null) {
			entityManager.persist(tab);
		} else {
			entityManager.merge(tab);
		}
	}

	/**
	 * Remove do banco de dados.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 * @throws ValidacaoException {@link ValidacaoException}
	 */
	public void remover(Tab tab) throws ValidacaoException {

		validarAntesRemover(tab);

		Tab tabParaSerRemovida = entityManager.getReference(Tab.class, tab.getId());
		entityManager.remove(tabParaSerRemovida);
	}

	/**
	 * Atualiza de acordo com os dados do banco.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 */
	public void atualizar(Tab tab) {
		entityManager.refresh(tab);
	}

	/* ------------------------------
	 * CONSULTAS
	 * ------------------------------
	 */

	/**
	 * Retorna a tab de acordo com o id passado por parâmetro.
	 * @author Senio Caires
	 * @param id {@link Long}
	 * @return {@link Tab}
	 */
	public Tab carregarPorId(final Long id) {

		Tab retorno = null;

		Query query = entityManager.createQuery("SELECT tab FROM Tab tab WHERE tab.id = :id");

		query.setParameter("id", id);

		try {
			retorno = (Tab) query.getSingleResult();
		} catch (NoResultException nre) {
			retorno = null;
		}

		return retorno;
	}

	/**
	 * Retorna a lista de tabs cujo nome ou alguma categoria seja coincidente com o parâmetro passado.
	 * @author Senio Caires
	 * @param nomeOuCategoria {@link String}
	 * @return {@link List}<{@link Tab}>
	 */
	@SuppressWarnings("unchecked")
	public List<Tab> listarPorNomeOuCategoria(final String nomeOuCategoria) {

		Query query = entityManager.createQuery("SELECT DISTINCT tab FROM Tab tab LEFT JOIN tab.categorias categoria WHERE LOWER(tab.nome) LIKE :nome OR LOWER(categoria) LIKE :categoria ORDER BY tab.atualizacao DESC");

		query.setParameter("nome", "%" + ConversaoUtil.nuloParaVazio(nomeOuCategoria).toLowerCase() + "%");
		query.setParameter("categoria", "%" + ConversaoUtil.nuloParaVazio(nomeOuCategoria).toLowerCase() + "%");

		return (List<Tab>) query.getResultList();
	}

	/**
	 * Retorna a lista de itens para tag cloud.
	 * @author Senio Caires
	 */
	@SuppressWarnings("unchecked")
	public List<ItemTag> listaTags() {

		List<ItemTag> retorno = new ArrayList<ItemTag>();

		Query query = entityManager.createNativeQuery("SELECT COUNT(categoriaTab.categoria) AS quantidade, categoriaTab.categoria AS tag FROM tabz.categoriasTab categoriaTab GROUP BY categoriaTab.categoria");

		for (Object[] object : (List<Object[]>) query.getResultList()) {
			retorno.add(new ItemTag(Integer.valueOf(object[0].toString()), String.valueOf(object[1])));
		}

		return retorno;
	}

	/* ------------------------------
	 * VALIDAÇÕES
	 * ------------------------------
	 */

	/**
	 * Valida antes de salvar.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 * @throws ValidacaoException {@link ValidacaoException}
	 */
	private void validarAntesSalvar(final Tab tab) throws ValidacaoException { }

	/**
	 * Valida antes de excluir.
	 * @author Senio Caires
	 * @param tab {@link Tab}
	 * @throws ValidacaoException {@link ValidacaoException}
	 */
	private void validarAntesRemover(final Tab tab) throws ValidacaoException { }
}
