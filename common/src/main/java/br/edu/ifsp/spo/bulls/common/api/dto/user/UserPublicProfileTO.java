package br.edu.ifsp.spo.bulls.common.api.dto.user;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserPublicProfileTO {
    public UUID id;
    public String name;
    public String description;
    public BaseUserTO user;
    public LocalDateTime createdAt;
    public List<ProfileTO> followers;
}
