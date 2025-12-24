package in.HMS.IService.Imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.HMS.Entity.Admin;
import in.HMS.Repository.AdminRepository;
import in.HMS.IService.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin registerAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin findByUserId(Integer userId) {
        return adminRepository.findAll()
                .stream()
                .filter(a -> a.getUser().getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}
