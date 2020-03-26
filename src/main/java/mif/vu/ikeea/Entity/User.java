package mif.vu.ikeea.Entity;

import lombok.Getter;
import lombok.Setter;
import mif.vu.ikeea.Enums.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private User manager;

    @ManyToMany
    @JoinTable(name = "restriction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "restriction_id"))
    private List<Restriction> restrictions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Goal> goals = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<LearningDay> learningDays = new ArrayList<>();
}
