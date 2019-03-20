package fcs.cec.opencec.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fcs.cec.opencec.entity.Lesson;

@Controller
public class LessonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LessonController.class);
	static ArrayList<Lesson> lessonList = new ArrayList<Lesson>();

	static {
		Document doc = null;
		String url = "https://script.googleusercontent.com/macros/echo?user_content_key=LEy1GAo9wjoLRZc9iC7Foj2_FlhoI-giInrErm2cV4u5x9ofZTKeL6S-8j3gm3s6KeWjkqEuwzhS-z8jFYAGLHj4X4Uv50wjm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnCT0QRJ7P_-LtV3tAd8_b_dUnbO1rEvbeLLB2eAoIGhp1hENMaacOI9TktsviLkDHJlUq1JAmpDs&lib=MmSKrXssQcdpiSXxZX7nm1QZVzjmXS3D2";
		try {
			doc = Jsoup.connect(url).timeout(30000).get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Elements elements = doc.select("lesson");
		Element eImg1 = null;
		Element eImg2 = null;
		Element audioMp3 = null;
		Element videoMp4 = null;
		String name = null;
		String img1 = null;
		String img2 = null;
		String audio = null;
		String video = null;
		Lesson lesson = null;
		for (Element element : elements) {
			name = element.attr("name");
			eImg1 = element.child(0);
			img1 = eImg1.text();
			eImg2 = element.child(1);
			img2 = eImg2.text();
			audioMp3 = element.child(2);
			audio = audioMp3.text();
			videoMp4 = element.child(3);
			video = audioMp3.text();
			lesson = new Lesson(name, audio, video, img1, img2);
			lessonList.add(lesson);
		}
	}

	@GetMapping(value = "lesson/{id}")
	public String profile(Model model, @PathVariable("id") String id) throws InterruptedException, ExecutionException {
		for (Lesson lesson : lessonList) {
			if(lesson.getName().equals(id)) {
				model.addAttribute("lesson", lesson);
			}
		}
		return "lesson/lesson";
	}
}