package br.com.serasa.pi.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users") 
public class UsuarioEntity implements UserDetails, Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotBlank
	@Column(name="matricula",unique=true)
	private String matricula;
	
	@Column(name = "nome")
	@NotBlank
	private String nome;
	
	@Column(name = "user_name")
	@NotBlank
	@Email
	private String username;	

	@NotBlank
	@Column(name = "password")
	private String password;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "enabled")
	private Boolean enabled;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.permissions;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username; 
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;		
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", 
	joinColumns = { @JoinColumn(name = "id_user") }
	, inverseJoinColumns = {@JoinColumn(name = "id_permission") })
	private List<Permission>permissions;
	
	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		for (Permission permission : this.permissions) {
			roles.add(permission.getDescription());
		}
		return roles;
	}


	@Override
	public int hashCode() {
		return Objects.hash(accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, matricula, nome,
				password, permissions, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioEntity other = (UsuarioEntity) obj;
		return Objects.equals(accountNonExpired, other.accountNonExpired)
				&& Objects.equals(accountNonLocked, other.accountNonLocked)
				&& Objects.equals(credentialsNonExpired, other.credentialsNonExpired)
				&& Objects.equals(enabled, other.enabled) && Objects.equals(matricula, other.matricula)
				&& Objects.equals(nome, other.nome) && Objects.equals(password, other.password)
				&& Objects.equals(permissions, other.permissions) && Objects.equals(username, other.username);
	}
}
