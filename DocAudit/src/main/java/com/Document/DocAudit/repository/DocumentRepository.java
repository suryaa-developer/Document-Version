package com.Document.DocAudit.repository;

import com.Document.DocAudit.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {
}
