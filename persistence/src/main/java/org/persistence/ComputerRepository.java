package org.persistence;

import java.util.ArrayList;

import org.core.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
public interface ComputerRepository extends JpaRepository<Computer, Integer> {

	@Query(value = "SELECT * FROM computer LEFT JOIN company on computer.company_id=company.id  WHERE computer.name LIKE %?1%  LIMIT ?3 OFFSET ?2  ", nativeQuery = true)
	public ArrayList<Computer> filter(final String string, long offset, int limit);
	
	@Query(value = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ?2 OFFSET ?1", nativeQuery = true)
	public ArrayList<Computer> findWithLimitOffset(long offset, int limit);

	@Query(value = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE '%' ?1 '%'", nativeQuery= true)
	public ArrayList<Computer> findByName(String name);
	
	@Query(value = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE '%' ?1 '%'", nativeQuery= true)
	public ArrayList<Computer> findByCompanyName(String name);
	
	@Query(value = "DELETE FROM computer WHERE company_id = ?1 ", nativeQuery= true)
	public boolean deleteComputersByCompanyId(long companyId);
}
