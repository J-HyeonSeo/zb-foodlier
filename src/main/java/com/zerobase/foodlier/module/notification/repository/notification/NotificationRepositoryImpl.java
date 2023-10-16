package com.zerobase.foodlier.module.notification.repository.notification;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.foodlier.module.member.member.domain.model.QMember;
import com.zerobase.foodlier.module.notification.domain.model.QNotification;
import com.zerobase.foodlier.module.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<NotificationDto> findNotificationBy(Long memberId, Pageable pageable) {
        QNotification notification = QNotification.notification;
        QMember member = QMember.member;

        List<NotificationDto> content = jpaQueryFactory.select(
                Projections.constructor(NotificationDto.class,
                        notification.id, notification.content, notification.notificationType,
                        notification.sendAt, notification.isRead, notification.url))
                .from(notification)
                .join(member)
                .on(notification.member.eq(member))
                .orderBy(notification.sendAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(content);
    }
}
