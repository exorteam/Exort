package exort.permission_manager.serviceimpl;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.repository.PermRepository;
import exort.permission_manager.repository.RolePermRepository;
import exort.permission_manager.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermServiceImpl implements PermService {

    @Autowired
    private PermRepository pr;
    @Autowired
    private RolePermRepository rpr;

    @Transactional
    @Override
    public ExortPerm create(String name, String category, String description) {
        if (pr.findById(name).isPresent()) {
            return null;
        }
        ExortPerm perm = new ExortPerm(name, category, description);
        pr.save(perm);
        return perm;
    }

    @Transactional
    @Override
    public void delete(String name) {
        rpr.deleteByPermId(name);
        try {
            pr.deleteById(name);
        } catch (EmptyResultDataAccessException ignore) { }
    }

    @Transactional
    @Override
    public ExortPerm update(String name, String category, String description) {
        ExortPerm perm = pr.findById(name).orElse(null);
        if (perm != null) {
            perm.setCategory(category);
            perm.setDescription(description);
            pr.save(perm);
            return perm;
        }
        return null;
    }

    @Override
    public ExortPerm get(String name) {
        return pr.findById(name).orElse(null);
    }

    @Override
    public List<ExortPerm> list() {
        return pr.findAllOrderByCategory();
    }
}
