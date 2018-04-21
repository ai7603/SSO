package domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Mapping {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(optional=false)
	private User casUser;
	@Column(nullable=false)
	private String localUser;
	@Column(nullable=false)
	private String host;
	@Column(nullable=false)
	private String app;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getCasUser() {
		return casUser;
	}
	public void setCasUser(User casUser) {
		this.casUser = casUser;
	}
	public String getLocalUser() {
		return localUser;
	}
	public void setLocalUser(String localUser) {
		this.localUser = localUser;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
}
