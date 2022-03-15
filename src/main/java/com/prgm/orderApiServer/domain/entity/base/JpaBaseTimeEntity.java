package com.prgm.orderApiServer.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.Instant;
import java.time.LocalDateTime;

@MappedSuperclass
public class JpaBaseTimeEntity {

    @Column(updatable = false) //DB의 값이 변경되지 않게 고정해주는 어노테이션 private LocalDateTime createdDate;
    private Instant createdDate;

    @PrePersist //persist (등록) 하기전에 발동 public void prePersist(){ LocalDateTime now = LocalDateTime.now(); createdDate = now; updatedDate = now; }
    public void prePersist(){
        Instant now = Instant.now();
        createdDate = now; }

}
