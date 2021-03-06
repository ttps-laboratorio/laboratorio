INSERT INTO users (email,password,role,username) VALUES ('ivacca@mail.com','$2a$10$4sMYAK8IV6KPrUUyspwNaO3qX2R2RWIBueFfMoS4qyIPudoEaAe4K','EMPLOYEE','ivacca');
INSERT INTO users (email,password,role,username) VALUES ('sscotto@mail.com','$2a$10$4sMYAK8IV6KPrUUyspwNaO3qX2R2RWIBueFfMoS4qyIPudoEaAe4K','EMPLOYEE','sscotto');
INSERT INTO users (email,password,role,username) VALUES ('admin@admin.com','$2a$10$0mYnz1wsa.KV28mgcHWWoufVEs7R0XF0ktYYGY1mWIwcGAUHZSWKG','ADMINISTRATOR','admin');
INSERT INTO users (email,password,role,username) VALUES ('config@config.com','$2a$10$0HMf1ttRdbbfeSbkKRBHXOoC79Efn2GezAM9VUG1wqQJdSz4la/VG','CONFIGURATOR','config');
INSERT INTO users (email,password,role,username) VALUES ('agus@mail.com','$2a$10$4sMYAK8IV6KPrUUyspwNaO3qX2R2RWIBueFfMoS4qyIPudoEaAe4K','EMPLOYEE','agus');
INSERT INTO users (email,password,role,username) VALUES ('ron@email.com','$2a$10$S2.HN4NEN748hN90Zlq59.pb9ie42hEmROdGqdhbiKZi8cBmsVJcK','PATIENT','rweasley');
INSERT INTO users (email,password,role,username) VALUES ('severus@email.com','$2a$10$S2.HN4NEN748hN90Zlq59.pb9ie42hEmROdGqdhbiKZi8cBmsVJcK','PATIENT','ssnape');
INSERT INTO users (email,password,role,username) VALUES ('harry@email.com','$2a$10$S2.HN4NEN748hN90Zlq59.pb9ie42hEmROdGqdhbiKZi8cBmsVJcK','PATIENT','hpotter');

INSERT INTO study_types (consent,name) VALUES ('','Exoma');
INSERT INTO study_types (consent,name) VALUES ('','Genoma mitocondrial completo');
INSERT INTO study_types (consent,name) VALUES ('','Carrier de enfermedades monogénicas recesivas');
INSERT INTO study_types (consent,name) VALUES ('','Cariotipo');
INSERT INTO study_types (consent,name) VALUES ('','Array CGH');

INSERT INTO study_statuses (id, name, num_order) VALUES (1,'Esperando comprobante de pago',1);
INSERT INTO study_statuses (id, name,num_order) VALUES (2,'Esperando validacion comprobante de pago',2);
INSERT INTO study_statuses (id, name,num_order) VALUES (3,'Enviar consentimiento informado',3);
INSERT INTO study_statuses (id, name,num_order) VALUES (4,'Esperando consentimiento informado firmado',4);
INSERT INTO study_statuses (id, name,num_order) VALUES (5,'Esperando seleccion de turno para extracción',5);
INSERT INTO study_statuses (id, name,num_order) VALUES (6,'Esperando toma de muestra',6);
INSERT INTO study_statuses (id, name,num_order) VALUES (7,'Esperando retiro de muestra desde sección de extracción',7);
INSERT INTO study_statuses (id, name,num_order) VALUES (8,'Esperando lote de muestra para iniciar procesamiento biotecnológico',8);
INSERT INTO study_statuses (id, name,num_order) VALUES (9,'Esperando resultado biotecnológico',9);
INSERT INTO study_statuses (id, name,num_order) VALUES (10,'Esperando interpretación de resultados e informes',10);
INSERT INTO study_statuses (id, name,num_order) VALUES (11,'Esperando ser entregado a médico derivante',11);
INSERT INTO study_statuses (id, name,num_order) VALUES (12,'Resultado entregado',12);
INSERT INTO study_statuses (id, name,num_order) VALUES (13,'Anulado por falta de pago', 13);
INSERT INTO study_statuses (id, name,num_order) VALUES (14,'Esperando resultado', 14);
INSERT INTO study_statuses (id, name,num_order) VALUES (15,'Resultado completo', 15);

