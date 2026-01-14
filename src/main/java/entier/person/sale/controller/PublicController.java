package entier.person.sale.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cong-khai")
@Getter
@Setter
@AllArgsConstructor
@Tag(name = "Công khai")
public class PublicController {
}
