INSERT INTO public.users (email,password,role,username) VALUES ('ivacca@mail.com','$2a$10$4sMYAK8IV6KPrUUyspwNaO3qX2R2RWIBueFfMoS4qyIPudoEaAe4K','EMPLOYEE','ivacca');
INSERT INTO public.users (email,password,role,username) VALUES ('sscotto@mail.com','$2a$10$4sMYAK8IV6KPrUUyspwNaO3qX2R2RWIBueFfMoS4qyIPudoEaAe4K','EMPLOYEE','sscotto');
INSERT INTO public.users (email,password,role,username) VALUES ('admin@admin.com','$2a$10$0mYnz1wsa.KV28mgcHWWoufVEs7R0XF0ktYYGY1mWIwcGAUHZSWKG','ADMINISTRATOR','admin');
INSERT INTO public.users (email,password,role,username) VALUES ('config@config.com','$2a$10$0HMf1ttRdbbfeSbkKRBHXOoC79Efn2GezAM9VUG1wqQJdSz4la/VG','CONFIGURATOR','config');
INSERT INTO public.users (email,password,role,username) VALUES ('agus@mail.com','$2a$10$4sMYAK8IV6KPrUUyspwNaO3qX2R2RWIBueFfMoS4qyIPudoEaAe4K','EMPLOYEE','agus');

INSERT INTO public.study_types (consent,name) VALUES ('','Exoma');
INSERT INTO public.study_types (consent,name) VALUES ('','Genoma mitocondrial completo');
INSERT INTO public.study_types (consent,name) VALUES ('','Carrier de enfermedades monogénicas recesivas');
INSERT INTO public.study_types (consent,name) VALUES ('','Cariotipo');
INSERT INTO public.study_types (consent,name) VALUES ('','Array CGH');

INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando comprobante de pago',1);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Enviar consentimiento informado',2);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando consentimiento informado firmado',3);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando seleccion de turno para extracción',4);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando toma de muestra',5);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando retiro de muestra desde sección de extracción',6);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando lote de muestra para iniciar procesamiento biotecnológico',7);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando resultado biotecnológico',8);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando interpretación de resultados e informes',9);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Esperando ser entregado a médico derivante',10);
INSERT INTO public.study_statuses (name,num_order) VALUES ('Resultado entregado',11);

UPDATE public.study_statuses SET next_id=2 WHERE id=1;
UPDATE public.study_statuses SET next_id=3,previous_id=1 WHERE id=2;
UPDATE public.study_statuses SET next_id=4,previous_id=2 WHERE id=3;
UPDATE public.study_statuses SET next_id=5,previous_id=3 WHERE id=4;
UPDATE public.study_statuses SET next_id=6,previous_id=4 WHERE id=5;
UPDATE public.study_statuses SET next_id=7,previous_id=5 WHERE id=6;
UPDATE public.study_statuses SET next_id=8,previous_id=6 WHERE id=7;
UPDATE public.study_statuses SET next_id=9,previous_id=7 WHERE id=8;
UPDATE public.study_statuses SET next_id=10,previous_id=8 WHERE id=9;
UPDATE public.study_statuses SET next_id=11,previous_id=9 WHERE id=10;
UPDATE public.study_statuses SET previous_id=10 WHERE id=11;

