package com.ttps.laboratorio.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.ttps.laboratorio.entity.Study;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class FileDownloadService {

	public void exportBudget(HttpServletResponse response, Study study) throws IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		//title
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(40);
		Paragraph title = new Paragraph(study.getPatient().getFirstName() + " " + study.getPatient().getLastName(), fontTitle);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		//content
		Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		boldFont.setSize(20);

		Font regularFont = FontFactory.getFont(FontFactory.HELVETICA);
		regularFont.setSize(16);

		//health insurance
		document.add(new Paragraph("Obra social: ", boldFont));
		if (study.getPatient().getHealthInsurance() != null) {
			document.add(new Paragraph("    - " + study.getPatient().getHealthInsurance().getName() + ".", regularFont));
		} else {
			document.add(new Paragraph("    - No tiene.", regularFont));
		}

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		//Presumptive diagnosis
		document.add(new Paragraph("Diagnóstico presuntivo: ", boldFont));
		document.add(new Paragraph("    - " + study.getPresumptiveDiagnosis().getDescription() + ".", regularFont));

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		//Study type
		document.add(new Paragraph("Tipo de estudio: ", boldFont));
		document.add(new Paragraph("    - " + study.getType().getName() + ".", regularFont));

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		//Budget
		document.add(new Paragraph("Presupuesto: ", boldFont));
		document.add(new Paragraph("    - " + study.getBudget().toString() + ".", regularFont));

		document.close();

	}

}
