package net.nanisl.zabuton.chabudai;

import org.apache.wicket.Page;

import net.nanisl.zabuton.app.ZabuApp;

final public class ChabudaiApp extends ZabuApp {

	public Class<? extends Page> getHomePage() {
		return IndexPage.class;
	}
}