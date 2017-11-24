package org.persistence;

import org.core.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository  extends JpaRepository<Company, Long> {

}
