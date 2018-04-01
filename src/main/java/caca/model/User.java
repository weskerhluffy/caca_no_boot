package caca.model;

public class User {

	private Long id;

	private String firstName;

	private String familyName;

	private String email;

	public User() {
		super();
	}

	public User(Long id, String firstName, String familyName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.familyName = familyName;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}