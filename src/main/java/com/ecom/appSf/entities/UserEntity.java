package com.ecom.appSf.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1754022545324503836L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, name = "public_user_id")
	private String userId;

	@Column(nullable = false, length = 20)
	private String firstName;

	@Column(nullable = false, length = 20)
	private String lastName;

	@Column(nullable = false, length = 20, unique = true)
	private String email;

	@Column(nullable = false, length = 60)
	private String encryptedPassword;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private AddressEntity address;

	@ManyToOne
	private RoleEntity role;

}
