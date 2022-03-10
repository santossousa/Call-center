package br.com.fabio.call_center.model;

import org.apache.commons.lang3.Validate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Call {
	private int duration;

	public Call(int duration) {
		super();
		Validate.notNull(duration);
        Validate.isTrue(duration >= 0);
		this.duration = duration;
	}

	public Integer getDuration() {
		return duration;
	}
	 public static Call buildRandomCall(int maxDuration, int minDuration) {
		 Validate.isTrue(maxDuration >= minDuration && minDuration >= 0);
	        return new Call(ThreadLocalRandom.current().nextInt(minDuration, maxDuration + 1));
	 }
	 public static List<Call> buildListOfRandomCalls(int listSize, int minDuration, int maxDuration){
		 Validate.isTrue(listSize >= 0);
		 List<Call> callList = new ArrayList<>();
	        for (int i = 0; i < listSize; i++) {
	            callList.add(buildRandomCall(minDuration, maxDuration));
	        }
	        return callList;

	 }
	

}
