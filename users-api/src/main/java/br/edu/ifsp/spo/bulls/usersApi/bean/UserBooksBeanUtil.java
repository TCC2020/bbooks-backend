package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserBooksBeanUtil {
    public UserBooksTO toDto(UserBooks userBooks) {
        UserBooksTO userBooksTO = new UserBooksTO();
        try{
            BeanUtils.copyProperties(userBooks, userBooksTO);
        }catch(Exception e) {
            e.printStackTrace();
        }
        userBooksTO.setStatus(UserBooks.Status.valueOf(userBooks.getStatus().name()).toString());

        return userBooksTO;
    }

    public UserBooks toDomain(UserBooksTO dto) {
        UserBooks userBooks = new UserBooks();
        try{
            BeanUtils.copyProperties(dto, userBooks);
        }catch(Exception e) {
            e.printStackTrace();
        }
        userBooks.setStatus(UserBooks.Status.getByString(dto.getStatus()));
        return userBooks;
    }

    Set<UserBooksTO> toDtoSet(Set<UserBooks> userBooks) {
        return userBooks.parallelStream().map(this::toDto).collect(Collectors.toSet());
    }
}
