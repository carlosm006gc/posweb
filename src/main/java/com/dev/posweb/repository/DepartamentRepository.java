
package com.dev.posweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.posweb.domain.Departament;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long>{

	@Query("select d from Departament d where lower(d.name) like lower(concat(:termo, '%'))")
	List<Departament> searchByName(@Param("termo") String termo);
}
