package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.User;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.UserRepository;
import com.example.rentacarv1.services.abstracts.UserService;
import com.example.rentacarv1.services.dtos.requests.user.AddUserRequest;
import com.example.rentacarv1.services.dtos.requests.user.UpdateUserRequest;
import com.example.rentacarv1.services.dtos.responses.user.GetUserListResponse;
import com.example.rentacarv1.services.dtos.responses.user.GetUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class UserManager implements UserService {

    private UserRepository userRepository;
    private ModelMapperService modelMapperService;
    @Override
    public List<GetUserListResponse> getAll() {
        List<User> users=userRepository.findAll();
        List<GetUserListResponse> userListResponse=users.stream().map(user -> this.modelMapperService.forResponse()
                .map(user,GetUserListResponse.class)).collect(Collectors.toList());

        return userListResponse;
    }

    @Override
    public GetUserResponse getById(int id) {
        User user = userRepository.findById(id).orElseThrow();
        GetUserResponse getUserResponse=this.modelMapperService.forRequest().map(user,GetUserResponse.class);
        return getUserResponse;
    }

    @Override
    public void add(AddUserRequest addUserRequest) {
        User user=this.modelMapperService.forRequest().map(addUserRequest,User.class);
        this.userRepository.save(user);
    }

    @Override
    public void update(UpdateUserRequest updateUserRequest) {
        User user =this.modelMapperService.forRequest().map(updateUserRequest,User.class);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
