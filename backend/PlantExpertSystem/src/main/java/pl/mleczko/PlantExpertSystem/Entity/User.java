package pl.mleczko.PlantExpertSystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;


    @NonNull
    @Column(name = "email", unique = true)
    private String email;

    @NonNull
    @Column(name = "password", columnDefinition = "LONGTEXT")
    private String password;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    private boolean isEnabled;

    @Column
    private LocalDateTime joinDate;

    @OneToOne(cascade = CascadeType.ALL)
    private VerificationToken verificationToken;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference("userMessages")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ContactMessage> messages;


    @JsonIgnore
    @JsonManagedReference("userDiagnoses")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Diagnose> diagnoses;

    @JsonManagedReference("userTemporaryDiseases")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TemporaryDisease> temporaryDiseases;


    public User(@NonNull String email, @NonNull String password, @NonNull String firstName, @NonNull String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnabled = false;
        this.joinDate = LocalDateTime.now();
    }

    public User() {
        this.isEnabled = true;
    }
}
