package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findByRoleName(String roleName);

}
