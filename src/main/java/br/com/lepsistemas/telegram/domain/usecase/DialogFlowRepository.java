package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.Output;

public interface DialogFlowRepository {

	Output forMessage(String text);

}
