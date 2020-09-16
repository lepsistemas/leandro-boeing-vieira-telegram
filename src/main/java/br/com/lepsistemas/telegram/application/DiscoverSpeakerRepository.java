package br.com.lepsistemas.telegram.application;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public interface DiscoverSpeakerRepository {

	EntryMessage discover(EntryMessage entry);

}
