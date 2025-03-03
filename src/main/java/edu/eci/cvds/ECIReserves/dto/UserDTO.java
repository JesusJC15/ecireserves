package edu.eci.cvds.ECIReserves.dto;

import edu.eci.cvds.ECIReserves.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole rol;
}
