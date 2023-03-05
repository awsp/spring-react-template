package com.github.awsp.repo;

import com.github.awsp.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
public interface SoftDeleteCRUDRepository<T extends BaseEntity, ID extends Long> extends JpaRepository<T, ID> {
  @Override
  @Transactional
  @Modifying
  @Query("UPDATE #{#entityName} e SET e.deleted = true WHERE e.id = ?1")
  void deleteById(ID id);

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
  List<T> findAll();

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false AND e.id = ?1")
  boolean existsById(ID id);

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.deleted = false")
  long count();

  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = true")
  List<T> findInactive();

  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e")
  List<T> superFindAll();
}
