package br.edu.ifsp.spo.bulls.users.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int profile1;
    private int Profile2;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
    private LocalDateTime addDate;

    public enum FriendshipStatus {
        pending("PENDING"),
        sent("SENT"),
        received("RECEIVED"),
        added("ADDED");

        private final String text;
        FriendshipStatus(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static FriendshipStatus getByString(String value) {
            for(FriendshipStatus status : FriendshipStatus.values()){
                if(status.text.equalsIgnoreCase(value))
                    return status;
            }
            return null;
        }
    }

}