package app.circles.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "type")
public class Type {
    @Id
    private Integer id;
    private String name;
}
