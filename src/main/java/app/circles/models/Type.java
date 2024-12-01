package app.circles.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "types")
public class Type {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
}
