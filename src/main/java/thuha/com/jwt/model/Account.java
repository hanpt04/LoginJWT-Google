package thuha.com.jwt.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @Nationalized
    private String name;
    @NotNull(message = "Role must not be null")
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    private Date createAt;
    @UpdateTimestamp
    private Date updateAt;
    private boolean isActive = true;
    private String password;
    private String mail;
    private String phone;

}
