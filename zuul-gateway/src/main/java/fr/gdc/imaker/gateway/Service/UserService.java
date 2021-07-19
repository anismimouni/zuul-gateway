package fr.gdc.imaker.gateway.Service;




import fr.gdc.imaker.gateway.Dto.UserDto;
import fr.gdc.imaker.gateway.Model.Users;

public interface UserService {
    Users findUserByEmail(String Email);
    Users registerUser(UserDto user);
    Users saveUser(Users user);

}
