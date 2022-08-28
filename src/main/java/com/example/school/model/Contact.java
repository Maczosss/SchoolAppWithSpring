package com.example.school.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="contact_msg")
public class Contact extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "contact_id")
    private int contactId;

    @NotBlank(message = "Type Your name")
    @Size(min = 3, max = 50, message = "3-50 characters for name")
    String name;

    @NotBlank(message = "Spierdalaj")
    @Pattern(regexp = "(^$|\\d{9})", message = "Number need to be at least 9 digits long")
    String mobileNum;

    @NotBlank(message = "Provide email address")
    @Email(message = "Please provide valid email address")
    String email;

    @NotBlank(message = "provide Subject")
    @Size(min = 5, max = 30, message = "Subject have to be at least 5 characters and maximum of 30 characters long")
    String subject;

    @NotBlank(message = "Tell as what you have in mind")
    @Size(min = 10, max = 500, message = "Message have to be at least 10 characters and maximum of 500 characters long")
    String message;

    private String status;
}
