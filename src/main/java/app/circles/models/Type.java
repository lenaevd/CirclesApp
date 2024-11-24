package app.circles.models;

import jakarta.persistence.*;

@Entity
@Table(name = "type")
public class Type {
    @Id
    private Integer id;
    private String name;
}
