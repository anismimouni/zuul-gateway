package fr.gdc.imaker.gateway.Service;
import fr.gdc.imaker.gateway.Dto.UserDto;
import fr.gdc.imaker.gateway.Model.Roles;
import fr.gdc.imaker.gateway.Model.Users;
import fr.gdc.imaker.gateway.Repository.RoleRepo;
import fr.gdc.imaker.gateway.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.NoSuchElementException;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private ModelMapper modelMapper;




    @Override
    public Users findUserByEmail(String Email) {
        return userRepository.findByEmail(Email);
    }

    @Override
    public Users registerUser(UserDto newUser) {
        if (findUserByEmail(newUser.getEmail()) == null) {
            Users user=modelMapper.map(newUser,Users.class);
            Roles userRole = roleRepository.findRolesByRoleName("user".toUpperCase(Locale.ROOT));
            user.setUserRole(userRole);
            String passwordEncode = bCryptPasswordEncoder.encode(newUser.getPassword());
            user.setPassword(passwordEncode);
            return userRepository.save(user);
        } else
            throw new NoSuchElementException("An account already exist with this mail !!");
    }
    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }
}




