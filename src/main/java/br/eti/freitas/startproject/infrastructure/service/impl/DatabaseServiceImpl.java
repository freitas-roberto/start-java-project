package br.eti.freitas.startproject.infrastructure.service.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eti.freitas.startproject.dto.OrganizationDto;
import br.eti.freitas.startproject.infrastructure.constant.SecurityConstant;
import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;
import br.eti.freitas.startproject.infrastructure.dto.RoleDto;
import br.eti.freitas.startproject.infrastructure.dto.UserDto;
import br.eti.freitas.startproject.infrastructure.service.DatabaseService;
import br.eti.freitas.startproject.infrastructure.service.PrivilegeService;
import br.eti.freitas.startproject.infrastructure.service.RoleService;
import br.eti.freitas.startproject.infrastructure.service.UserService;
import br.eti.freitas.startproject.service.OrganizationService;

@Service
@Transactional
public class DatabaseServiceImpl implements DatabaseService {

	private static final Logger LOG = LoggerFactory.getLogger(DatabaseServiceImpl.class);

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PrivilegeService privilegeService;

	private static String roleSystem = SecurityConstant.SECURITY_JWT_DEFAULT_ROLE_PREFIX + "SYSTEM";
	private static String roleManager = SecurityConstant.SECURITY_JWT_DEFAULT_ROLE_PREFIX + "MANAGER";
	private static String roleUser = SecurityConstant.SECURITY_JWT_DEFAULT_ROLE_PREFIX + "USER";
	
	public void uploadOthersDatabases() {

		LOG.info("Start data load on 'NON PRODUCTIVE' Database.");

		try {
			forOthersEnvironments();
		} catch (Exception ex) {
			LOG.info("Failure of environment data load! {}", ex);			
		}
	}
	
	public void uploadProductionDatabase() {
		LOG.info("Start data load on 'PRODUCTIVE' Database.");
		try {
			forProductionEnvironment();
		} catch (Exception ex) {
			LOG.info("Failure of environment data load! {}", ex);			
		}
	}

