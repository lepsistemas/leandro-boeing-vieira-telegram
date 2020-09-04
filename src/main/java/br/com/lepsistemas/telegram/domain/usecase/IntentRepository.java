package br.com.lepsistemas.telegram.domain.usecase;

import java.util.List;

import br.com.lepsistemas.telegram.domain.model.Intent;

public interface IntentRepository {

	List<Intent> by(String text);

}
