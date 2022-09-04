package com.example.school.model;

import com.example.school.annotation.FieldsValueMatch;
import com.example.school.annotation.PasswordValidator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match."
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match."
        )
})
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int personId;

    @NotBlank(message = "name must not be blank")
    @Size(min=3, message = "name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Need to provide mobile number")
    @Pattern(regexp = "(^$|[0-9]{9})", message = "Mobile number needs to be only digits with length 9")
    private String mobileNumber;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Please provide email again")
    @Email(message = "Please provide a valid confirmation email address")
    @Transient
    private String confirmEmail;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 3, message = "Passwords needs to be at least 3 chars long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message = "Confirm password can't be empty" )
    @Size(min = 3, message = "Confirm passwords needs to be at least 3 chars long")
    @Transient
    private String confirmPwd;

    //associations
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;
}
