package server.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coach {
    private int coachId;
    private String name;
    private int age;
    private int years_coached;
    private int teamId;
}