	private void forOthersEnvironments() {

		LOG.info("Add Organizations...");
		OrganizationDto org1 = new OrganizationDto();
		org1.setName("Empresa Teste 1");
		org1.setEmail("empresa1@teste.com.br");
		org1.setOrganizationKey(UUID.randomUUID());
		org1.setEnabled(true);
		organizationService.createOrganization(org1);
		
		OrganizationDto org2 = new OrganizationDto();
		org2.setName("Empresa Teste 2");
		org2.setEmail("empresa2@teste.com.br");
		org2.setOrganizationKey(UUID.randomUUID());
		org2.setEnabled(true);
		organizationService.createOrganization(org2);		
		
		LOG.info("Add Users...");
		UserDto user1 = new UserDto();
		user1.setName("System User Administrator");
		user1.setEmail("sys@freitas.eti.br");
		user1.setUsername("sys");
		user1.setPassword("1234");
		userService.createUser(user1);
		
		UserDto user2 = new UserDto();
		user2.setName("Operator User");
		user2.setEmail("operator@freitas.eti.br");
		user2.setUsername("operator");
		user2.setPassword("1234");
		userService.createUser(user2);

		UserDto user3 = new UserDto();
		user3.setName("Basic User");
		user3.setEmail("user@freitas.eti.br");
		user3.setUsername("user");
		user3.setPassword("1234");
		userService.createUser(user3);

		LOG.info("Add Roles...");
		RoleDto role1 = new RoleDto();
		role1.setName(roleSystem);
		role1.setDescription("Role for system administrator");
		role1.setEnabled(true);
		roleService.createRole(role1);
		
		RoleDto role2 = new RoleDto();
		role2.setName(roleManager);
		role2.setEnabled(true);
		roleService.createRole(role2);
		
		RoleDto role3 = new RoleDto();		
		role3.setName(roleUser);
		role3.setEnabled(true);
		roleService.createRole(role3);
		
		roleService.addRoleToUser("sys", roleSystem);
		roleService.addRoleToUser("operator", roleManager);
		roleService.addRoleToUser("user", roleUser);

		LOG.info("Add Privileges for Organization...");
		PrivilegeDto privilegeOrganization1 = new PrivilegeDto();
		privilegeOrganization1.setResource("organization");
		privilegeOrganization1.setType("read");
		privilegeOrganization1.setEnabled(true);
		privilegeService.createPrivilege(privilegeOrganization1);

		PrivilegeDto privilegeOrganization2 = new PrivilegeDto();
		privilegeOrganization2.setResource("organization");
		privilegeOrganization2.setType("write");
		privilegeOrganization2.setEnabled(true);
		privilegeService.createPrivilege(privilegeOrganization2);

		PrivilegeDto privilegeOrganization3 = new PrivilegeDto();
		privilegeOrganization3.setResource("organization");
		privilegeOrganization3.setType("delete");
		privilegeOrganization3.setEnabled(true);
		privilegeService.createPrivilege(privilegeOrganization3);
		
		LOG.info("Add Privileges for User...");
		PrivilegeDto privilegeUser1 = new PrivilegeDto();
		privilegeUser1.setResource("user");
		privilegeUser1.setType("read");
		privilegeUser1.setEnabled(true);
		privilegeService.createPrivilege(privilegeUser1);

		PrivilegeDto privilegeUser2 = new PrivilegeDto();
		privilegeUser2.setResource("user");
		privilegeUser2.setType("write");
		privilegeUser2.setEnabled(true);
		privilegeService.createPrivilege(privilegeUser2);

		PrivilegeDto privilegeUser3 = new PrivilegeDto();
		privilegeUser3.setResource("user");
		privilegeUser3.setType("delete");
		privilegeUser3.setEnabled(true);
		privilegeService.createPrivilege(privilegeUser3);
		
		LOG.info("Add Privileges for Role...");
		PrivilegeDto privilegeRole1 = new PrivilegeDto();
		privilegeRole1.setResource("role");
		privilegeRole1.setType("read");
		privilegeRole1.setEnabled(true);
		privilegeService.createPrivilege(privilegeRole1);

		PrivilegeDto privilegeRole2 = new PrivilegeDto();
		privilegeRole2.setResource("role");
		privilegeRole2.setType("write");
		privilegeRole2.setEnabled(true);
		privilegeService.createPrivilege(privilegeRole2);

		PrivilegeDto privilegeRole3 = new PrivilegeDto();
		privilegeRole3.setResource("role");
		privilegeRole3.setType("delete");
		privilegeRole3.setEnabled(true);
		privilegeService.createPrivilege(privilegeRole3);
	
		LOG.info("Add Privileges for Privileges...");
		PrivilegeDto privilegePrivilege1 = new PrivilegeDto();
		privilegePrivilege1.setResource("privilege");
		privilegePrivilege1.setType("read");
		privilegePrivilege1.setEnabled(true);
		privilegeService.createPrivilege(privilegePrivilege1);

		PrivilegeDto privilegePrivilege2 = new PrivilegeDto();
		privilegePrivilege2.setResource("privilege");
		privilegePrivilege2.setType("write");
		privilegePrivilege2.setEnabled(true);
		privilegeService.createPrivilege(privilegePrivilege2);

		LOG.info("Add Privileges for ROLE Manager...");
		privilegeService.addPrivilegeToRole(roleManager, privilegeUser1.getResource(), privilegeUser1.getType());
		privilegeService.addPrivilegeToRole(roleManager, privilegeUser2.getResource(), privilegeUser2.getType());
		privilegeService.addPrivilegeToRole(roleManager, privilegeUser3.getResource(), privilegeUser3.getType());

		privilegeService.addPrivilegeToRole(roleManager, privilegeRole1.getResource(), privilegeRole1.getType());
		privilegeService.addPrivilegeToRole(roleManager, privilegeRole2.getResource(), privilegeRole2.getType());
		privilegeService.addPrivilegeToRole(roleManager, privilegeRole3.getResource(), privilegeRole3.getType());

		privilegeService.addPrivilegeToRole(roleManager, privilegeOrganization1.getResource(), privilegeOrganization1.getType());
		privilegeService.addPrivilegeToRole(roleManager, privilegeOrganization2.getResource(), privilegeOrganization2.getType());
		privilegeService.addPrivilegeToRole(roleManager, privilegeOrganization3.getResource(), privilegeOrganization3.getType());

		LOG.info("Add Privileges for ROLE User...");
		privilegeService.addPrivilegeToRole(roleUser, privilegeUser1.getResource(), privilegeUser1.getType());
		privilegeService.addPrivilegeToRole(roleUser, privilegeRole1.getResource(), privilegeRole1.getType());
		privilegeService.addPrivilegeToRole(roleUser, privilegeOrganization1.getResource(), privilegeOrganization1.getType());

		LOG.info("Add Privileges for ROLE Operator...");
		userService.addPrivilegeToUser("operator", privilegeOrganization1.getResource(), privilegeOrganization1.getType());
		userService.addPrivilegeToUser("operator", privilegeRole1.getResource(), privilegeRole1.getType());

	}
	
	private void forProductionEnvironment() {

		LOG.info("Add Principal Organization...");
		OrganizationDto org1 = new OrganizationDto();
		org1.setName("UsinaTECH");
		org1.setEmail("contato@usinatech.com.br");
		org1.setOrganizationKey(UUID.randomUUID());
		org1.setEnabled(false);
		organizationService.createOrganization(org1);

		LOG.info("Add System Administator User...");
		UserDto user1 = new UserDto();
		user1.setName("System ADMINSTRATOR");
		user1.setEmail("contato@usinateh.com.br");
		user1.setUsername("sys");
		user1.setPassword("1234");
		userService.createUser(user1);
		
		LOG.info("Add SYSTEM Role...");
		RoleDto role1 = new RoleDto();
		role1.setName(roleSystem);
		role1.setDescription("Role for system administrator");
		role1.setEnabled(true);
		roleService.createRole(role1);
		
		LOG.info("Associate SYS user into a SYSTEM role.");
		roleService.addRoleToUser("sys", roleSystem);

	}

}
