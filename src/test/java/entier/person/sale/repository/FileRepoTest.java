package entier.person.sale.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import entier.person.sale.dto.req.FileReq;
import entier.person.sale.dto.res.FileRes;
import entier.person.sale.dto.res.PageResponse;
import entier.person.sale.util.DbFunctionExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FileRepoTest {

    @Mock
    private DbFunctionExecutor dbFunctionExecutor;

    @InjectMocks
    private FileRepo fileRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ==========================
    // Test layTatCaFile
    // ==========================
    @Test
    void layTatCaFile_shouldReturnPageResponse() {

        // given
        String search = "test";
        int page = 1;
        int limit = 10;

        PageResponse<FileRes> mockResponse = new PageResponse<>();
        mockResponse.setTotalElements(1L);

        when(dbFunctionExecutor.execute(
                eq("fn_lay_tat_ca_file"),
                eq(List.of(search, page, limit)),
                ArgumentMatchers.<TypeReference<PageResponse<FileRes>>>any()
        )).thenReturn(mockResponse);

        // when
        PageResponse<FileRes> result =
                fileRepo.layTatCaFile(search, page, limit);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1L);

        verify(dbFunctionExecutor, times(1))
                .execute(eq("fn_lay_tat_ca_file"), anyList(), any(TypeReference.class));
    }

    // ==========================
    // Test luuFile
    // ==========================
    @Test
    void luuFile_shouldReturnFileRes() {

        // given
        FileReq req = new FileReq();
        req.setFileName("a.png");
        req.setStoredName("uuid.png");
        req.setUrl("/files/uuid.png");
        req.setContentType("image/png");
        req.setSize(1024L);
        req.setUserId(1L);

        FileRes mockRes = new FileRes();
        mockRes.setFileName("a.png");

        when(dbFunctionExecutor.execute(
                eq("fn_tao_file"),
                eq(List.of(
                        "a.png",
                        "uuid.png",
                        "/files/uuid.png",
                        "image/png",
                        1024L,
                        1L
                )),
                eq(FileRes.class)
        )).thenReturn(mockRes);

        // when
        FileRes result = fileRepo.luuFile(req);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFileName()).isEqualTo("a.png");

        verify(dbFunctionExecutor).execute(
                eq("fn_tao_file"),
                anyList(),
                eq(FileRes.class)
        );
    }

    // ==========================
    // Test xoaFile
    // ==========================
    @Test
    void xoaFile_shouldReturnTrue() {

        // given
        Long id = 10L;

        when(dbFunctionExecutor.execute(
                eq("fn_xoa_file"),
                eq(List.of(id)),
                eq(Boolean.class)
        )).thenReturn(true);

        // when
        Boolean result = fileRepo.xoaFile(id);

        // then
        assertThat(result).isTrue();

        verify(dbFunctionExecutor).execute(
                eq("fn_xoa_file"),
                eq(List.of(id)),
                eq(Boolean.class)
        );
    }
}
