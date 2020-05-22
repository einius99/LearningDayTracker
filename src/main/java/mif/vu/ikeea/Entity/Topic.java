package mif.vu.ikeea.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic parent;

    @OneToMany(mappedBy="parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Topic> children = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "topic_learning_day",
                joinColumns = {@JoinColumn(name = "topic_id")},
                inverseJoinColumns = {@JoinColumn(name = "learning_day_id")},
            uniqueConstraints = {@UniqueConstraint(
                    columnNames = {"topic_id", "learning_day_id"})})
    private List<LearningDay> learningDays = new ArrayList<>();
}
