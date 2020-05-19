package br.com.itau.fraud.dtos;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO implements IBaseDTO {

    private BigInteger id;

    private String user;
}
