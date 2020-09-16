package br.com.lepsistemas.telegram.infrastructure.stanfordnlp;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.lepsistemas.telegram.application.DiscoverSpeakerRepository;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import edu.stanford.nlp.simple.Sentence;

@Component
public class StanfordDiscoverSpeakerRepository implements DiscoverSpeakerRepository {
	
	private static final String ME = "LEANDRO";
	
	@Override
	public EntryMessage discover(EntryMessage input) {
	    Sentence sentence = new Sentence(input.text());
	    List<String> mentions = sentence.mentions();
	    
	    EntryMessage output = new EntryMessage(input.id(), input.text());
    	for (String mention : mentions) {
			if (isNotMe(mention)) {
				output.speaker(mention);
			}
		}

		return output;
	}

	private boolean isNotMe(String mention) {
		return !StanfordDiscoverSpeakerRepository.ME.equalsIgnoreCase(mention);
	}

}
