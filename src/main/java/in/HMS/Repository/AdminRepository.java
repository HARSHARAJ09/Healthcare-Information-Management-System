package in.HMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import in.HMS.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUser_UserId(Integer userId);
}
