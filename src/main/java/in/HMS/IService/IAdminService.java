package in.HMS.IService;

import in.HMS.Entity.Admin;

public interface IAdminService {

    Admin create(Admin admin);

    Admin findByEmail(String email);

    Admin findById(Long adminId);
}
