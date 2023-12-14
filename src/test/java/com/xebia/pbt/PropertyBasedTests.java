package com.xebia.pbt;

import net.jqwik.api.*;
import org.assertj.core.api.*;

class PropertyBasedTests {
	@Property
	void reverseTwiceProduceSameResult(@ForAll String s) {
		StringBuilder sb = new StringBuilder(s);
		String expected = sb.reverse().reverse().toString();
		Assertions.assertThat(expected).isEqualTo(s);
	}
}