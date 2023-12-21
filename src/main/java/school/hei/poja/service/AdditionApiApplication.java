import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

@SpringBootApplication
public class AdditionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdditionApiApplication.class, args);
    }
}

@Entity
class AdditionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger numA;
    private BigInteger numB;
    private BigInteger sum;

    // Getter and Setter methods
}

@Repository
interface AdditionResultRepository extends JpaRepository<AdditionResult, Long> {
}

@RestController
class AdditionController {
    private final AdditionResultRepository resultRepository;

    public AdditionController(AdditionResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @GetMapping("/addition")
    public String add(@RequestParam("a") String a, @RequestParam("b") String b) {
        try {
            BigInteger numA = new BigInteger(a);
            BigInteger numB = new BigInteger(b);
            BigInteger sum = numA.add(numB);

            AdditionResult result = new AdditionResult();
            result.setNumA(numA);
            result.setNumB(numB);
            result.setSum(sum);

            resultRepository.save(result);

            return sum.toString();
        } catch (NumberFormatException e) {
            return "Les paramètres doivent être des nombres valides.";
        }
    }
}
