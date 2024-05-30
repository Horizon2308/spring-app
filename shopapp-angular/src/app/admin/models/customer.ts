// private Long id;

// @Column(name = "fullname", length = 100)
// private String fullName;

// @Column(name = "phone_number", nullable = false, length = 10, unique = true)
// private String phoneNumber;

// @Column(name = "email", length = 100)
// private String email;

// private String address;

export interface Customer {
  id: number;
  fullname: string;
  phone_number: string;
  email: string;
  address: string;
}
