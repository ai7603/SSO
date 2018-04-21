package domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class SessionStorage {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable=false)
	private String st;
	@Column(nullable=false)
	private String sessionId;
	@Column(length=1000,nullable=false)
	private String localService;
	@Column(nullable=false)
	private Timestamp created=new Timestamp(System.currentTimeMillis());
	
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getLocalService() {
		return localService;
	}
	public void setLocalService(String localService) {
		this.localService = localService;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((localService == null) ? 0 : localService.hashCode());
		result = prime * result + ((st == null) ? 0 : st.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SessionStorage other = (SessionStorage) obj;
		if (localService == null) {
			if (other.localService != null)
				return false;
		} else if (!localService.equals(other.localService))
			return false;
		if (st == null) {
			if (other.st != null)
				return false;
		} else if (!st.equals(other.st))
			return false;
		return true;
	}
	
}
