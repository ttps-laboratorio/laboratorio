package com.ttps.laboratorio.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;

@Service
public class PdfGeneratorService {

	public void generateBudget(Study study, String filename) throws IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();

		// title
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(40);
		Paragraph title = new Paragraph(study.getPatient().getFirstName() + " " + study.getPatient().getLastName(),
				fontTitle);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// content
		Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		boldFont.setSize(20);

		Font regularFont = FontFactory.getFont(FontFactory.HELVETICA);
		regularFont.setSize(16);

		// health insurance
		document.add(new Paragraph("Obra social: ", boldFont));
		if (study.getPatient().getHealthInsurance() != null) {
			document.add(
					new Paragraph("    - " + study.getPatient().getHealthInsurance().getName() + ".", regularFont));
		} else {
			document.add(new Paragraph("    - No tiene.", regularFont));
		}

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// Presumptive diagnosis
		document.add(new Paragraph("Diagnóstico presuntivo: ", boldFont));
		document.add(new Paragraph("    - " + study.getPresumptiveDiagnosis().getDescription() + ".", regularFont));

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// Study type
		document.add(new Paragraph("Tipo de estudio: ", boldFont));
		document.add(new Paragraph("    - " + study.getType().getName() + ".", regularFont));

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// Budget
		document.add(new Paragraph("Presupuesto: ", boldFont));
		document.add(new Paragraph("    - " + study.getBudget().toString() + ".", regularFont));

		document.close();
	}

	public void generateConsent(Study study, String filename) throws IOException {

		Patient patient = study.getPatient();
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();

		// title
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(40);
		Paragraph title = new Paragraph("Consentimiento de estudio", fontTitle);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);

		// content
		Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		boldFont.setSize(20);

		Font regularFont = FontFactory.getFont(FontFactory.HELVETICA);
		regularFont.setSize(16);

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		document.add(new Paragraph(patient.getFirstName() + " " + patient.getLastName() + " con dni " + patient.getDni()
				+ " presto conformidad para realizarme el estudio médico.", regularFont));

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// Study type
		document.add(new Paragraph("Tipo de estudio: ", boldFont));
		document.add(new Paragraph("    - " + study.getType().getName() + ".", regularFont));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		// Study type
		document.add(new Paragraph("Firma: ", boldFont));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("Aclaración: ", boldFont));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.close();
	}

	public void generateFinalReport(Study study, String filename) throws IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();

		// Report date
		Font fontDate = FontFactory.getFont(FontFactory.HELVETICA);
		fontDate.setSize(14);
		Paragraph date = new Paragraph(study.getFinalReport().getCreatedAt().getDayOfMonth() + "/"
				+ study.getFinalReport().getCreatedAt().getMonthValue() + "/"
				+ study.getFinalReport().getCreatedAt().getYear(), fontDate);
		date.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(date);

		// title
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(40);
		Paragraph title = new Paragraph("Resultados", fontTitle);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);

		fontTitle.setSize(30);
		title = new Paragraph(study.getPatient().getFirstName() + " " + study.getPatient().getLastName(), fontTitle);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// content
		Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		boldFont.setSize(20);

		Font regularFont = FontFactory.getFont(FontFactory.HELVETICA);
		regularFont.setSize(16);

		// Result
		document.add(new Paragraph("Resultado: ", boldFont));
		if (study.getFinalReport() != null && study.getFinalReport().getPositiveResult() != null) {
			if (study.getFinalReport().getPositiveResult()) {
				document.add(new Paragraph("    - Positivo.", regularFont));
			} else {
				document.add(new Paragraph("    - Negativo.", regularFont));
			}
		}

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// Reporter
		document.add(new Paragraph("Médico informante: ", boldFont));
		document.add(new Paragraph("    - " + study.getFinalReport().getMedicalInformant().getFirstName() + " " +
				study.getFinalReport().getMedicalInformant().getLastName() + ".", regularFont));

		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));

		// Report
		document.add(new Paragraph("Informe del resultado: ", boldFont));
		document.add(new Paragraph("    - " + study.getFinalReport().getReport(), regularFont));

		document.close();
	}
}