UPDATE study_statuses SET next_id=2 WHERE id=1;
UPDATE study_statuses SET next_id=3,previous_id=1 WHERE id=2;
UPDATE study_statuses SET next_id=4,previous_id=2 WHERE id=3;
UPDATE study_statuses SET next_id=5,previous_id=3 WHERE id=4;
UPDATE study_statuses SET next_id=6,previous_id=4 WHERE id=5;
UPDATE study_statuses SET next_id=7,previous_id=5 WHERE id=6;
UPDATE study_statuses SET next_id=8,previous_id=6 WHERE id=7;
UPDATE study_statuses SET next_id=9,previous_id=7 WHERE id=8;
UPDATE study_statuses SET next_id=10,previous_id=8 WHERE id=9;
UPDATE study_statuses SET next_id=11,previous_id=9 WHERE id=10;
UPDATE study_statuses SET next_id=12,previous_id=10 WHERE id=11;
UPDATE study_statuses SET previous_id=11 WHERE id=12;
UPDATE study_statuses SET previous_id=1 WHERE id=13;

INSERT INTO health_insurances (email,name,phone_number) VALUES ('osde@email.com', 'OSDE', '111');
INSERT INTO health_insurances (email,name,phone_number) VALUES ('ioma@email.com', 'IOMA', '222');
INSERT INTO health_insurances (email,name,phone_number) VALUES ('galeno@email.com', 'Galeno', '333');

INSERT INTO guardians (address,email,first_name,last_name,phone_number) VALUES ('5 678','tutor@email.com','Minerva','McGonagall','2215555555');

INSERT INTO patients (address,affiliate_number,birth_date,clinic_history,dni,email,first_name,last_name,phone_number,health_insurance_id,user_id) VALUES ('Calle falsa 123','567','1980-03-01','paciente colorado','11111111','ron@email.com','Ron','Weasley','111',1,6);
INSERT INTO patients (address,affiliate_number,birth_date,clinic_history,dni,email,first_name,last_name,phone_number,health_insurance_id,user_id) VALUES ('54 124','123','1960-01-09','paciente malhumorado','11111112','severus@email.com','Severus','Snape','222',1,7);
INSERT INTO patients (address,affiliate_number,birth_date,clinic_history,dni,email,first_name,last_name,phone_number,health_insurance_id,user_id) VALUES ('1 543','111','1980-07-31','paciente peligroso','11111113','harry@email.com','Harry','Potter','333',1,8);
INSERT INTO patients (affiliate_number,birth_date,clinic_history,dni,first_name,last_name,guardian_id,health_insurance_id) VALUES ('666','2010-07-30','Crecio bien',11111114,'Neville','Longbottom',1,2);

INSERT INTO doctors (email,first_name,last_name,license_number,phone_number) VALUES ('doctor_uno@email.com','Doctor','Uno',111,'111');
INSERT INTO doctors (email,first_name,last_name,license_number,phone_number) VALUES ('doctor_dos@email.com','Doctor','Dos',222,'222');

INSERT INTO employees (first_name,last_name,user_id) VALUES ('Ignacio','Vacca',1);
INSERT INTO employees (first_name,last_name,user_id) VALUES ('Sofia','Scotto',2);
INSERT INTO employees (first_name,last_name,user_id) VALUES ('Agustina','Garcia',5);

INSERT INTO extractionists (fullname) VALUES ('Star Lord');
INSERT INTO extractionists (fullname) VALUES ('Iron Man');
INSERT INTO extractionists (fullname) VALUES ('The Hulk');

