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
@Table(name = "contact_msg")
@SqlResultSetMappings({
       @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
})
@NamedQueries({
        @NamedQuery(name = "Contact.findOpenMsgs",
                query = "SELECT c FROM Contact c WHERE c.status = :status"),
        @NamedQuery(name = "Contact.updateMsgStatus",
                query = "UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Contact.findOpenMsgsNative",
                query = "SELECT * FROM contact_msg c WHERE c.status = :status",
                resultClass = Contact.class),
        @NamedNativeQuery(name = "Contact.findOpenMsgsNative.count",
                query = "SELECT count(*) as cnt FROM contact_msg c WHERE c.status = :status",
                resultSetMapping = "SqlResultSetMapping.count"),
        /*Spring Data JPA doesn’t support dynamic sorting for native queries.
        Doing that would require Spring Data to analyze the provided statement and generate
        the ORDER BY clause in the database-specific dialect. This would be a very complex operation
        and is currently not supported by Spring Data JPA.*/
        @NamedNativeQuery(name = "Contact.updateMsgStatusNative",
                query = "UPDATE contact_msg c SET c.status = ?1 WHERE c.contact_id = ?2")
})
public class Contact extends BaseEntity {

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
