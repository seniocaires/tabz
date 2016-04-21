package tabz.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * Classe de entidade das tabs.
 * @author Senio Caires
 */
@Entity(name = "Tab")
@Table(name = "tabs", schema = "tabz")
@SequenceGenerator(name="tabs_id_seq", sequenceName="tabz.tabs_id_seq", allocationSize=1)
public class Tab implements Serializable {

	/* ------------------------------
	 * CONSTANTES
	 * ------------------------------
	 */

	/**
	 * serialVersionUID gerado automaticamente.
	 * @author Senio Caires
	 */
	private static final long serialVersionUID = 1L;

	/* ------------------------------
	 * ATRIBUTOS
	 * ------------------------------
	 */

	/**
	 * @author Senio Caires
	 */
	private Long id;

	/**
	 * @author Senio Caires
	 */
	private String nome;

	/**
	 * @author Senio Caires
	 */
	private String link;

	/**
	 * @author Senio Caires
	 */
	private List<String> categorias;

	/**
	 * @author Senio Caires
	 */
	private Date atualizacao;

	/* ------------------------------
	 * CONSTRUTORES
	 * ------------------------------
	 */

	/**
	 * Construtor padrão.
	 * @author Senio Caires
	 */
	public Tab() {}

	/* ------------------------------
	 * GET / SET
	 * ------------------------------
	 */

	/**
	 * Retorna o id.
	 * @author Senio Caires
	 * @return {@link Long}
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tabs_id_seq")
	public Long getId() {
		return id;
	}

	/**
	 * Altera o id.
	 * @author Senio Caires
	 * @param id - {@link Long}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna o nome
	 * @author Senio Caires
	 * @return {@link String}
	 */
	@Column(nullable = false, length = 300)
	public String getNome() {
		return nome;
	}

	/**
	 * Altera o nome.
	 * @author Senio Caires
	 * @param nome - {@link String}
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna o link.
	 * @author Senio Caires
	 * @return {@link String}
	 */
	@Column(nullable = false, length = 800)
	public String getLink() {
		return link;
	}

	/**
	 * Altera o link.
	 * @author Senio Caires
	 * @param link - {@link String}
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Retorna a lista de categorias.
	 * @author Senio Caires
	 * @return {@link List}<{@link String}>
	 */
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER) /* MAPEAMENTO JPA */
	@CollectionTable(name = "categoriasTab", joinColumns = @JoinColumn(name = "idTab") , schema = "tabz") /* MAPEAMENTO JPA */
//	@CollectionOfElements(targetElement = String.class, fetch = FetchType.EAGER) /* MAPEAMENTO HIBERNATE */
//	@JoinTable(name = "categoriasTab", joinColumns = @JoinColumn(name = "idTab"), schema = "tabz") /* MAPEAMENTO HIBERNATE */
	@ForeignKey(name = "fk_tab")
	@Column(name = "categoria", nullable = false, length = 200)
	public final List<String> getCategorias() {

		if (categorias == null) {
			categorias = new ArrayList<String>();
		}

		return categorias;
	}

	/**
	 * Altera a lista de categorias.
	 * @author Senio Caires
	 * @param categorias {@link List}<{@link String}>
	 */
	public final void setCategorias(final List<String> categorias) {
		this.categorias = categorias;
	}

	/**
	 * Retorna a data de atualização.
	 * @author Senio Caires
	 * @return {@link Date}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAtualizacao() {

		if (atualizacao == null) {
			atualizacao = new Date();
		}

		return atualizacao;
	}

	/**
	 * Altera a data de atualização.
	 * @author Senio Caires
	 * @param atualizacao - {@link Date}
	 */
	public void setAtualizacao(Date atualizacao) {
		this.atualizacao = atualizacao;
	}

	/* ------------------------------
	 * PERSISTENCIA
	 * ------------------------------
	 */

	/**
	 * Atualiza antes de salvar no banco de dados.
	 * @author Senio Caires
	 */
	@PreUpdate
	@PrePersist
	@Transient
	public void atualizarDados() {
		setAtualizacao(new Date());
	}

	/* ------------------------------
	 * OUTROS
	 * ------------------------------
	 */

	/**
	 * Retorna a lista de categorias concatenadas com vírgula.
	 * @author Senio Caires
	 * @return {@link String}
	 */
	@Transient
	public String getCategoriasJuntas() {

		StringBuilder retorno = new StringBuilder("");

		for (String categoria : getCategorias()) {
			if (retorno.length() != 0) {
				retorno.append(",");
			}
			retorno.append(categoria);
		}

		return retorno.toString();
	}
}
