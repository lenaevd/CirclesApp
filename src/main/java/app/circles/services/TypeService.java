package app.circles.services;

import app.circles.models.Type;
import app.circles.repos.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {
    private final TypeRepository typeRepo;

    @Autowired
    public TypeService(TypeRepository repo) {
        this.typeRepo = repo;
    }

    public List<Type> findTypesByNamesList(List<String> names) {
        List<Type> types = new ArrayList<>();
        for (String name: names) {
            types.add(typeRepo.findByName(name));
        }
        return types;
    }
}
