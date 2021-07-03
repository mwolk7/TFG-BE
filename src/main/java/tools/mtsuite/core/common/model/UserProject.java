package tools.mtsuite.core.common.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.common.model.enums.Roles;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;

@Entity
@Table(name = AppConstants.USER_PROJECTS)
public class UserProject extends GenericObject {
	/****************
	 * Relationships**
	 *****************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private User user;

	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private Roles rol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Project project;
	/****************
	 * Attributes **
	 *****************/

	public UserProject() {
	}

	/****************
	 * Constructors **
	 *****************/

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}
}
