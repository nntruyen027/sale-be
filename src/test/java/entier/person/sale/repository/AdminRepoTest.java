package entier.person.sale.repository;

import entier.person.sale.dto.req.AdminRequest;
import entier.person.sale.dto.req.UpdateAdminReq;
import entier.person.sale.dto.res.UserFullRes;
import entier.person.sale.util.DbFunctionExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class AdminRepoTest {

    @Mock
    private DbFunctionExecutor dbFunctionExecutor;

    @InjectMocks
    private AdminRepo adminRepo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void taoQuanTriVien_shouldReturnUserFullRes() {
        // given
        AdminRequest adminRequest = AdminRequest.builder()
                .hoTen("test")
                .avatar("test")
                .password("test")
                .repeatPassword("test")
                .build();

        Timestamp testDate = Timestamp.valueOf(LocalDateTime.now());
        UserFullRes mocRes = UserFullRes.builder()
                .hoTen("test")
                .username("test")
                .id(1L)
                .isActive(true)
                .createdAt(testDate)
                .permissions(List.of())
                .roles(List.of())
                .avatar("test")
                .build();

        List<Object> params = new ArrayList<>();
        params.add(adminRequest.getUsername());
        params.add(adminRequest.getHoTen());
        params.add((adminRequest.getPassword()));
        params.add(adminRequest.getAvatar());

        when(dbFunctionExecutor.execute(
                eq("auth.fn_tao_nguoi_dung_quan_tri"),
                eq(params),
                eq(UserFullRes.class)))
                .thenReturn(mocRes);

        // when
        UserFullRes result = adminRepo.taoQuanTriVien(adminRequest);

        assertThat(result).isNotNull();
        assertThat(result.getHoTen()).isEqualTo("test");
        assertThat(result.getUsername()).isEqualTo("test");
        assertThat(result.getId()).isEqualTo(1L);

        verify(dbFunctionExecutor, times(1))
                .execute(eq("auth.fn_tao_nguoi_dung_quan_tri"), anyList(), eq(UserFullRes.class));
    }

    @Test
    void capNhatQuanTriVien_shouldReturnUserFullRes() {
        UpdateAdminReq adminRequest = UpdateAdminReq.builder()
                .hoTen("test")
                .avatar("test")
                .build();

        Timestamp testDate = Timestamp.valueOf(LocalDateTime.now());

        UserFullRes mocRes = UserFullRes.builder()
                .hoTen("test")
                .username("test")
                .id(1L)
                .isActive(true)
                .createdAt(testDate)
                .permissions(List.of())
                .roles(List.of())
                .avatar("test")
                .build();

        when(dbFunctionExecutor.execute(
                eq("auth.fn_cap_nhat_thong_tin_quan_tri_vien"),
                eq(List.of(
                        1L,
                        adminRequest.getAvatar(),
                        adminRequest.getHoTen())),
                eq(UserFullRes.class))
        ).thenReturn(mocRes);

        //when
        UserFullRes result = adminRepo.capNhatQuanTriVien(1L, adminRequest);

        assertThat(result).isNotNull();
        assertThat(result.getHoTen()).isEqualTo("test");
        assertThat(result.getUsername()).isEqualTo("test");

        verify(dbFunctionExecutor, times(1))
                .execute(eq("auth.fn_cap_nhat_thong_tin_quan_tri_vien"),
                        anyList(),
                        eq(UserFullRes.class));
    }
}
