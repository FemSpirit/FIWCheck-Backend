package FemSpirit.fiwcheckbackend.role.service;

import FemSpirit.fiwcheckbackend.role.dto.RoleRequestDTO;
import FemSpirit.fiwcheckbackend.role.dto.RoleResponseDTO;
import FemSpirit.fiwcheckbackend.role.entity.Role;
import FemSpirit.fiwcheckbackend.role.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import FemSpirit.fiwcheckbackend.user.repository.UserRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleService::toResponse)
                .toList();
    }

    public RoleResponseDTO create(RoleRequestDTO request) {
        Role role = new Role(request.name());
        Role saved = roleRepository.save(role);
        return toResponse(saved);
    }

    public static RoleResponseDTO toResponse(Role role) {
        return new RoleResponseDTO(role.getId(), role.getName());
    }

    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rolle nicht gefunden"));
        return toResponse(role);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rolle nicht gefunden"));

        if(userRepository.existsByRoleId(id)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Rolle ist noch einem User zugeordnet und kann nicht gelöscht werden");
        }
        roleRepository.delete(role);
    }
}
