package FemSpirit.fiwcheckbackend.role;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Rolle nicht gefunden"));
        return toResponse(role);
    }
}