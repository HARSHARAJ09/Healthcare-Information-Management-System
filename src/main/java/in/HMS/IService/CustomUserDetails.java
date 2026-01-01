package in.HMS.IService;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.HMS.Entity.Admin;
import in.HMS.Entity.Doctor;
import in.HMS.Entity.Patient;



public class CustomUserDetails implements UserDetails {

	private Object user;
	private String email;
	private String password;
	private String role;
	private Long entityId;

	public CustomUserDetails(Admin admin) {
		this.user = admin;
		this.email = admin.getEmail();
		this.password = admin.getPassword();
		this.role = admin.getRole();
		this.entityId = admin.getAdminId();
	}

	public CustomUserDetails(Doctor doctor) {
		this.user = doctor;
		this.email = doctor.getEmail();
		this.password = doctor.getPassword();
		this.role = doctor.getRole();
		this.entityId = doctor.getDoctorId();
	}

	public CustomUserDetails(Patient patient) {
		this.user = patient;
		this.email = patient.getEmail();
		this.password = patient.getPassword();
		this.role = patient.getRole();
		this.entityId = patient.getPatientId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public Long getEntityId() {
		return entityId;
	}

	public Object getUser() {
		return user;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