INSERT INTO presumptive_diagnoses (description) VALUES ('Acidez de estómago');
INSERT INTO presumptive_diagnoses (description) VALUES ('Acné');
INSERT INTO presumptive_diagnoses (description) VALUES ('Acúfenos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Adenoma hipofisiario');
INSERT INTO presumptive_diagnoses (description) VALUES ('Aerofagia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Aftas bucales');
INSERT INTO presumptive_diagnoses (description) VALUES ('Agorafobia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Alergia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Alergia al látex');
INSERT INTO presumptive_diagnoses (description) VALUES ('Alergia al polen');
INSERT INTO presumptive_diagnoses (description) VALUES ('Alergias alimentarias');
INSERT INTO presumptive_diagnoses (description) VALUES ('Alopecia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Alzheimer');
INSERT INTO presumptive_diagnoses (description) VALUES ('Amenorrea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Amigdalitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Anemia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Aneurisma de aorta');
INSERT INTO presumptive_diagnoses (description) VALUES ('Angina de pecho');
INSERT INTO presumptive_diagnoses (description) VALUES ('Anisakiasis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Anorexia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ansiedad');
INSERT INTO presumptive_diagnoses (description) VALUES ('Apendicitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Apnea del sueño');
INSERT INTO presumptive_diagnoses (description) VALUES ('Arritmias');
INSERT INTO presumptive_diagnoses (description) VALUES ('Arterioesclerosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Artritis reumatoide');
INSERT INTO presumptive_diagnoses (description) VALUES ('Artrosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Asbestosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Asma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Astigmatismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ataxia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ateroesclerosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Autismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Balanitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bartolinitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Botulismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bradicardia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bronquiectasias');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bronquitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Brucelosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bruxismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bulimia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bullying');
INSERT INTO presumptive_diagnoses (description) VALUES ('Bursitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Callos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de cabeza y cuello');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de colon');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de cuello de útero');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de endometrio');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de estómago');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de faringe');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de intestino delgado');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de laringe');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de las vías biliares');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de mama');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de ovario');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de páncreas');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de piel');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de próstata');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de pulmón');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de riñón');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de testículo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de tiroides');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de uretra');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cáncer de vejiga');
INSERT INTO presumptive_diagnoses (description) VALUES ('Candidiasis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cardiopatías congénitas');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cataratas');
INSERT INTO presumptive_diagnoses (description) VALUES ('Celiaquía');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cervicitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Chikungunya');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ciática');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cirrosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Citomegalovirus');
INSERT INTO presumptive_diagnoses (description) VALUES ('Colecistitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Colelitiasis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cólera');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cólico del lactante');
INSERT INTO presumptive_diagnoses (description) VALUES ('Cólico nefrítico');
INSERT INTO presumptive_diagnoses (description) VALUES ('Colitis ulcerosa');
INSERT INTO presumptive_diagnoses (description) VALUES ('Colon irritable');
INSERT INTO presumptive_diagnoses (description) VALUES ('Conjuntivitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Coronavirus');
INSERT INTO presumptive_diagnoses (description) VALUES ('Corte de digestión o hidrocución');
INSERT INTO presumptive_diagnoses (description) VALUES ('Creutzfeldt jakob (Vacas locas)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Degeneración macular asociada a la edad (DMAE)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Demencia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Demencia con cuerpos de Lewy');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dengue');
INSERT INTO presumptive_diagnoses (description) VALUES ('Depresión');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dermatitis atópica');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dermatitis del pañal');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dermatitis seborreica');
INSERT INTO presumptive_diagnoses (description) VALUES ('Derrame pleural');
INSERT INTO presumptive_diagnoses (description) VALUES ('Desprendimiento de retina');
INSERT INTO presumptive_diagnoses (description) VALUES ('Diabetes');
INSERT INTO presumptive_diagnoses (description) VALUES ('Diarrea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Diarrea del viajero');
INSERT INTO presumptive_diagnoses (description) VALUES ('Difteria');
INSERT INTO presumptive_diagnoses (description) VALUES ('Disfunción sexual femenina');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dislexia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dismenorrea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dismorfofobia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dispepsia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Diverticulitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Dolor de cabeza o cefalea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Eccema');
INSERT INTO presumptive_diagnoses (description) VALUES ('Edema Pulmonar');
INSERT INTO presumptive_diagnoses (description) VALUES ('ELA (esclerosis lateral amiotrófica)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Embolia pulmonar');
INSERT INTO presumptive_diagnoses (description) VALUES ('Encefalitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Encefalopatía hepática');
INSERT INTO presumptive_diagnoses (description) VALUES ('Endocarditis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Endometriosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad de Crohn');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad de Kawasaki');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad de Paget');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad de Whipple');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad de Wilson');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad del sueño');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad por virus de Marburgo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad renal crónica');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfermedad tromboembólica venosa');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enfisema');
INSERT INTO presumptive_diagnoses (description) VALUES ('Enuresis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Epilepsia');
INSERT INTO presumptive_diagnoses (description) VALUES ('EPOC (enfermedad pulmonar obstructiva crónica)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Escarlatina');
INSERT INTO presumptive_diagnoses (description) VALUES ('Esclerodermia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Esclerosis múltiple');
INSERT INTO presumptive_diagnoses (description) VALUES ('Escoliosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Esofagitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Esofagitis eosinofílica');
INSERT INTO presumptive_diagnoses (description) VALUES ('Esófago de Barret');
INSERT INTO presumptive_diagnoses (description) VALUES ('Espina bífida');
INSERT INTO presumptive_diagnoses (description) VALUES ('Espolón calcáneo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Espondilitis anquilosante');
INSERT INTO presumptive_diagnoses (description) VALUES ('Esquizofrenia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Esterilidad e infertilidad');
INSERT INTO presumptive_diagnoses (description) VALUES ('Estrabismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Estreñimiento');
INSERT INTO presumptive_diagnoses (description) VALUES ('Estrés');
INSERT INTO presumptive_diagnoses (description) VALUES ('Factores de riesgo cardiovascular');
INSERT INTO presumptive_diagnoses (description) VALUES ('Faringitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Faringoamigdalitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fascitis plantar');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fenilcetonuria');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fibromialgia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fibrosis pulmonar');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fibrosis pulmonar idiopática');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fibrosis Quística');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fiebre amarilla');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fiebre del heno');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fiebre tifoidea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fiebres hemorrágicas');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fimosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Fobia social');
INSERT INTO presumptive_diagnoses (description) VALUES ('Gases y flatulencias');
INSERT INTO presumptive_diagnoses (description) VALUES ('Gastritis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Gastroenteritis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Glaucoma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Golpe de calor');
INSERT INTO presumptive_diagnoses (description) VALUES ('Gonorrea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Gota');
INSERT INTO presumptive_diagnoses (description) VALUES ('Gripe');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hafefobia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Halitosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hematoma subdural');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hemocromatosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hemofilia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hemorragias ginecológicas');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hemorroides');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hepatitis A');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hepatitis B');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hepatitis C');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hernia discal');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hernia inguinal');
INSERT INTO presumptive_diagnoses (description) VALUES ('Herpes labial');
INSERT INTO presumptive_diagnoses (description) VALUES ('Herpes zóster');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hidradenitis supurativa');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hidrocele');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipercolesterolemia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipercolesterolemia familiar');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hiperhidrosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipermenorrea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipermetropía');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hiperplasia benigna de próstata');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipertensión arterial');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipertiroidismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipoglucemia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipotensión');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hipotiroidismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hirsutismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Hongos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ictus');
INSERT INTO presumptive_diagnoses (description) VALUES ('Impétigo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Impotencia/ disfunción eréctil');
INSERT INTO presumptive_diagnoses (description) VALUES ('Incontinencia urinaria');
INSERT INTO presumptive_diagnoses (description) VALUES ('Infarto de miocardio');
INSERT INTO presumptive_diagnoses (description) VALUES ('Infección urinaria o cistitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Insomnio');
INSERT INTO presumptive_diagnoses (description) VALUES ('Insuficiencia cardiaca');
INSERT INTO presumptive_diagnoses (description) VALUES ('Intolerancia a la lactosa');
INSERT INTO presumptive_diagnoses (description) VALUES ('Juanetes');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ladillas (piojos del pubis)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Legionella');
INSERT INTO presumptive_diagnoses (description) VALUES ('Leishmaniasis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Lengua geográfica o glositis migratoria benigna');
INSERT INTO presumptive_diagnoses (description) VALUES ('Lepra');
INSERT INTO presumptive_diagnoses (description) VALUES ('Leucemia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Linfoma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Lipedema');
INSERT INTO presumptive_diagnoses (description) VALUES ('Lipotimia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Listeriosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Litiasis renal');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ludopatía');
INSERT INTO presumptive_diagnoses (description) VALUES ('Lumbalgia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Lupus');
INSERT INTO presumptive_diagnoses (description) VALUES ('Malaria');
INSERT INTO presumptive_diagnoses (description) VALUES ('Melanoma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Melanoma metastásico');
INSERT INTO presumptive_diagnoses (description) VALUES ('Melasma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Meningitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Mielitis transversa');
INSERT INTO presumptive_diagnoses (description) VALUES ('Mieloma múltiple');
INSERT INTO presumptive_diagnoses (description) VALUES ('Migrañas');
INSERT INTO presumptive_diagnoses (description) VALUES ('Miocardiopatía');
INSERT INTO presumptive_diagnoses (description) VALUES ('Miomas uterinos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Miopía');
INSERT INTO presumptive_diagnoses (description) VALUES ('Mobbing');
INSERT INTO presumptive_diagnoses (description) VALUES ('Molusco contagioso');
INSERT INTO presumptive_diagnoses (description) VALUES ('Mononucleosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Muerte súbita cardiaca');
INSERT INTO presumptive_diagnoses (description) VALUES ('Narcolepsia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Neumonía');
INSERT INTO presumptive_diagnoses (description) VALUES ('Neumotórax');
INSERT INTO presumptive_diagnoses (description) VALUES ('Obesidad');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ojo seco');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ojo vago');
INSERT INTO presumptive_diagnoses (description) VALUES ('Orquitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Ortorexia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Orzuelo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Osteoporosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Osteosarcoma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Otitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Pancreatitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Paperas (parotiditis)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Parálisis de Bell');
INSERT INTO presumptive_diagnoses (description) VALUES ('Parálisis del sueño');
INSERT INTO presumptive_diagnoses (description) VALUES ('Parkinson');
INSERT INTO presumptive_diagnoses (description) VALUES ('Pericarditis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Peste');
INSERT INTO presumptive_diagnoses (description) VALUES ('Pie de atleta');
INSERT INTO presumptive_diagnoses (description) VALUES ('Pielonefritis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Pies cavos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Pies planos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Pies zambos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Poliomielitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Preeclampsia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Presbicia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Prostatitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Psoriasis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Rabia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Rectocele');
INSERT INTO presumptive_diagnoses (description) VALUES ('Retinoblastoma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Retinopatía diabética');
INSERT INTO presumptive_diagnoses (description) VALUES ('Rinitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Rosácea');
INSERT INTO presumptive_diagnoses (description) VALUES ('Rotavirus');
INSERT INTO presumptive_diagnoses (description) VALUES ('Rubéola');
INSERT INTO presumptive_diagnoses (description) VALUES ('Salmonelosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sarampión');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sarcoidosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sarcoma');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sepsis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sífilis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Silicosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de burnout');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Diógenes');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Down');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Dravet');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de estrés postraumático');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de fatiga crónica');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Ganser');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Gilbert');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Gorlin-Goltz');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Guillain-Barré');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de hiperestimulación ovárica');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Marfan');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Patau');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Reiter');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Rett');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Reye');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Sanfilippo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Sjögren');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Smith-Magenis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Tourette');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Turner');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Williams');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome de Wolfram');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome del túnel carpiano');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndrome postvacacional');
INSERT INTO presumptive_diagnoses (description) VALUES ('Síndromes mielodisplásicos (SMD)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sinus Pilonidal');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sinusitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Siringomielia');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sobrecrecimiento bacteriano o SIBO');
INSERT INTO presumptive_diagnoses (description) VALUES ('Sonambulismo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Tendinitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Tétanos');
INSERT INTO presumptive_diagnoses (description) VALUES ('Tortícolis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Tos ferina');
INSERT INTO presumptive_diagnoses (description) VALUES ('Toxoplasmosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trastorno bipolar');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trastorno de conducta del sueño en fase REM');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trastorno de menstruación');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trastorno obsesivo compulsivo (TOC)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trastorno por atracón');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trastorno por déficit de atención e hiperactividad (TDAH)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trastornos del ritmo circadiano');
INSERT INTO presumptive_diagnoses (description) VALUES ('Tricomoniasis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Trombosis venosa (flebitis)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Tuberculosis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Tumores cerebrales');
INSERT INTO presumptive_diagnoses (description) VALUES ('Uñas encarnadas (onicocriptosis)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Uretritis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Urticaria');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vaginitis o vulvovaginitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vaginosis bacteriana');
INSERT INTO presumptive_diagnoses (description) VALUES ('Varicela');
INSERT INTO presumptive_diagnoses (description) VALUES ('Varices');
INSERT INTO presumptive_diagnoses (description) VALUES ('Varicocele');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vasculitis');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vegetaciones');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vértigo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vigorexia');
INSERT INTO presumptive_diagnoses (description) VALUES ('VIH / Sida');
INSERT INTO presumptive_diagnoses (description) VALUES ('Virus del Nilo Occidental');
INSERT INTO presumptive_diagnoses (description) VALUES ('Virus del papiloma humano (VPH)');
INSERT INTO presumptive_diagnoses (description) VALUES ('Virus Zika');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vitíligo');
INSERT INTO presumptive_diagnoses (description) VALUES ('Vulvitis');