INSERT INTO public.presumptive_diagnoses (description) VALUES ('Acidez de estómago');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Acné');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Acúfenos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Adenoma hipofisiario');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Aerofagia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Aftas bucales');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Agorafobia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Alergia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Alergia al látex');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Alergia al polen');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Alergias alimentarias');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Alopecia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Alzheimer');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Amenorrea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Amigdalitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Anemia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Aneurisma de aorta');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Angina de pecho');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Anisakiasis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Anorexia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ansiedad');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Apendicitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Apnea del sueño');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Arritmias');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Arterioesclerosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Artritis reumatoide');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Artrosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Asbestosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Asma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Astigmatismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ataxia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ateroesclerosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Autismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Balanitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bartolinitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Botulismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bradicardia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bronquiectasias');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bronquitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Brucelosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bruxismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bulimia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bullying');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Bursitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Callos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de cabeza y cuello');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de colon');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de cuello de útero');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de endometrio');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de estómago');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de faringe');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de intestino delgado');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de laringe');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de las vías biliares');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de mama');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de ovario');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de páncreas');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de piel');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de próstata');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de pulmón');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de riñón');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de testículo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de tiroides');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de uretra');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cáncer de vejiga');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Candidiasis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cardiopatías congénitas');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cataratas');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Celiaquía');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cervicitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Chikungunya');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ciática');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cirrosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Citomegalovirus');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Colecistitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Colelitiasis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cólera');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cólico del lactante');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Cólico nefrítico');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Colitis ulcerosa');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Colon irritable');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Conjuntivitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Coronavirus');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Corte de digestión o hidrocución');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Creutzfeldt jakob (Vacas locas)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Degeneración macular asociada a la edad (DMAE)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Demencia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Demencia con cuerpos de Lewy');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dengue');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Depresión');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dermatitis atópica');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dermatitis del pañal');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dermatitis seborreica');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Derrame pleural');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Desprendimiento de retina');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Diabetes');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Diarrea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Diarrea del viajero');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Difteria');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Disfunción sexual femenina');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dislexia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dismenorrea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dismorfofobia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dispepsia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Diverticulitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Dolor de cabeza o cefalea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Eccema');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Edema Pulmonar');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('ELA (esclerosis lateral amiotrófica)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Embolia pulmonar');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Encefalitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Encefalopatía hepática');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Endocarditis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Endometriosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad de Crohn');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad de Kawasaki');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad de Paget');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad de Whipple');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad de Wilson');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad del sueño');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad por virus de Marburgo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad renal crónica');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfermedad tromboembólica venosa');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enfisema');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Enuresis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Epilepsia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('EPOC (enfermedad pulmonar obstructiva crónica)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Escarlatina');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Esclerodermia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Esclerosis múltiple');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Escoliosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Esofagitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Esofagitis eosinofílica');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Esófago de Barret');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Espina bífida');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Espolón calcáneo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Espondilitis anquilosante');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Esquizofrenia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Esterilidad e infertilidad');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Estrabismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Estreñimiento');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Estrés');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Factores de riesgo cardiovascular');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Faringitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Faringoamigdalitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fascitis plantar');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fenilcetonuria');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fibromialgia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fibrosis pulmonar');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fibrosis pulmonar idiopática');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fibrosis Quística');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fiebre amarilla');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fiebre del heno');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fiebre tifoidea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fiebres hemorrágicas');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fimosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Fobia social');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Gases y flatulencias');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Gastritis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Gastroenteritis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Glaucoma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Golpe de calor');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Gonorrea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Gota');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Gripe');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hafefobia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Halitosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hematoma subdural');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hemocromatosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hemofilia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hemorragias ginecológicas');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hemorroides');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hepatitis A');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hepatitis B');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hepatitis C');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hernia discal');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hernia inguinal');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Herpes labial');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Herpes zóster');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hidradenitis supurativa');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hidrocele');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipercolesterolemia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipercolesterolemia familiar');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hiperhidrosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipermenorrea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipermetropía');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hiperplasia benigna de próstata');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipertensión arterial');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipertiroidismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipoglucemia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipotensión');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hipotiroidismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hirsutismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Hongos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ictus');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Impétigo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Impotencia/ disfunción eréctil');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Incontinencia urinaria');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Infarto de miocardio');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Infección urinaria o cistitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Insomnio');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Insuficiencia cardiaca');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Intolerancia a la lactosa');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Juanetes');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ladillas (piojos del pubis)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Legionella');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Leishmaniasis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Lengua geográfica o glositis migratoria benigna');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Lepra');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Leucemia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Linfoma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Lipedema');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Lipotimia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Listeriosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Litiasis renal');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ludopatía');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Lumbalgia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Lupus');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Malaria');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Melanoma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Melanoma metastásico');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Melasma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Meningitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Mielitis transversa');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Mieloma múltiple');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Migrañas');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Miocardiopatía');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Miomas uterinos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Miopía');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Mobbing');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Molusco contagioso');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Mononucleosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Muerte súbita cardiaca');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Narcolepsia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Neumonía');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Neumotórax');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Obesidad');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ojo seco');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ojo vago');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Orquitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Ortorexia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Orzuelo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Osteoporosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Osteosarcoma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Otitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Pancreatitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Paperas (parotiditis)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Parálisis de Bell');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Parálisis del sueño');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Parkinson');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Pericarditis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Peste');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Pie de atleta');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Pielonefritis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Pies cavos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Pies planos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Pies zambos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Poliomielitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Preeclampsia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Presbicia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Prostatitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Psoriasis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Rabia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Rectocele');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Retinoblastoma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Retinopatía diabética');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Rinitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Rosácea');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Rotavirus');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Rubéola');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Salmonelosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sarampión');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sarcoidosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sarcoma');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sepsis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sífilis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Silicosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de burnout');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Diógenes');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Down');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Dravet');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de estrés postraumático');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de fatiga crónica');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Ganser');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Gilbert');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Gorlin-Goltz');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Guillain-Barré');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de hiperestimulación ovárica');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Marfan');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Patau');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Reiter');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Rett');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Reye');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Sanfilippo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Sjögren');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Smith-Magenis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Tourette');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Turner');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Williams');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome de Wolfram');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome del túnel carpiano');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndrome postvacacional');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Síndromes mielodisplásicos (SMD)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sinus Pilonidal');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sinusitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Siringomielia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sobrecrecimiento bacteriano o SIBO');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Sonambulismo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Tendinitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Tétanos');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Tortícolis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Tos ferina');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Toxoplasmosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trastorno bipolar');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trastorno de conducta del sueño en fase REM');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trastorno de menstruación');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trastorno obsesivo compulsivo (TOC)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trastorno por atracón');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trastorno por déficit de atención e hiperactividad (TDAH)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trastornos del ritmo circadiano');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Tricomoniasis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Trombosis venosa (flebitis)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Tuberculosis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Tumores cerebrales');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Uñas encarnadas (onicocriptosis)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Uretritis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Urticaria');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vaginitis o vulvovaginitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vaginosis bacteriana');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Varicela');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Varices');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Varicocele');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vasculitis');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vegetaciones');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vértigo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vigorexia');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('VIH / Sida');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Virus del Nilo Occidental');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Virus del papiloma humano (VPH)');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Virus Zika');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vitíligo');
INSERT INTO public.presumptive_diagnoses (description) VALUES ('Vulvitis');