package com.study.realworld.domain.profile.entity;

import com.study.realworld.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @JoinColumn(name = "from_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    @JoinColumn(name = "to_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;

    @Builder
    public Follow(final Long id, final User fromUser, final User toUser) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public Follow(final User fromUser, final User toUser) {
        this(null, fromUser, toUser);
    }

    @Override
    public String toString() {
        return "Follow{" + "id=" + id +
                ", from=" + fromUser.getUsername() +
                ", to=" + toUser.getUsername() +
                '}';
    }
}
