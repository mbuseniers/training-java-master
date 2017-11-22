package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Company;

public interface CompanyRepository  extends JpaRepository<Company, Long> {

}
