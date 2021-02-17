package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "group_invites")
public class GroupInvite {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @NotNull(message = "Group is missing")
    @ManyToOne
    private GroupRead group;
    @NotNull(message = "user id is missing")
    private UUID userId;
    @NotNull(message = "inviter id is missing")
    private UUID inviter;
}
