package com.devpedrod.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devpedrod.cursomc.domain.Cliente;
import com.devpedrod.cursomc.domain.enums.TipoCliente;
import com.devpedrod.cursomc.dto.ClienteNewDTO;
import com.devpedrod.cursomc.repositories.ClienteRepository;
import com.devpedrod.cursomc.resources.exception.FieldMessage;
import com.devpedrod.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	ClienteRepository repo;
	@Override
	public void initialize(ClienteInsert ann) { 
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getId()) && !BR.isValidCPF(objDto.getCpfOuCnpj())  ) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getId()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())  ) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente auxCpfOuCnpj = repo.findByCpfOuCnpj(objDto.getCpfOuCnpj());
		if(auxCpfOuCnpj != null) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Ou CNPJ já existente"));
		}

		
		Cliente auxEmail = repo.findByEmail(objDto.getEmail());
		if(auxEmail != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
