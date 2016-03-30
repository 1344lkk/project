package com.hzcwtech.wuzhong.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;;

public class GrantedRole implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String role;
	
	private final String name;
	
	public GrantedRole(String code, String name) {
		Assert.hasText(code, "A granted authority textual representation is required");
		this.role = code;
		this.name = name;
	}
	
	@Override
	public String getAuthority() {
		return role;
	}

	public String getName() {
		return name;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof GrantedRole) {
			return role.equals(((GrantedRole) obj).role);
		}

		return false;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	public String toString() {
		return this.role;
	}
}
