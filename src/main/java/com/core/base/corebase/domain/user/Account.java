package com.core.base.corebase.domain.user;

import com.core.base.corebase.domain.user.code.UserState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @Column(name = "uid", insertable = false, updatable = false, columnDefinition = "uuid")
    private UUID uid;

    @MapsId("uid")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String refreshToken;

    protected Account() {
        // For JPA
    }

    public Account(String refreshToken, UserState state, User user) {
        this.uid = user.getUid();
        this.refreshToken = refreshToken;
        this.state = state.name();
        this.user = user;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateState(UserState newState) {
        this.state = newState.name();
    }

    public UserState getUserState() {
        if(state == null) {
            return null;
        }
        return UserState.valueOf(this.state);
    }
}
