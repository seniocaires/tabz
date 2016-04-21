package tabz.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

import com.sporeon.baseutil.ConversaoUtil;
import com.sporeon.facesutil.util.JSFHelperBootstrap;

import tabz.entidade.Tab;
import tabz.excecao.ValidacaoException;
import tabz.service.TabServiceLocal;
import tabz.view.ItemTag;

/**
 * Managed bean da aplicação.
 * @author Senio Caires
 */
@ManagedBean(name = "tabMB")
@ViewScoped
public class TabManagedBean {

	/* ------------------------------
	 * ATRIBUTOS
	 * ------------------------------
	 */

	/**
	 * @author Senio Caires
	 */
	private Tab tab;

	/**
	 * @author Senio Caires
	 */
	private List<Tab> tabs;

	/**
	 * @author Senio Caires
	 */
	private String campoPesquisa;

	/**
	 * @author Senio Caires
	 */
	private String categoriasAdicionar;

	/**
	 * @author Senio Caires
	 */
	private TagCloudModel tagCloud;

	/* ------------------------------
	 * EJB
	 * ------------------------------
	 */

	/**
	 * @author Senio Caires
	 */
	@EJB
	private TabServiceLocal tabService;

	/* ------------------------------
	 * CONSTRUTORES
	 * ------------------------------
	 */

	/**
	 * Construtor padrão.
	 * @author Senio Caires
	 */
	public TabManagedBean() { }

	/* ------------------------------
	 * AÇÕES
	 * ------------------------------
	 */

	/**
	 * Filtrar a lista de tabs.
	 * @author Senio Caires
	 */
	public void pesquisar() {

		setTabs(tabService.listarPorNomeOuCategoria(ConversaoUtil.nuloParaVazio(getCampoPesquisa()).trim()));

		if (getTabs().isEmpty()) {
			JSFHelperBootstrap.addGlobalMessageInfo("", "Nenhum registro encontrado.");
		}
	}

	/**
	 * Criar uma nova tab.
	 * @author Senio Caires
	 */
	public final void nova() {
		setTab(new Tab());
		setCategoriasAdicionar("");
	}

	/**
	 * Editar a tab selecionada no resultado da pesquisa.
	 * @author Senio Caires
	 * @param tabSelecionada - {@link Tab}
	 */
	public void editar(Tab tabSelecionada) {
		setTab(tabService.carregarPorId(tabSelecionada.getId()));
		setCategoriasAdicionar("");
	}

	/**
	 * Salvar a tab.
	 * @author Senio Caires
	 */
	public final void salvar() {

		try {

			tabService.salvar(getTab());

			setCategoriasAdicionar("");

			pesquisar();

		} catch (ValidacaoException validacaoException) {

			JSFHelperBootstrap.addGlobalMessageError("", validacaoException.getMessage());

			return;
		}

		JSFHelperBootstrap.addGlobalMessageSucess("", "Registro salvo com sucesso.");
	}

	/**
	 * Remover a tab.
	 * @author Senio Caires
	 */
	public final void excluir() {

		try {

			tabService.remover(getTab());

			nova();

			pesquisar();

		} catch (ValidacaoException ve) {

			JSFHelperBootstrap.addGlobalMessageError("", ve.getMessage());

			return;
		}

		JSFHelperBootstrap.addGlobalMessageSucess("", "Registro excluído com sucesso.");
	}

	/**
	 * Quebrar as categorias por vírgula e adicionar na tab.
	 * @author Senio Caires
	 */
	public void adicionarCategorias() {

		for (String categoria : ConversaoUtil.nuloParaVazio(getCategoriasAdicionar()).split(",")) {
			if (!getTab().getCategorias().contains(categoria.trim())) {
				getTab().getCategorias().add(categoria.trim());
			}
		}

		setCategoriasAdicionar("");
	}

	/**
	 * Remover a categoria selecionada da tab.
	 * @author Senio Caires
	 * @param categoriaSelecionada - {@link String}
	 */
	public void removerCategoria(String categoriaSelecionada) {

		getTab().getCategorias().remove(categoriaSelecionada);

		if (getTab().getId() != null) {
			try {
				tabService.salvar(getTab());
			} catch (ValidacaoException validacaoException) {
				JSFHelperBootstrap.addGlobalMessageError("", validacaoException.getMessage());
			}
		}
	}

	/**
	 * Evento ao selecionar um item da tag cloud.
	 * @param event - {@link SelectEvent}
	 */
	public void onSelect(SelectEvent event) {
		TagCloudItem item = (TagCloudItem) event.getObject();
		setCampoPesquisa(item.getLabel());
		pesquisar();
	}

	/* ------------------------------
	 * EXIBIÇÃO
	 * ------------------------------
	 */

	/**
	 * Informa se é para exibir o resultado da pesquisa.
	 * @author Senio Caires
	 * @return boolean
	 */
	public final boolean getExibirResultadoPesquisa() {
		return !getTabs().isEmpty();
	}

	/**
	 * Informa se é para exibir as categorias.
	 * @author Senio Caires
	 * @return boolean
	 */
	public final boolean getExibirCategorias() {
		return !getTab().getCategorias().isEmpty();
	}

	/* ------------------------------
	 * GET / SET
	 * ------------------------------
	 */

	/**
	 * Retorna a tab.
	 * @author Senio Caires
	 * @return {@link Tab}
	 */
	public final Tab getTab() {

		if (tab == null) {
			tab = new Tab();
		}

		return tab;
	}

	/**
	 * Altera a tab.
	 * @author Senio Caires
	 * @param tabParametro - {@link Tab}
	 */
	public final void setTab(final Tab tabParametro) {
		this.tab = tabParametro;
	}

	/**
	 * Retorna a lista de tabs.
	 * @author Senio Caires
	 * @return @{@link List}<{@link Tab}>
	 */
	public List<Tab> getTabs() {

		if (tabs == null) {
			tabs = new ArrayList<Tab>();
		}

		return tabs;
	}

	/**
	 * Altera a lista de tabs.
	 * @param tabs - @{@link List}<{@link Tab}>
	 * @author Senio Caires
	 */
	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	/**
	 * Retorna o campo de pesquisa.
	 * @author Senio Caires
	 * @return {@link String}
	 */
	public String getCampoPesquisa() {
		return campoPesquisa;
	}

	/**
	 * Altera o campo de pesquisa.
	 * @param campoPesquisa - {@link String}
	 * @author Senio Caires
	 */
	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}

	/**
	 * Retorna as categorias para adicionar.
	 * @author Senio Caires
	 * @return {@link String}
	 */
	public String getCategoriasAdicionar() {
		return categoriasAdicionar;
	}

	/**
	 * Altera as categorias para adicionar.
	 * @author Senio Caires
	 * @param categoriasAdicionar - {@link String}
	 */
	public void setCategoriasAdicionar(String categoriasAdicionar) {
		this.categoriasAdicionar = categoriasAdicionar;
	}

	/**
	 * Retorna a tag cloud.
	 * @author Senio Caires
	 * @return {@link TagCloudModel}
	 */
	public TagCloudModel getTagCloud() {

		tagCloud = new DefaultTagCloudModel();

		for (ItemTag itemTag : tabService.listaTags()) {
			tagCloud.addTag(new DefaultTagCloudItem(itemTag.getTag(), itemTag.getQuantidadeTag()));
		}

		return tagCloud;
	}
}
