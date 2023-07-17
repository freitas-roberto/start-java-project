package br.eti.freitas.startproject.infrastructure.custom;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomPermissionEvaluator implements PermissionEvaluator {
	private static final Logger LOG = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

	@Override
	public boolean hasPermission( Authentication authentication
								, Object targetDomainObject
								, Object permission) {
		LOG.info("CustomPermissionEvaluator.hasPermission: authentication={}, targetDomainObject={}, permission={}", authentication, targetDomainObject, permission);
		if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)) {
			LOG.error("CustomPermissionEvaluator.hasPermission: authentication={}, targetDomainObject={}, permission={}", authentication, targetDomainObject, permission);
			return false;
		}
		return hasPrivilege(authentication, targetDomainObject.toString().toLowerCase(), permission.toString().toLowerCase());
	}

	@Override
	public boolean hasPermission( Authentication authentication
			                    , Serializable targetId
			                    , String resource
			                    , Object permission) {
		LOG.info("CustomPermissionEvaluator.hasPermission: authentication={}, targetId={}, resource={}, permission={}", authentication, targetId, resource, permission);
		if ((authentication == null) || (resource == null) || !(permission instanceof String)) {
			LOG.error("CustomPermissionEvaluator.hasPermission: authentication={}, targetId={}, resource={}, permission={}", authentication, targetId, resource, permission);
			return false;
		}
		return hasPrivilege(authentication, resource.toLowerCase(), permission.toString().toLowerCase());
	}

	private boolean hasPrivilege(Authentication authentication, String resource, String permission) {
		LOG.info("CustomPermissionEvaluator.hasPrivilege: authentication={}, resource={}, permission={}", authentication, resource, permission);
		for (final GrantedAuthority grantedAuth : authentication.getAuthorities()) {
			if (grantedAuth.getAuthority().trim().startsWith(permission) && grantedAuth.getAuthority().contains(resource)) {
				return true;
			}
		}
		LOG.error("CustomPermissionEvaluator.hasPrivilege: authentication={}, resource={}, permission={}, resource={}", authentication, resource, permission);
		return false;
	}
}