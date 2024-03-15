package utez.edu.mx.backend.access.role.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonType;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import utez.edu.mx.backend.access.privilege.model.Privilege;
import utez.edu.mx.backend.access.user.model.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "roles")
@TypeDef(name = "json", typeClass = JsonType.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", columnDefinition = "VARCHAR(45) NOT NULL")
    private RoleTypes name;

    @Column(name = "description", columnDefinition = "VARCHAR(100) NOT NULL")
    private String description;

    @Column(name = "privileges", columnDefinition = "json")
    @Type(type = "json")
    private String privileges;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role")
    private List<User> users;

    public Role(RoleTypes name, String description, String privileges) {
        this.name = name;
        this.description = description;
        this.privileges = privileges;
    }

    public List<Privilege> getPrivileges() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(this.privileges, new TypeReference<List<Privilege>>() {
        });
    }
}
