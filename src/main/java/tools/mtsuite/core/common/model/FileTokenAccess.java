package tools.mtsuite.core.common.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = AppConstants.TOKEN_FILE)
public class FileTokenAccess extends GenericObject {
	/****************
	 * Relationships**
	 *****************/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private User user;


	/****************
	 * Attributes **
	 *****************/

	@Column(name = "TOKEN")
	private String token;


	public FileTokenAccess() {
	}

	/*****************
	 * Getter & Setters**
	 *****************/

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
