package org.persistence;

import org.core.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface ComputerRepository extends JpaRepository<Computer, Integer> {

	@Query(value = "SELECT * FROM computer LEFT JOIN company on computer.company_id=company.id  WHERE computer.name LIKE %?1%  LIMIT ?3 OFFSET ?2  ", nativeQuery = true)
    ArrayList<Computer> filter(final String string, long offset, int limit);
	
	@Query(value = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ?2 OFFSET ?1", nativeQuery = true)
    ArrayList<Computer> findWithLimitOffset(long offset, int limit);

	@Query(value = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE '%' ?1 '%'", nativeQuery= true)
    ArrayList<Computer> findByName(String name);
	
	@Query(value= "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE company.id=?1", nativeQuery=true)
    ArrayList<Computer> findByCompanyId(Long companyIdToFind);
	
	@Query(value = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE '%' ?1 '%'", nativeQuery= true)
    ArrayList<Computer> findByCompanyName(String name);

	@Modifying
	@Query(value = "DELETE FROM computer WHERE company_id = ?1 ", nativeQuery= true)
    void deleteComputersByCompanyId(long companyId);
}
