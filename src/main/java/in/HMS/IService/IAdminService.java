package in.HMS.IService;

import in.HMS.Entity.Admin;

public interface IAdminService {

    Admin registerAdmin(Admin admin);

    Admin findByUserId(Integer userId);
}
