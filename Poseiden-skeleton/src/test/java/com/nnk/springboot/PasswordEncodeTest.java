package com.nnk.springboot;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
@SpringBootTest
public class PasswordEncodeTest {
    @Test
    public void testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("123456");

        assertThat(encoder.matches("123456", pw)).isTrue();
        assertThat(encoder.matches("123456", "$2a$10$ifdTpbCbvcARsOY4QIzC9een3qA9dLe/fLGxQwB8VEaEOY5V49n4G")).isTrue();

        System.out.println("[ "+ pw + " ]");
    }
}
